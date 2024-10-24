package com.dicoding.dicodingevent.ui.favoriteEvent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.FavoriteEventViewModelFactory
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.databinding.FragmentAvailableBinding
import com.dicoding.dicodingevent.databinding.FragmentFavoriteEventBinding
import com.dicoding.dicodingevent.ui.availableEvent.AvailableAdapter
import com.dicoding.dicodingevent.ui.availableEvent.AvailableViewModel


class FavoriteEventFragment : Fragment() {

    private var _binding: FragmentFavoriteEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteEventAdapter
    private lateinit var viewModel: FavoriteEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding init
        _binding = FragmentFavoriteEventBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //init view model
        viewModel = ViewModelProvider(this, FavoriteEventViewModelFactory(requireActivity().application))[FavoriteEventViewModel::class.java]

        //init adapter
        adapter = FavoriteEventAdapter(emptyList()) { favoriteEvent ->
            val bundle = Bundle().apply {
                putParcelable("favoriteEvent", favoriteEvent) // Kirim objek Parcelable
            }
            findNavController().navigate(R.id.action_availableFragment_to_detailFragment, bundle)
        }


        //setup recyclerview
        setupRecyclerView()

        //observe favorite events
        viewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEvents ->
            // Panggil setFavoriteEvents() untuk memperbarui data di adapter
            adapter.setFavoriteEvents(favoriteEvents)
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