package com.yasserelgammal.contactappkotlin.ui.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yasserelgammal.contactappkotlin.data.model.Person
import com.yasserelgammal.contactappkotlin.databinding.ItemRowBinding
import com.yasserelgammal.contactappkotlin.ui.fragments.common.ContactDiffUtil

class RecycleAdapter(private var interaction: ContactRecycleInteraction): RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> (){
    private var oldContacts = emptyList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        return RecycleViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false),
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

    class RecycleViewHolder(private val binding: ItemRowBinding, private val interaction: ContactRecycleInteraction): RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Person) {
            binding.apply {
                tvName.text = contact.name
                tvTitle.text = contact.title
                tvNumber.text = contact.phoneNumber
                ivPerson.setImageResource(contact.image)

                itemView.setOnClickListener {
                    interaction.onItemSelected(adapterPosition,contact)
                }

                ivEdit.setOnClickListener {
                    interaction.onItemUpdate(adapterPosition,contact)
                }

                ivCall.setOnClickListener {
                    interaction.onItemCall(adapterPosition, contact)
                }

                ivDelete.setOnClickListener {
                    interaction.onItemDeleted(adapterPosition, contact)
                }
            }
        }

    }

}