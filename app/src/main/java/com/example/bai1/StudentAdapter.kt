package com.example.bai1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class StudentAdapter(val students: List<StudentModel>): BaseAdapter() {
    override fun getCount() = students.size

    override fun getItem(position: Int) = students[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            itemView = LayoutInflater.from(parent?.context).inflate(R.layout.layout_student_item, null)
            viewHolder = ViewHolder()
            viewHolder.textHoten = itemView.findViewById(R.id.text_hoten)
            viewHolder.textMssv = itemView.findViewById(R.id.text_mssv)
            itemView.tag = viewHolder
        } else {
            itemView = convertView
            viewHolder = itemView.tag as ViewHolder
        }

        val student = students[position]
        viewHolder.textHoten.text = student.hoten
        viewHolder.textMssv.text = student.mssv

        return itemView
    }

    class ViewHolder {
        lateinit var textHoten: TextView
        lateinit var textMssv: TextView
    }
}