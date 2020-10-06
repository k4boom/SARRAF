package com.musazenbilci.rightdenemeforsarraf

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import java.lang.Exception

class ListFragment : Fragment() {
    var listNameList=ArrayList<String>()
    var listIdList=ArrayList<Int>()
    private lateinit var listAdapter: AdapterForRecyclerViewofList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate called")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createNewListButton.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToCreateListFragment()
            Navigation.findNavController(view).navigate(action)
        }
        listAdapter=AdapterForRecyclerViewofList(listNameList,listIdList)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=listAdapter
        super.onViewCreated(view, savedInstanceState)
        sqlDataRequestfromOnlyListNames()
    }
    fun sqlDataRequestfromOnlyListNames(){
        try {
            context?.let {
                val database=it.openOrCreateDatabase("OnlyListNames", Context.MODE_PRIVATE,null)
                val cursor=database.rawQuery("SELECT * FROM listNames",null)
                val listNameIndex=cursor.getColumnIndex("name")
                val listIdIndex=cursor.getColumnIndex("id")
                listIdList.clear()
                listNameList.clear()
                while (cursor.moveToNext()){
                    listIdList.add(cursor.getInt(listIdIndex))
                    listNameList.add(cursor.getString(listNameIndex))

                }
                cursor.close()
                listAdapter.notifyDataSetChanged()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }














    override fun onStart() {
        println("onStart called")
        super.onStart()
    }

    override fun onResume() {
        println("onREsume called")
        super.onResume()
    }

    override fun onPause() {
        println("onPause called")
        super.onPause()
    }
}