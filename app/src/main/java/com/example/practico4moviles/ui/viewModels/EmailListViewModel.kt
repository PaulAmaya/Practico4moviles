package com.example.practico4moviles.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4moviles.models.Email
import com.example.practico4moviles.models.Emails
import com.example.practico4moviles.repositories.EmailRepository

class EmailListViewModel: ViewModel() {
    private val _emailList = MutableLiveData<Emails>().apply {
        value = arrayListOf()
    }
    val emailList: LiveData<Emails> = _emailList

    fun getEmailList(){
        EmailRepository.getEmailsList(
            onSuccess = {
                _emailList.value = it
            }, onError = {
                it.printStackTrace()
            }
        )
    }
}