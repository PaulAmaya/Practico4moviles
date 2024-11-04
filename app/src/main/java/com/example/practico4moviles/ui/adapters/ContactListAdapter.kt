package com.example.practico4moviles.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practico4moviles.R
import com.example.practico4moviles.databinding.ContactItemBinding
import com.example.practico4moviles.models.Contact
import com.example.practico4moviles.models.Contacts

class ContactListAdapter(private val listener: ContactItemListener) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {
    private var contactsList: Contacts = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactsList[position],listener)
    }

    fun updateData(it: Contacts) {
        contactsList = it
        notifyDataSetChanged()
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgViewProfile = itemView.findViewById<ImageView>(R.id.imgViewProfile)
        private val lblName = itemView.findViewById<TextView>(R.id.lblName)
        private val lblCompany = itemView.findViewById<TextView>(R.id.lblCompany)

        fun bind(contact: Contact, listener: ContactItemListener) {
            Glide.with(itemView)
                .load(contact.profile_picture)
                .circleCrop()
                .into(imgViewProfile)
            lblName.text = contact.name
            lblCompany.text = contact.company
            itemView.setOnClickListener {
                listener.onContactClicked(contact)
            }
        }
    }

    interface ContactItemListener {
        fun onContactClicked(contact: Contact)
    }

}

