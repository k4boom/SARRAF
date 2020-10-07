package com.musazenbilci.rightdenemeforsarraf

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_data.*
import kotlinx.android.synthetic.main.fragment_show_data.view.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.jar.Manifest


class AddDataFragment : Fragment() {
    //SOME BABY STEPS HERE

    var ViewList=ArrayList<View>()
    var uriForTheImage: Uri?=null
    var bitmapForTheImage: Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //println(AddDataFragmentArgs.fromBundle(requireArguments()).fromWhere)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        saveTheCharButton.setOnClickListener {
            saveInput(it)
        }
        imageView.setOnClickListener {
            selectImage(it)
        }
        arguments?.let {
            val sheetName=AddDataFragmentArgs.fromBundle(it).clickedListNameForaddData
            if (AddDataFragmentArgs.fromBundle(it).fromWhere==0){
                val gorselSecmeArkaPlan= BitmapFactory.decodeResource(context?.resources,R.drawable.image_selection)
                imageView.setImageBitmap(gorselSecmeArkaPlan)

             }
             else{
                val SelectedId=AddDataFragmentArgs.fromBundle(it).İdOfTheClickedName
                println(SelectedId)
                context?.let {
                    try {
                        val database=it.openOrCreateDatabase("ListContent",Context.MODE_PRIVATE,null)
                        println(sheetName)
                        val cursor=database.rawQuery("SELECT * FROM $sheetName WHERE id= ? ",
                            arrayOf(SelectedId.toString()))
                        val nameIndex=cursor.getColumnIndex("name")
                        val nicknameIndex=cursor.getColumnIndex("nickname")
                        val defW1WordIndex=cursor.getColumnIndex("defW1Word")
                        val fatalityIndex=cursor.getColumnIndex("fatality")
                        val friendshipIndex=cursor.getColumnIndex("friendship")
                        val relationshipIndex=cursor.getColumnIndex("relationship")
                        val intelligenceIndex=cursor.getColumnIndex("intelligence")
                        val fearsIndex=cursor.getColumnIndex("fears")
                        val secretIndex=cursor.getColumnIndex("secret")
                        val goodGuyButIndex=cursor.getColumnIndex("goodGuyBut")
                        val socialLifeIndex=cursor.getColumnIndex("socialLife")
                        val obsessionIndex=cursor.getColumnIndex("obsession")
                        val extraIndex=cursor.getColumnIndex("extra")
                        val gorselIndex=cursor.getColumnIndex("gorsel")
                        while (cursor.moveToNext()){
                            nameOfTheCharacter.setText(cursor.getString(nameIndex))
                            nickNameofTheCharacter.setText(cursor.getString(nicknameIndex))
                            defW1WordOfTheCharacter.setText(cursor.getString(defW1WordIndex))
                            //I VE LOST MY WILLING CUZ OF SOMETHING WENT WRONG THAT I EXPLAINED AT DOWN A FEW LINES OF CODE
                            if(cursor.getString(fatalityIndex)==""){
                                fatalityOfTheCharacter.visibility=View.GONE
                            }
                            fatalityOfTheCharacter.setText(cursor.getString(fatalityIndex))
                            friendshipOfTheCharacter.setText(cursor.getString(friendshipIndex))
                            relationshipOfTheCharacter.setText(cursor.getString(relationshipIndex))
                            intelligenceOfTheCharacter.setText(cursor.getString(intelligenceIndex))
                            fearsOfTheCharacter.setText(cursor.getString(fearsIndex))
                            secretOfTheCharacter.setText(cursor.getString(secretIndex))
                            goodGuyButOfTheCharacter.setText(cursor.getString(goodGuyButIndex))
                            socialLifeOfTheCharacter.setText(cursor.getString(socialLifeIndex))
                            obsessionOfTheCharacter.setText(cursor.getString(obsessionIndex))
                            extraOfTheCharacter.setText(cursor.getString(extraIndex))
                            val byteDizisi=cursor.getBlob(gorselIndex)
                            val bitmap=BitmapFactory.decodeByteArray(byteDizisi,0,byteDizisi.size)
                            imageView.setImageBitmap(bitmap)
                        }
                        cursor.close()
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
        //I VE TRIED TO PUT ALL THE TEXTVIEWS INTO A LIST AND DO THE NULLCHECK ALL TOGETHER BUT IT DIDNOT WORKED AND I DIDNOT UNDERSTAND WHY
//        addElementToViewList()
//        for(view in ViewList){
//            if(view.textView.text==""){
//                view.visibility=View.GONE
//            }else{
//
//            }
//        }



        super.onViewCreated(view, savedInstanceState)
    }
    fun saveInput(view: View){
        var dbName:String?=null
        var personName=nameOfTheCharacter.text.toString()
        val personNickname=nickNameofTheCharacter.text.toString()
        val persondefW1Word=defW1WordOfTheCharacter.text.toString()
        val personFatality=fatalityOfTheCharacter.text.toString()
        val personFriendship=friendshipOfTheCharacter.text.toString()
        val personRelationship=relationshipOfTheCharacter.text.toString()
        val personIntelligence=intelligenceOfTheCharacter.text.toString()
        val personFears=fearsOfTheCharacter.text.toString()
        val personSecret=secretOfTheCharacter.text.toString()
        val personGoodGuyBut=goodGuyButOfTheCharacter.text.toString()
        val personSocialLife=socialLifeOfTheCharacter.text.toString()
        val personObsession=obsessionOfTheCharacter.text.toString()
        val personExtra=extraOfTheCharacter.text.toString()
        if(bitmapForTheImage!=null){
            val smallerBitmap=createSmallBitmap(bitmapForTheImage!!,300)
            val outputStream= ByteArrayOutputStream()
            smallerBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteDizisi=outputStream.toByteArray()
            try {

               arguments?.let {
                    dbName=AddDataFragmentArgs.fromBundle(it).clickedListNameForaddData}
                    println(dbName)
                    context?.let {
                        val database=it.openOrCreateDatabase("ListContent",Context.MODE_PRIVATE,null)
                        database.execSQL("CREATE TABLE IF NOT EXISTS $dbName(id INTEGER PRIMARY KEY,name VARCHAR," +
                                "nickname VARCHAR,defW1Word VARCHAR,fatality VARCHAR,friendship VARCHAR,relationship VARCHAR" +
                                ",intelligence VARCHAR,fears VARCHAR,secret VARCHAR,goodGuyBut VARCHAR,socialLife VARCHAR," +
                                "obsession VARCHAR,extra VARCHAR,gorsel BLOB)")
                        val sqlString=" INSERT INTO $dbName (name,nickname,defW1word,fatality,friendship,relationship,intelligence,fears,secret,goodGuyBut,socialLife,obsession,extra,gorsel) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
                        val statement=database.compileStatement(sqlString)
//                        //I VE THOUGHT TO CREATE A LOOP OR FUN FOR IT BUT I REALIZED I HAD TO DO THAT WORK ANYWAY
                        statement.bindString(1,personName)
                        statement.bindString(2,personNickname)
                        statement.bindString(3,persondefW1Word)
                        statement.bindString(4,personFatality)
                        statement.bindString(5,personFriendship)
                        statement.bindString(6,personRelationship)
                        statement.bindString(7,personIntelligence)
                        statement.bindString(8,personFears)
                        statement.bindString(9,personSecret)
                        statement.bindString(10,personGoodGuyBut)
                        statement.bindString(11,personSocialLife)
                        statement.bindString(12,personObsession)
                        statement.bindString(13,personExtra)
                        statement.bindBlob(14,byteDizisi)
                        statement.execute()
                        val cursor=database.rawQuery("SELECT * FROM $dbName",null)
                        val index=cursor.getColumnIndex("name")
                        while (cursor.moveToNext()){
                            //println(cursor.getString(index))
                        }
                    }


           }catch (e:Exception){
               e.printStackTrace()
            }
            arguments?.let {
                val action=AddDataFragmentDirections.actionAddDataFragmentToShowDataFragment(
                    AddDataFragmentArgs.fromBundle(it).clickedListNameForaddData)
                Navigation.findNavController(view).navigate(action)
            }
        }

    }
    private fun addElementToViewList(){
        ViewList.add(nameOfTheCharacter)
        ViewList.add(nickNameofTheCharacter)
        ViewList.add(defW1WordOfTheCharacter)
        ViewList.add(fatalityOfTheCharacter)
        ViewList.add(friendshipOfTheCharacter)
        ViewList.add(relationshipOfTheCharacter)
        ViewList.add(intelligenceOfTheCharacter)
        ViewList.add(fearsOfTheCharacter)
        ViewList.add(secretOfTheCharacter)
        ViewList.add(goodGuyButOfTheCharacter)
        ViewList.add(socialLifeOfTheCharacter)
        ViewList.add(obsessionOfTheCharacter)
        ViewList.add(extraOfTheCharacter)
    }
    fun selectImage(view: View){
        //CHECK IF THE PERMISSION IS ALREADY GIVEN
        activity?.let {
            if(ContextCompat.checkSelfPermission(it.applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                //WHERE THE PERMISSION IS NOT GRANTED
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }else{
                //WHERE IT IS
                val galeriIntent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==1){

                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //  THAT MEANS WE HAVE THE PERMISSION
                    val galeriIntent =Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galeriIntent,2)
                }

        }
        //else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==2 && resultCode==Activity.RESULT_OK && data!=null){
            uriForTheImage=data.data
            try {
                context?.let {
                    if(uriForTheImage!=null){
                        if (Build.VERSION.SDK_INT>=28){
                            val source=  ImageDecoder.createSource(it.contentResolver,uriForTheImage!!)
                            bitmapForTheImage= ImageDecoder.decodeBitmap(source)
                            imageView.setImageBitmap(bitmapForTheImage)
                        }else{
                            bitmapForTheImage=MediaStore.Images.Media.getBitmap(it.contentResolver,uriForTheImage!!)
                            imageView.setImageBitmap(bitmapForTheImage)
                        }
                    }

                }

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun createSmallBitmap(userBitmap: Bitmap,maximumSize:Int):Bitmap{
        var width=userBitmap.width
        var height=userBitmap.height
        val bitmapProportion=width.toDouble()/height.toDouble()
        if (bitmapProportion>1){
            width=maximumSize
            val smallerHeight=width/bitmapProportion
            height=smallerHeight.toInt()
        }else{
            height=maximumSize
            val smallerWidth=height*bitmapProportion
            width=smallerWidth.toInt()
        }

        return Bitmap.createScaledBitmap(userBitmap,width,height,true)
    }
}