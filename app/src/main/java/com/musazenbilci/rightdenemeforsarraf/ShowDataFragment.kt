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
import kotlinx.android.synthetic.main.fragment_show_data.*
import java.lang.Exception

class ShowDataFragment() : Fragment() {
    var listName:String=""
    var characterNameList=ArrayList<String>()
    var characterIdList= ArrayList<Int>()
    private lateinit var listAdapter:AdapterForReyclerViewofCharacters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //SETTING THE TITLE
        arguments?.let {
            val listNameArg=ShowDataFragmentArgs.fromBundle(it).clickedListName
            println(listNameArg)
            textView.text=listNameArg
            listName=listNameArg
        }
        addCharButton.setOnClickListener {
            val newAction=ShowDataFragmentDirections.actionShowDataFragmentToAddDataFragment(listName,0,0)
            Navigation.findNavController(it).navigate(newAction)
        }

        listAdapter= AdapterForReyclerViewofCharacters(characterNameList,characterIdList,listName)
        recyclerView2.layoutManager=LinearLayoutManager(context)
        recyclerView2.adapter=listAdapter
        sqlDataRequestwithListName(listName)
        super.onViewCreated(view, savedInstanceState)
    }
    fun sqlDataRequestwithListName(ListName:String){
        try {
            activity?.let {
                val database=it.openOrCreateDatabase("ListContent", Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS $ListName(id INTEGER PRIMARY KEY,name VARCHAR,nickname VARCHAR,defW1Word VARCHAR,fatality VARCHAR,friendship VARCHAR,relationship VARCHAR" +
                        ",intelligence VARCHAR,fears VARCHAR,secret VARCHAR,goodGuyBut VARCHAR,socialLife VARCHAR," +
                        "obsession VARCHAR,extra VARCHAR,gorsel BLOB)")
                val cursor=database.rawQuery("SELECT * FROM $ListName",null)
                val characterNameIndex=cursor.getColumnIndex("name")
                val characterIdIndex=cursor.getColumnIndex("id")
                characterIdList.clear()
                characterNameList.clear()
                while (cursor.moveToNext()){
                    println(characterNameIndex)
                    println(characterIdIndex)
                    characterNameList.add(cursor.getString(characterNameIndex))
                    characterIdList.add(cursor.getInt(characterIdIndex))
                }
                listAdapter.notifyDataSetChanged()
                cursor.close()
                            }


        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}