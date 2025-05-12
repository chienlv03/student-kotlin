package com.example.bai1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recycle_view)

        // 1. Chuan bi du lieu
        val students = mutableListOf<StudentModel>()
        for (i in 1..28) { students.add(StudentModel(
            "Student $i", "ID$i",
            resources.getIdentifier("thumb$i", "drawable", packageName),
            false
        )) }

        // 2. Tao adapter
        val adapter = StudentAdapter(students) {
            Log.v("TAG", "Item clicked: $it")
        }

        // 3. Thiet lap cho doi tuong RecyclerView
        val recyclerStudents = findViewById<RecyclerView>(R.id.recycle_student)
        recyclerStudents.adapter = adapter
        recyclerStudents.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btn_add).setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
            val hoten = dialogView.findViewById<EditText>(R.id.editText_hoten)
            val mssv = dialogView.findViewById<EditText>(R.id.editText_mssv)

            AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("OK") { _, _ ->
                    val ten = hoten.text.toString()
                    val ma = mssv.text.toString()
                    students.add(0, StudentModel(ten, ma, R.drawable.thumb1, false))
                    adapter.notifyItemInserted(0)
                    Toast.makeText(this, "Đã thêm sinh viên mới", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }

        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa những sinh viên đã chọn?")
                .setPositiveButton("Xóa") { _, _ ->
                    val indicesToRemove = mutableListOf<Int>()
                    students.forEachIndexed { index, student ->
                        if (student.checkBox) {
                            indicesToRemove.add(index)
                        }
                    }
                    indicesToRemove.reversed().forEach { index ->
                        students.removeAt(index)
                        adapter.notifyItemRemoved(index)
                    }
                    Toast.makeText(this, "Đã thêm sinh viên mới", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Hủy", null)
                .create()
                .show()
        }


        findViewById<Button>(R.id.btn_update).setOnClickListener {
            val studentToUpdate = students.getOrNull(1) // hoặc chọn vị trí động nếu bạn có
            if (studentToUpdate != null) {
                val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
                val hoten = dialogView.findViewById<EditText>(R.id.editText_hoten)
                val mssv = dialogView.findViewById<EditText>(R.id.editText_mssv)

                // Đổ dữ liệu hiện tại vào EditText
                hoten.setText(studentToUpdate.hoten)
                mssv.setText(studentToUpdate.mssv)

                AlertDialog.Builder(this)
                    .setTitle("Cập nhật sinh viên")
                    .setView(dialogView)
                    .setPositiveButton("Cập nhật") { _, _ ->
                        studentToUpdate.hoten = hoten.text.toString()
                        studentToUpdate.mssv = mssv.text.toString()
                        adapter.notifyItemChanged(1)
                        Toast.makeText(this, "Đã thêm sinh viên mới", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Hủy", null)
                    .create()
                    .show()
            }
        }



//        findViewById<Button>(R.id.btn_add).setOnClickListener {
//            students.add(1, StudentModel("New Student", "0001", R.drawable.thumb1, false))
//            adapter.notifyItemInserted(1)
//        }

//        findViewById<Button>(R.id.btn_delete).setOnClickListener {
//            val indicesToRemove = mutableListOf<Int>()
//            // Iterate through students to find checked items
//            students.forEachIndexed { index, student ->
//                if (student.checkBox) {
//                    indicesToRemove.add(index)
//                }
//            }
//            // Remove items in reverse order to avoid index shifting
//            indicesToRemove.reversed().forEach { index ->
//                students.removeAt(index)
//                adapter.notifyItemRemoved(index)
//            }
//        }
//
//        findViewById<Button>(R.id.btn_update).setOnClickListener {
//            students[1].hoten = "Updated Student"
//            adapter.notifyItemChanged(1)
//        }
    }
}