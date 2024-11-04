package com.example.practico4moviles.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practico4moviles.R
import com.example.practico4moviles.databinding.ActivityMainBinding
import com.example.practico4moviles.models.Contact
import com.example.practico4moviles.ui.adapters.ContactListAdapter
import com.example.practico4moviles.ui.viewModels.ContactListViewModel

class ContactListActivity : AppCompatActivity(), ContactListAdapter.ContactItemListener {
    private val viewModel: ContactListViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // El permiso fue concedido
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
            } else {
                // El permiso fue denegado
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setupRecyclerView()
        setupViewModelObservers()
        viewModel.getContactList()

        checkAndRequestPermission()

        binding.fabAddUser.setOnClickListener {
            val intent = Intent(this, ContactDetailActivity::class.java)
            intent.putExtra("contact_id", 0) // 0 indica que es un nuevo contacto
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.getContactList() // Recargar la lista de contactos cada vez que se vuelve a esta actividad
    }

    private fun checkAndRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // El permiso ya fue concedido
                Toast.makeText(this, "Permiso ya concedido", Toast.LENGTH_SHORT).show()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                // Muestra una explicación al usuario sobre por qué necesitas el permiso
                Toast.makeText(this, "El permiso es necesario para acceder a las imágenes", Toast.LENGTH_LONG).show()
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            else -> {
                // Solicita el permiso directamente
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }




    private fun setupRecyclerView() {
        val adapter = ContactListAdapter(this)
        binding.rvContactList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ContactListActivity)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.contactList.observe(this) {
            val adapter = binding.rvContactList.adapter as ContactListAdapter
            adapter.updateData(it)
        }
    }

    override fun onContactClicked(contact: Contact) {
        val intent = ContactDetailActivity.newIntent(this, contact.id)
        startActivity(intent)
    }



}