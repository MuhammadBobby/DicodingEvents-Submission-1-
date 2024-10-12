package com.dicoding.dicodingevent.ui.finishedEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.databinding.FragmentAvailableBinding
import com.dicoding.dicodingevent.databinding.FragmentFinishedBinding
import com.dicoding.dicodingevent.ui.availableEvent.AvailableAdapter
import com.dicoding.dicodingevent.ui.availableEvent.AvailableViewModel

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
        viewModel = ViewModelProvider(this).get(FinishedViewModel::class.java)

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
        adapter = FinishedAdapter()
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