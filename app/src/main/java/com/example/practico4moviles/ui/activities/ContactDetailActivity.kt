package com.example.practico4moviles.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.practico4moviles.R
import com.example.practico4moviles.databinding.ActivityContactDetailBinding
import com.example.practico4moviles.models.Contact
import com.example.practico4moviles.ui.fragments.EmailFragment
import com.example.practico4moviles.ui.viewModels.MainViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.math.log

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailBinding
    private val viewModel: MainViewModel by viewModels()
    private var isNewContact = false

    private var fileChooserResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                val bitmap = getBitmapFromIntent(data)
                //TODO: aqui enviarían al API el bitmap
                binding.imageViewProfile.setImageBitmap(bitmap)

            }
        }
    private fun getBitmapFromIntent(data: Intent?): Bitmap? {
        val imgUrl = data?.data

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = contentResolver.query(
            imgUrl!!,
            filePathColumn, null, null, null
        )
        cursor!!.moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()

        val bitmap = BitmapFactory.decodeFile(picturePath)
        Log.d("ContactDetailActivity", "bitmap: $bitmap")
        return bitmap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contactId = intent.getIntExtra("contact_id", 0)
        isNewContact = (contactId == 0)

        if (!isNewContact) {
            viewModel.getContactById(contactId)
        }

        setupObservers()
        viewModel.profileImage.observe(this, {  })
        setupEmailFragment()

        Log.e(viewModel.profileImage.value.toString(), "profileImage")
        Glide.with(this)
            .load(viewModel.profileImage.value)
            .circleCrop()
            .into(binding.imageViewProfile)

        binding.imageViewProfile.setOnClickListener { openImagePicker() }
        binding.buttonSave.setOnClickListener { saveContactDetails(contactId) }
        binding.buttonDelete.setOnClickListener { deleteContact(contactId) }
        binding.buttonChangePicture.setOnClickListener { openImagePicker() }
        binding.buttonDelete.visibility = if (isNewContact) View.GONE else View.VISIBLE
    }

    private fun setupObservers() {
        viewModel.name.observe(this, Observer { name ->
            binding.editTextName.setText(name)
        })
        viewModel.company.observe(this, Observer { company ->
            binding.editTextCompany.setText(company)
        })
        viewModel.lastName.observe(this, Observer { lastName ->
            binding.editTextLastName.setText(lastName)
        })
        viewModel.address.observe(this, Observer { address ->
            binding.editTextAddress.setText(address)
        })
        viewModel.city.observe(this, Observer { city ->
            binding.editTextCity.setText(city)
        })
        viewModel.state.observe(this, Observer { state ->
            binding.editTextState.setText(state)
        })
        viewModel.profileImage.observe(this, Observer { profileImage ->
            Glide.with(this)
                .load(profileImage)
                .circleCrop()
                .into(binding.imageViewProfile)
        })
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        fileChooserResultLauncher.launch(intent)
    }


    private fun saveContactDetails(contactId: Int) {
        val updatedContact = Contact(
            id = contactId,
            name = binding.editTextName.text.toString(),
            last_name = binding.editTextLastName.text.toString(),
            company = binding.editTextCompany.text.toString(),
            address = binding.editTextAddress.text.toString(),
            city = binding.editTextCity.text.toString(),
            state = binding.editTextState.text.toString(),
            profile_picture = binding.imageViewProfile.toString()
        )

        if (isNewContact) {
            viewModel.addContact(updatedContact,
                onSuccess = {
                    Toast.makeText(this, "Contacto creado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onError = {
                    Toast.makeText(this, "Error al crear el contacto", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            viewModel.updateContact(updatedContact,
                onSuccess = {
                    Toast.makeText(this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onError = {
                    Toast.makeText(this, "Error al guardar el contacto", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun deleteContact(contactId: Int) {
        viewModel.deleteContact(contactId,
            onSuccess = {
                Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show()
                finish()
            },
            onError = {
                Toast.makeText(this, "Error al eliminar el contacto", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setupEmailFragment() {
        val fragment = EmailFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerEmail, fragment)
            .commit()
    }


    companion object {
        private const val EXTRA_CONTACT_ID = "contact_id"
        fun newIntent(context: Context, contactId: Int): Intent {
            return Intent(context, ContactDetailActivity::class.java).apply {
                putExtra(EXTRA_CONTACT_ID, contactId)
            }
        }
    }
}
