package com.dicoding.dicodingevent.ui.availableEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.databinding.FragmentAvailableBinding

class AvailableFragment : Fragment() {

    private var _binding: FragmentAvailableBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AvailableAdapter
    private lateinit var viewModel: AvailableViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAvailableBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(AvailableViewModel::class.java)

        // Inisialisasi adapter dengan listener klik untuk navigasi ke halaman detail
        adapter = AvailableAdapter { eventId ->
            val bundle = Bundle().apply {
                putInt("eventId", eventId)
            }
            findNavController().navigate(R.id.action_availableFragment_to_detailFragment, bundle)
        }


        // Set up RecyclerView
        setupRecyclerView()

        // Observe events from ViewModel
        viewModel.events.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events) // Update adapter with new data
        }

        // Observe error messages from ViewModel
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Observe loading state from ViewModel
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading) // Show or hide loading based on LiveData
        }

        return root
    }

    private fun setupRecyclerView() {
        // Set layout manager dan adapter untuk RecyclerView
        binding.rvListAvailable.layoutManager = LinearLayoutManager(context)
        binding.rvListAvailable.adapter = adapter
    }

    // Handle loading state
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
