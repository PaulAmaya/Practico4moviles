package com.example.practico4moviles.api

import com.example.practico4moviles.models.Contact
import com.example.practico4moviles.models.Contacts
import com.example.practico4moviles.models.Emails
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactService {
    @GET("personas")
    fun getContacts(): Call<Contacts>

    @GET("personas/{id}")
    fun getContactsById(@Path("id") id: Int): Call<Contact>

    @PUT("personas/{id}")
    fun updateContact(@Path("id") id: Int, @Body contact: Contact): Call<Void>

    @DELETE("personas/{id}")
    fun deleteContact(@Path("id") id: Int): Call<Void>

    @POST("personas")
    fun addContact(@Body contact: Contact): Call<Void>

    @Multipart
    @POST("personas/{id}/profile-picture")
    fun uploadProfilePicture(
        @Path("id") contactId: Int,
        @Part image: MultipartBody.Part
    ): Call<Void>

    @POST("emails")
    fun getListaEmails(): Call<Emails>



}