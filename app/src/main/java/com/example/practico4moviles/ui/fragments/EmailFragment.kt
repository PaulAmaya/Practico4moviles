package com.example.practico4moviles.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practico4moviles.databinding.FragmentEmailBinding
import com.example.practico4moviles.ui.adapters.EmailListAdapter
import com.example.practico4moviles.ui.viewModels.EmailListViewModel

class EmailFragment : Fragment() {

    private var _binding: FragmentEmailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmailListViewModel by viewModels()
    private lateinit var emailListAdapter: EmailListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailListAdapter = EmailListAdapter(this)
        binding.recyclerViewEmails.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = emailListAdapter
        }

        // Observa los emails del ViewModel y actualiza el adaptador cuando cambien
        viewModel.emailList.observe(viewLifecycleOwner) { emails ->
            Log.d("EmailFragment", "Emails received: ${emails.size}")
            emailListAdapter.updateData(emails)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
