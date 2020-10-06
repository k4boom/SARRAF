package com.musazenbilci.rightdenemeforsarraf

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_list.*
import java.lang.Exception

class CreateListFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("onAttach called")


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            //WHERE TO CREATE DATABASE OF LIST NAMES ONLY
        listNameSaveButton.setOnClickListener {
            if (createListNameTextView.text.toString() == "" || createListNameTextView.text == null) {
                Toast.makeText(activity, "Lütfen bir liste ismi giriniz", Toast.LENGTH_LONG).show()
            } else {

            try {
                context?.let {
                    //DATABASE FOR LIST NAMES ONLY
                    val database =
                        it.openOrCreateDatabase("OnlyListNames", Context.MODE_PRIVATE, null)
                    database.execSQL("CREATE TABLE IF NOT EXISTS listNames(id INTEGER PRIMARY KEY,name VARCHAR)")
                    val sqlString = "INSERT INTO listNames(name) VALUES(?)"
                    val statement = database.compileStatement(sqlString)
                    var nameofList = createListNameTextView.text.toString()
                    statement.bindString(1, nameofList)
                    statement.execute()
                    //DATASHEET FOR EACH LIST NAME IN A DIFFERENT DATABASE
//                    val databaseForEach =
//                        it.openOrCreateDatabase("ListContent", Context.MODE_PRIVATE, null)
//                    databaseForEach.execSQL(
//                        "CREATE TABLE IF NOT EXISTS $nameofList(id INTEGER PRIMARY KEY,name VARCHAR," +
//                                "nickname VARCHAR,defW1Word VARCHAR,fatality VARCHAR,friendship VARCHAR,relationship VARCHAR" +
//                                ",intelligence VARCHAR,fears VARCHAR,secret VARCHAR,goodGuyBut VARCHAR,socialLife VARCHAR," +
//                                "obsession VARCHAR,extra VARCHAR,gorsel BLOB)"
//                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val getBackAction =
                CreateListFragmentDirections.actionCreateListFragmentToListFragment()
            Navigation.findNavController(view).navigate(getBackAction)
        }
        }
        super.onViewCreated(view, savedInstanceState)

    }




}