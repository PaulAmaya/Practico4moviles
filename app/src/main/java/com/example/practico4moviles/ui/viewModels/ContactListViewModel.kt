package com.example.practico4moviles.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4moviles.models.Contact
import com.example.practico4moviles.models.Contacts
import com.example.practico4moviles.repositories.ContactRepository

class ContactListViewModel : ViewModel() {
    private val _contactList = MutableLiveData<Contacts>().apply {
        value = arrayListOf()
    }
    val contactList: LiveData<Contacts> = _contactList

    fun getContactList(){
        ContactRepository.getContactsList(
            onSuccess = {
                _contactList.value = it
            }, onError = {
                it.printStackTrace()
            }
        )
    }
}