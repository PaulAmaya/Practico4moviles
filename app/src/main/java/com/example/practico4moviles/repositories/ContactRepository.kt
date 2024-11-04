package com.example.practico4moviles.repositories

import com.example.practico4moviles.api.ContactService
import com.example.practico4moviles.models.Contact
import com.example.practico4moviles.models.Contacts
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ContactRepository {
    fun getContactsList(onSuccess: (Contacts) -> Unit, onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactService::class.java)
        service.getContacts().enqueue(object : Callback<Contacts> {
            override fun onResponse(call: Call<Contacts>, response: Response<Contacts>) {
                if (response.isSuccessful) {
                    val contact = response.body()
                    onSuccess(contact!!)
                }
            }

            override fun onFailure(call: Call<Contacts>, t: Throwable) {
                onError(t)
            }
        })
    }
    fun getContactById(id: Int, onSuccess: (Contact) -> Unit, onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactService::class.java)
        service.getContactsById(id).enqueue(object : Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if (response.isSuccessful) {
                    val contact = response.body()
                    onSuccess(contact!!)
                }
            }

            override fun onFailure(call: Call<Contact>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }

    fun addContact(contact: Contact, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactService::class.java)
        val call = service.addContact(contact)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error en la respuesta: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }


    fun updateContact(contact: Contact, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactService::class.java)
        val call = service.updateContact(contact.id, contact)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error en la respuesta: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun deleteContact(id: Int, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactService::class.java)
        val call = service.deleteContact(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error en la respuesta: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun uploadProfilePicture(contactId: Int, image: MultipartBody.Part, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactService::class.java)
        val call = service.uploadProfilePicture(contactId, image)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error en la respuesta: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }



}