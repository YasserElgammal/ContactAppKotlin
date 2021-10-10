package com.yasserelgammal.contactappkotlin.ui.fragments.list

import android.Manifest
import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yasserelgammal.contactappkotlin.R
import com.yasserelgammal.contactappkotlin.data.model.Person
import com.yasserelgammal.contactappkotlin.data.viewmodel.ContactViewModel
import com.yasserelgammal.contactappkotlin.databinding.FragmentListBinding
import com.yasserelgammal.contactappkotlin.ui.fragments.common.CallUtility
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class ListFragment : Fragment(), ContactRecycleInteraction, EasyPermissions.PermissionCallbacks {

    private val mContactViewModel: ContactViewModel by viewModels()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val recAdapter by lazy { RecycleAdapter(this@ListFragment) }
    private val REQUEST_PHONE_CALL = 19
    private var mMenuSearch : SearchView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        // RecycleView
        binding.myRecLayout.layoutManager = LinearLayoutManager(requireContext())
        binding.myRecLayout.adapter = recAdapter
        // observe data
        mContactViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            recAdapter.setData(data)

        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatAction.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bar_menu, menu)
        mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch?.isSubmitButtonEnabled = false
        mMenuSearch?.setIconifiedByDefault(false)
        mMenuSearch?.imeOptions = EditorInfo.IME_ACTION_DONE

        mMenuSearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchNote(newText)
                return true
            }

        })
    }

    override fun onItemSelected(position: Int, item: Person) {
        val action = ListFragmentDirections.actionListFragmentToProfileFragment(item)
        findNavController().navigate(action)
    }

    override fun onItemUpdate(position: Int, item: Person) {
        val action = ListFragmentDirections.actionListFragmentToEditFragment(item)
        findNavController().navigate(action)
    }

    override fun onItemDeleted(position: Int, item: Person) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(resources.getString(R.string.delete_item))
            .setNeutralButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(getString(R.string.agree)) { _, _ ->
                mContactViewModel.deleteItem(item)
            }
            // Single-choice items (initialized with checked item)
            .show()
    }

    override fun onItemCall(position: Int, item: Person) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:" + item.phoneNumber)

        if (CallUtility.hasCallPermission(requireContext())){
            startActivity(intent)
        }else{
            requestCallPermission()
        }
    }

    private fun requestCallPermission(){
            EasyPermissions.requestPermissions(this,
            "You need to accept call permission to use this feature",
                REQUEST_PHONE_CALL,
                Manifest.permission.CALL_PHONE)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(this).build().show()
        }else{
            requestCallPermission()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete_all -> {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle(resources.getString(R.string.delete_all_contacts_menu))
                    .setNeutralButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.cancel()
                    }
                    .setPositiveButton(getString(R.string.agree)) { _, _ ->
                        mContactViewModel.deleteAll()
                    }
                    .show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        mContactViewModel.searchDatabase(searchQuery).observe(
            this, { list ->
                list.let {
                    recAdapter.setData(it)
                }
            }
        )
    }
}