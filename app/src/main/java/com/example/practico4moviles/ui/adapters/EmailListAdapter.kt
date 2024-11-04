package com.example.practico4moviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practico4moviles.R
import com.example.practico4moviles.models.Email
import com.example.practico4moviles.models.Emails
import com.example.practico4moviles.ui.fragments.EmailFragment

class EmailListAdapter(
    private var listener: EmailFragment
) : RecyclerView.Adapter<EmailListAdapter.EmailViewHolder>() {
    private var emailList: Emails = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emailList[position])
    }

    override fun getItemCount(): Int = emailList.size

    fun updateData(it: Emails) {
        emailList = it
        notifyDataSetChanged()
    }

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val emailTextView: TextView = itemView.findViewById(R.id.textViewEmail)
        private val labelTextView: TextView = itemView.findViewById(R.id.textViewLabel)

        fun bind(email: Email) {
            emailTextView.text = email.email
            labelTextView.text = email.label
        }
    }

    interface EmailItemListener {
        fun onEmailClicked(email: Email)
    }

}