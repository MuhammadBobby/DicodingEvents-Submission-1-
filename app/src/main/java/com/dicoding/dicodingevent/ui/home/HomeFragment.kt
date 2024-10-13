package com.dicoding.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.dicoding.dicodingevent.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var adapter: HomeAdapter
    private lateinit var slideModels: MutableList<SlideModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Membuat daftar SlideModel
        slideModels = ArrayList()

        // Set up RecyclerView
        setupRecyclerView()

        // Observe events from ViewModel
        homeViewModel.eventFinished.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events) // Update the adapter with new data
        }

        // Observe error messages from ViewModel
        homeViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Observe loading state from ViewModel
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading) // Show or hide loading based on LiveData
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Memastikan bahwa data diambil ketika fragment dibuat
        homeViewModel.loadEventSlider()

        // Observe data from the ViewModel if necessary
        homeViewModel.eventSlider.observe(viewLifecycleOwner) { slides ->
            // Log untuk melihat data yang diterima

            if (slides.isNotEmpty()) {
                slideModels.clear()
                slideModels.addAll(slides.map { SlideModel(it.mediaCover) })
                binding.imageSlider.setImageList(slideModels)
            } else {
                Toast.makeText(context, "Image Failed Load", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter()
        binding.rvListHome.layoutManager = LinearLayoutManager(context)
        binding.rvListHome.adapter = adapter
    }

    // Handle loading state
    private fun showLoading(isLoading: Boolean) {
        binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
