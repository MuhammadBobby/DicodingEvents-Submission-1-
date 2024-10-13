package com.dicoding.dicodingevent.ui.finishedEvent

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
import com.dicoding.dicodingevent.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FinishedAdapter
    private lateinit var viewModel: FinishedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[FinishedViewModel::class.java]

        // Inisialisasi adapter dengan listener klik untuk navigasi ke halaman detail
        adapter = FinishedAdapter { eventId ->
            val bundle = Bundle().apply {
                putInt("eventId", eventId)
            }
            findNavController().navigate(R.id.action_navigation_finished_event_to_detailFragment, bundle)
        }

        // Set up RecyclerView
        setupRecyclerView()

        // Observe events from ViewModel
        viewModel.events.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events) // Update the adapter with new data
        }

        // Observe error messages from ViewModel
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Observe loading state from ViewModel
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading) // Show or hide loading based on the LiveData
        }

        return root
    }

    private fun setupRecyclerView() {
        binding.rvListFinished.layoutManager = LinearLayoutManager(context)
        binding.rvListFinished.adapter = adapter
    }


    //    handle loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFinished.visibility = View.VISIBLE
        } else {
            binding.progressBarFinished.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}