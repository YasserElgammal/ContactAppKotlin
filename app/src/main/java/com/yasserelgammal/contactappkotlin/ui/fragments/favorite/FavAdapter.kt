package com.yasserelgammal.contactappkotlin.ui.fragments.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yasserelgammal.contactappkotlin.data.model.Person
import com.yasserelgammal.contactappkotlin.databinding.FavRowBinding
import com.yasserelgammal.contactappkotlin.databinding.ItemRowBinding
import com.yasserelgammal.contactappkotlin.ui.fragments.common.ContactDiffUtil
import com.yasserelgammal.contactappkotlin.ui.fragments.list.ContactRecycleInteraction

class FavAdapter(private var interaction: ContactRecycleInteraction): RecyclerView.Adapter<FavAdapter.RecycleViewHolder> (){
    private var oldContacts = emptyList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        return RecycleViewHolder(
            FavRowBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.bind(contact = oldContacts[position])
    }

    override fun getItemCount(): Int {
        return oldContacts.size
    }

    fun setData(newContactList: List<Person>){
        val diffUtil = ContactDiffUtil(oldContacts, newContactList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldContacts = newContactList
        diffResult.dispatchUpdatesTo(this)
    }

    class RecycleViewHolder(private val binding: FavRowBinding, private val interaction: ContactRecycleInteraction): RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Person) {
            binding.apply {
                tvNameShort.text = contact.name

                itemView.setOnClickListener {
                    interaction.onItemSelected(adapterPosition,contact)
                }
            }
        }

    }

}