package com.example.practico4moviles.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4moviles.models.Contact
import com.example.practico4moviles.models.Emails
import com.example.practico4moviles.repositories.ContactRepository
import okhttp3.MultipartBody
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody


class MainViewModel: ViewModel() {

    private val _profileImage = MutableLiveData<String>().apply { value = "" }
    val profileImage: LiveData<String> = _profileImage

    private val _name = MutableLiveData<String>().apply { value = "" }
    val name: LiveData<String> = _name

    private val _company = MutableLiveData<String>().apply { value = "" }
    val company: LiveData<String> = _company

    private val _lastName = MutableLiveData<String>().apply { value = "" }
    val lastName: LiveData<String> = _lastName

    private val _address = MutableLiveData<String>().apply { value = "" }
    val address: LiveData<String> = _address

    private val _city = MutableLiveData<String>().apply { value = "" }
    val city: LiveData<String> = _city

    private val _state = MutableLiveData<String>().apply { value = "" }
    val state: LiveData<String> = _state



    fun getContactById(id: Int) {
        ContactRepository.getContactById(
            id,
            onSuccess = { contact ->
                _profileImage.value = contact.profile_picture
                _name.value = contact.name
                _company.value = contact.company
                _lastName.value = contact.last_name
                _address.value = contact.address
                _city.value = contact.city
                _state.value = contact.state
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
    fun addContact(contact: Contact, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        ContactRepository.addContact(contact, onSuccess, onError)
    }


    fun updateContact(contact: Contact, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        ContactRepository.updateContact(contact, onSuccess, onError)
    }

    // Método para eliminar el contacto
    fun deleteContact(id: Int, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        ContactRepository.deleteContact(id, onSuccess, onError)
    }

    fun uploadProfileImage(contactId: Int, imageFile: File, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("profile_picture", imageFile.name, requestFile)

        ContactRepository.uploadProfilePicture(contactId, body,
            onSuccess = {
                _profileImage.value = "https://apicontactos.jmacboy.com/api/personas/$contactId/profile-picture"
                onSuccess()
            },
            onError = {
                onError(it)
            }
        )
    }

//    fun uploadImage(image: String, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
//        ContactRepository.uploadProfilePicture(id, image, onSuccess, onError)
//    }
}
