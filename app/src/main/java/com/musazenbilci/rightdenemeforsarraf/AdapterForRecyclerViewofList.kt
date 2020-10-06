package com.musazenbilci.rightdenemeforsarraf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_row_of_list.view.*

class AdapterForRecyclerViewofList(val listNames:ArrayList<String>,val listIds:ArrayList<Int>):RecyclerView.Adapter<AdapterForRecyclerViewofList.ListNameVH>() {
    class ListNameVH(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNameVH {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row_of_list,parent,false)
        return ListNameVH(view)
    }

    override fun onBindViewHolder(holder: ListNameVH, position: Int) {
        holder.itemView.recyclerViewoflistTextView.text=listNames[position]
        holder.itemView.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToShowDataFragment(listNames[position])
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return listNames.size
    }
}