package com.yasserelgammal.contactappkotlin.ui.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yasserelgammal.contactappkotlin.R
import com.yasserelgammal.contactappkotlin.data.model.Person
import com.yasserelgammal.contactappkotlin.data.viewmodel.ContactViewModel
import com.yasserelgammal.contactappkotlin.databinding.FragmentFavoriteBinding
import com.yasserelgammal.contactappkotlin.databinding.FragmentListBinding
import com.yasserelgammal.contactappkotlin.ui.fragments.list.ContactRecycleInteraction
import com.yasserelgammal.contactappkotlin.ui.fragments.list.ListFragmentDirections


class FavoriteFragment : Fragment(), ContactRecycleInteraction {

    private val mContactViewModel: ContactViewModel by viewModels()
    private var _binding: FragmentFavoriteBinding?=null
    private val binding get() = _binding!!
    private val recAdapter by lazy { FavAdapter(this@FavoriteFragment) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        // RecycleView
        binding.myRecLayout.layoutManager = GridLayoutManager(requireContext(),3)
        binding.myRecLayout.adapter = recAdapter
        // observe data
        mContactViewModel.getAllFav.observe(viewLifecycleOwner, { data ->
            recAdapter.setData(data)

        })
        return binding.root
    }

    override fun onItemSelected(position: Int, item: Person) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToProfileFragment(item)
        findNavController().navigate(action)
    }

    override fun onItemUpdate(position: Int, item: Person) {
    }

    override fun onItemDeleted(position: Int, item: Person) {
    }

    override fun onItemCall(position: Int, item: Person) {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}