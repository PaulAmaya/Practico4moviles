package com.example.practico4moviles.repositories

import com.example.practico4moviles.api.ContactService
import com.example.practico4moviles.models.Emails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EmailRepository {
    fun getEmailsList(onSuccess: (Emails) -> Unit, onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactService::class.java)
        service.getListaEmails().enqueue(object : Callback<Emails> {
            override fun onResponse(call: Call<Emails>, response: Response<Emails>) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                } else {
                    onError(Throwable("Error en la respuesta"))
                }
            }

            override fun onFailure(call: Call<Emails>, t: Throwable) {
                onError(t)
            }
        })
    }
}