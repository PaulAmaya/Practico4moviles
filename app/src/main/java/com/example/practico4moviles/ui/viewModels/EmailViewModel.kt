package com.example.practico4moviles.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmailViewModel : ViewModel() {
    private val _email = MutableLiveData<String>().apply {
        value = ""
    }

    private val _label = MutableLiveData<String>().apply {
        value = ""
    }

}