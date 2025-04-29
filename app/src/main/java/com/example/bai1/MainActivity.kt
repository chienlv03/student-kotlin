package com.example.bai1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var selectedIndex: Int = -1

    private lateinit var editHoten: EditText
    private lateinit var editMssv: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonUpdate: Button
    private lateinit var buttonDelete: Button
    private lateinit var listStudent: ListView

    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<StudentModel>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editHoten = findViewById(R.id.edit_hoten)
        editMssv = findViewById(R.id.edit_mssv)
        buttonAdd = findViewById(R.id.button_add)
        buttonUpdate = findViewById(R.id.button_update)
        buttonDelete = findViewById(R.id.button_delete)
        listStudent = findViewById(R.id.list_student)

        for (i in 1..10) {
            students.add(StudentModel("Student $i", "MSSV $i"))
        }

        adapter = StudentAdapter(students)
        listStudent.adapter = adapter

        fun clearInputs() {
            editHoten.text.clear()
            editMssv.text.clear()
            selectedIndex = -1
        }

        listStudent.setOnItemClickListener { _, _, position, _ ->
            val selectedStudent = students[position]
            editHoten.setText(selectedStudent.hoten)
            editMssv.setText(selectedStudent.mssv)
            selectedIndex = position
        }

        buttonAdd.setOnClickListener {
            val hoten = editHoten.text.toString()
            val mssv = editMssv.text.toString()

            if (hoten.isEmpty() || mssv.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ họ tên và MSSV", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (students.any { it.mssv == mssv }) {
                Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            students.add(0, StudentModel(hoten, mssv))
            adapter.notifyDataSetChanged()
            clearInputs()
        }

        buttonUpdate.setOnClickListener {
            val name = editHoten.text.toString().trim()
            val mssv = editMssv.text.toString().trim()

            if (selectedIndex != -1) {
                // Kiểm tra nếu MSSV mới bị trùng với MSSV của sinh viên khác
                if (students.withIndex().any { (index, student) ->
                        index != selectedIndex && student.mssv == mssv
                    }) {
                    Toast.makeText(this, "MSSV đã tồn tại ở sinh viên khác", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                students[selectedIndex] = StudentModel(name, mssv)
                adapter.notifyDataSetChanged()
                clearInputs()
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên để cập nhật", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDelete.setOnClickListener {
            if (selectedIndex != -1) {
                students.removeAt(selectedIndex)
                adapter.notifyDataSetChanged()
                clearInputs()
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên để xoá", Toast.LENGTH_SHORT).show()
            }
        }
    }
}