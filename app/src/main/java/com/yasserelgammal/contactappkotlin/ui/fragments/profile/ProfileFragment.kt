package com.yasserelgammal.contactappkotlin.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yasserelgammal.contactappkotlin.R
import com.yasserelgammal.contactappkotlin.data.viewmodel.ContactViewModel
import com.yasserelgammal.contactappkotlin.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding?= null
    private val binding get() = _binding!!
    private var favValue:Int = 0
    private var contactId:Int?= null
    private val mContactViewModelViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeargs = ProfileFragmentArgs.fromBundle(it)
            val contact = safeargs.contactProfile
            binding?.apply {
                contactId = contact.id
                ivProfile.setImageResource(contact.image)
                tvNameProfile.text = contact.name
                tvNumberProfile.text = contact.phoneNumber
                tvTitleProfile.text = contact.title
                tvGroupProfile.text = contact.label.toString()
                tvNotesProfile.text = contact.notes
                if (contact.fav == 1){
                    ivFav.isSelected = true
                    favValue = 1
                }
            }
        }
        favSelected()
    }

    private fun favSelected() {
        binding.ivFav.setOnClickListener {
            binding.ivFav.isSelected = !binding.ivFav.isSelected
            if (favValue == 1){
                favValue = 0
                mContactViewModelViewModel.favContact(0, contactId!!)
                Toast.makeText(requireContext(), "Contact removed from Favorite", Toast.LENGTH_SHORT).show()
            }else{
                favValue = 1
                mContactViewModelViewModel.favContact(1, contactId!!)
                Toast.makeText(requireContext(), "Contact Added to Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

}