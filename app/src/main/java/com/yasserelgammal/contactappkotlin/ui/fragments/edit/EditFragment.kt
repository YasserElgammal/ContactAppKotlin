package com.yasserelgammal.contactappkotlin.ui.fragments.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yasserelgammal.contactappkotlin.R
import com.yasserelgammal.contactappkotlin.data.model.Person
import com.yasserelgammal.contactappkotlin.data.viewmodel.ContactViewModel
import com.yasserelgammal.contactappkotlin.databinding.FragmentEditBinding
import com.yasserelgammal.contactappkotlin.ui.fragments.common.SharedViewModel

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<EditFragmentArgs>()
    private val mContactViewModelViewModel: ContactViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private var selectedDrawer: Int?=null
    private var contactId: Int?= null


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, mSharedViewModel.items)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        drawableSelected()

        val argsData = args.contactData

        binding.apply {
            contactId = argsData.id
            inputLayoutNameEdit.editText?.setText(argsData.name)
            iLayoutPhoneEdit.editText?.setText(argsData.phoneNumber)
            iLayoutTitleEdit.editText?.setText(argsData.title)
            iLayoutNotesEdit.editText?.setText(argsData.notes)

            (menu.editText as? AutoCompleteTextView)?.setText(
                mSharedViewModel.parseGroupToString(argsData.label), false
            )

        }
        when(argsData.image){
            R.drawable.ic_person -> {
                binding.ivPersonSelectedEdit.isSelected = true
                selectedDrawer = R.drawable.ic_person
            }
            R.drawable.ic_female -> {
                binding.ivFemaleSelectedEdit.isSelected = true
                selectedDrawer = R.drawable.ic_female
            }
            R.drawable.ic_male -> {
                binding.ivMaleSelectedEdit.isSelected = true
                selectedDrawer = R.drawable.ic_male
            }
        }

        binding.buttonSubmitEdit.setOnClickListener {
            getDataToUpdate()
        }

    }

    // toggle function between drawable
    private fun drawableSelected(){
        binding.apply {

            ivFemaleSelectedEdit.setOnClickListener {
                ivFemaleSelectedEdit.isSelected = !ivFemaleSelectedEdit.isSelected
                selectedDrawer = R.drawable.ic_female

                ivMaleSelectedEdit.isSelected = false
                ivPersonSelectedEdit.isSelected = false
            }

            ivPersonSelectedEdit.setOnClickListener {
                ivPersonSelectedEdit.isSelected = !ivPersonSelectedEdit.isSelected
                selectedDrawer = R.drawable.ic_person

                ivMaleSelectedEdit.isSelected = false
                ivFemaleSelectedEdit.isSelected = false
            }

            ivMaleSelectedEdit.setOnClickListener {
                ivMaleSelectedEdit.isSelected = !ivMaleSelectedEdit.isSelected
                selectedDrawer = R.drawable.ic_male

                ivFemaleSelectedEdit.isSelected = false
                ivPersonSelectedEdit.isSelected = false
            }
        }
    }

    // get data from edit text and update process
    private fun getDataToUpdate(){
        val eName = binding.inputLayoutNameEdit.editText?.text.toString().trim()
        val ePhone = binding.iLayoutPhoneEdit.editText?.text.toString().trim()
        val eTitle = binding.iLayoutTitleEdit.editText?.text.toString().trim()
        val eGroup = binding.menu.editText?.text.toString().trim()
        val eNotes = binding.iLayoutNotesEdit.editText?.text.toString().trim()

        val validation = mSharedViewModel.validations(eName,ePhone,eTitle,eNotes)

        if (validation){
            val insertData = Person(args.contactData.id,eName, eTitle, ePhone, mSharedViewModel.parseGroup(eGroup) ,eNotes, selectedDrawer!!,contactId!!)
            mContactViewModelViewModel.updateData(insertData)
            Toast.makeText(context, "Contact Updated Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        }else{
            Toast.makeText(context, "Some Fields missed", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}