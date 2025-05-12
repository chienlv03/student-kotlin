package com.example.bai1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bai1.StudentAdapter.ItemViewHolder

class StudentAdapter(val students: List<StudentModel>, val callback: (Int) -> Unit): RecyclerView.Adapter<ItemViewHolder>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, null)
        return ItemViewHolder(itemView, callback)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val student = students[position]
        holder.textHoten.text = student.hoten
        holder.textMssv.text = student.mssv
        holder.imageAvatar.setImageResource(student.avatarId)

        holder.checkBox.isChecked = student.checkBox
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            student.checkBox = isChecked
        }
    }

    override fun getItemCount() = students.size

    class ItemViewHolder(itemView: View, val callback: (Int) -> Unit): RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val textHoten: TextView = itemView.findViewById(R.id.text_hoten)
        val textMssv: TextView = itemView.findViewById(R.id.text_mssv)
        val imageAvatar: ImageView = itemView.findViewById(R.id.image_avatar)

        init {
            itemView.setOnClickListener {
                callback(adapterPosition)
            }
        }
    }
}