package com.example.practico4moviles.models

data class Contact (
    val id: Int,
    val name: String,
    val last_name: String,
    val company: String,
    val address: String,
    val city: String,
    val state: String,
    val profile_picture: String,
)

typealias Contacts = ArrayList<Contact>