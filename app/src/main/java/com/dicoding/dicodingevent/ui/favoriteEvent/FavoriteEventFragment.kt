package com.dicoding.dicodingevent.ui.favoriteEvent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.FavoriteViewModelFactory
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.databinding.FragmentFavoriteEventBinding


class FavoriteEventFragment : Fragment() {

    private var _binding: FragmentFavoriteEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteEventAdapter
    private lateinit var viewModel: FavoriteEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding init
        _binding = FragmentFavoriteEventBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //init view model
        viewModel = ViewModelProvider(this, FavoriteViewModelFactory(requireActivity().application))[FavoriteEventViewModel::class.java]

        //init adapter
        adapter = FavoriteEventAdapter(emptyList()) { eventId ->
            val bundle = Bundle().apply {
                putInt("eventId", eventId) // Kirim objek Parcelable
            }
            findNavController().navigate(R.id.action_favoriteEventFragment_to_detailFragment, bundle)
        }


        //setup recyclerview
        setupRecyclerView()

        //observe favorite events
        viewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEvents ->
            if (favoriteEvents.isNullOrEmpty()) {
                binding.tvNoFavorite.visibility = View.VISIBLE
                binding.rvListFavoriteEvents.visibility = View.GONE
            } else {
                binding.tvNoFavorite.visibility = View.GONE
                binding.rvListFavoriteEvents.visibility = View.VISIBLE
                // Panggil setFavoriteEvents() untuk memperbarui data di adapter
                adapter.setFavoriteEvents(favoriteEvents)
            }

        }


        return root
    }

    private fun setupRecyclerView() {
        // Set layout manager dan adapter untuk RecyclerView
        binding.rvListFavoriteEvents.layoutManager = LinearLayoutManager(context)
        binding.rvListFavoriteEvents.adapter = adapter
    }

    companion object {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}