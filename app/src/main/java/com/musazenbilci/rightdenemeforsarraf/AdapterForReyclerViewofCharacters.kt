package com.musazenbilci.rightdenemeforsarraf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_row_of_chars.view.*

class AdapterForReyclerViewofCharacters(val CharNameList:ArrayList<String>,val CharIdList:ArrayList<Int>,val ListName:String):RecyclerView.Adapter<AdapterForReyclerViewofCharacters.VH>() {
    class VH(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row_of_chars,parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.recyclerViewoflistTextView.text=CharNameList[position]
        holder.itemView.setOnClickListener {
            val action=ShowDataFragmentDirections.actionShowDataFragmentToAddDataFragment(ListName,1,CharIdList[position])
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return CharNameList.size
    }
}