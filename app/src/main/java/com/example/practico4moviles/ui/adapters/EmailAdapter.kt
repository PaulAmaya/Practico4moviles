package com.example.practico4moviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.practico4moviles.R
import com.example.practico4moviles.databinding.ItemEmailBinding
import com.example.practico4moviles.models.Email

class EmailAdapter(private var emailList: MutableList<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val binding = ItemEmailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmailViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emailList[position])
    }

    override fun getItemCount(): Int = emailList.size

    fun updateEmails(newEmails: List<Email>) {
        emailList.clear()
        emailList.addAll(newEmails)
        notifyDataSetChanged()
    }

    fun addEmail(email: Email) {
        emailList.add(email)
        notifyItemInserted(emailList.size - 1)
    }

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lblemail = itemView.findViewById<TextView>(R.id.textViewEmail)
        private val lblLabel = itemView.findViewById<TextView>(R.id.textViewLabel)
        fun bind(email: Email) {

        }
    }
}
