package com.yasserelgammal.contactappkotlin.ui.fragments.add

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
import com.yasserelgammal.contactappkotlin.R
import com.yasserelgammal.contactappkotlin.data.model.Person
import com.yasserelgammal.contactappkotlin.data.viewmodel.ContactViewModel
import com.yasserelgammal.contactappkotlin.databinding.FragmentAddBinding
import com.yasserelgammal.contactappkotlin.ui.fragments.common.SharedViewModel

class AddFragment : Fragment(){

    private var _binding : FragmentAddBinding?= null
    private val binding get() = _binding!!

    private val mContactViewModelViewModel: ContactViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private var selectedDrawer: Int = R.drawable.ic_person

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawableSelected()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, mSharedViewModel.items)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.buttonSubmit.setOnClickListener {
            getDataToInsert()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // get data from fields and insert
    private fun getDataToInsert(){

            val cName = binding.inputLayoutName.editText?.text.toString().trim()
            val cPhone = binding.iLayoutPhone.editText?.text.toString().trim()
            val cTitle = binding.iLayoutTitle.editText?.text.toString().trim()
            val cGroup = binding.menu.editText?.text.toString().trim()
            val cNotes = binding.iLayoutNotes.editText?.text.toString().trim()

        val validation = mSharedViewModel.validations(cName,cPhone,cTitle,cNotes)

        if (validation){
            val insertData = Person(0,cName, cTitle, cPhone, mSharedViewModel.parseGroup(cGroup) ,cNotes, selectedDrawer,0)
            mContactViewModelViewModel.insertData(insertData)
            Toast.makeText(context, "Contact Created Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(context, "Some Fields missed", Toast.LENGTH_SHORT).show()
        }

    }
        // toggle function between drawable
        private fun drawableSelected(){
            binding.apply {

                ivFemaleSelected.setOnClickListener {
                    ivFemaleSelected.isSelected = !ivFemaleSelected.isSelected
                    selectedDrawer = R.drawable.ic_female

                    ivMaleSelected.isSelected = false
                    ivPersonSelected.isSelected = false
                }

                ivPersonSelected.setOnClickListener {
                    ivPersonSelected.isSelected = !ivPersonSelected.isSelected
                    selectedDrawer = R.drawable.ic_person

                    ivMaleSelected.isSelected = false
                    ivFemaleSelected.isSelected = false
                }

                ivMaleSelected.setOnClickListener {
                    ivMaleSelected.isSelected = !ivMaleSelected.isSelected
                    selectedDrawer = R.drawable.ic_male

                    ivFemaleSelected.isSelected = false
                    ivPersonSelected.isSelected = false
                }
            }
        }

}