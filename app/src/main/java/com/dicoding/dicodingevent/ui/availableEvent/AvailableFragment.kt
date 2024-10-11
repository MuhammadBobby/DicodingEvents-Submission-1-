package com.dicoding.dicodingevent.ui.availableEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        adapter = AvailableAdapter()
        binding.rvListAvailable.layoutManager = LinearLayoutManager(context)
        binding.rvListAvailable.adapter = adapter
    }


    //    handle loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
