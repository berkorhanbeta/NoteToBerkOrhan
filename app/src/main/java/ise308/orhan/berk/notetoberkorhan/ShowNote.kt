/*
 * Created by Berk Orhan on 7.12.2020
 * APPBeta Mobile | www.APPBeta.net | mobile@appbeta.net
 *
 *TODO           YASAL UYARI
 *
 *                 Bu proje dosyasının herhangi bir amaç ile izinsiz olarak dağıtılması,
 *                 çoğaltılması, satılması ve izinsiz kullanılması yasaktır!
 *
 *                 İzinsiz kullanım, çoğaltımı, dağıtımı, satışı yapıldığı
 *                 tespit edilmesi halinde yasal yollara başvurulacaktır.
 *
 *                  Copyright (c)  2020. Berk Orhan. All rights reserved.
 *
 */



package ise308.orhan.berk.notetoberkorhan

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ise308.orhan.berk.notetoberkorhan.utils.JSONSerializer
import ise308.orhan.berk.notetoberkorhan.utils.Note


class ShowNote : AppCompatActivity() {


    private var note: Note? =  null
    lateinit var noteTitle : TextView
    lateinit var noteDescription : TextView
    private var noteList: ArrayList<Note>? = null
    private var mSerializer: JSONSerializer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_note)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Toolbar BackButton Show
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        noteTitle = findViewById(R.id.onTitle)
        noteDescription = findViewById(R.id.onDescription)


        jOpener()
        // Receiving position value from MainActivity
        val positionID = intent!!.getIntExtra("positionID",0)
        Log.i("@@@@@@@@@@@@@@@@@ : ",positionID.toString())

        // Sending arraylist parameter to selectedNote Function
        selectedNote(noteList!![positionID])
        Log.i("@@@@@@@@@@@@@@@@@ : ", note.toString())

        // Calling setNote Function
        setNote()

    }

    // Toolbar Back Button Function
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Get Selected Note
    fun selectedNote(noteSelected: Note) {
        note = noteSelected
    }


    fun setNote(){

        // Set Note Title
        noteTitle.text = note!!.title

        // Set Note Description
        noteDescription.text = note!!.description


        // Check which one is checked.
        // If its not checked, then hide the TextView
        if (note!!.important){
            supportActionBar?.setTitle(getString(R.string.importantText))
        }else if (note!!.todo){
            supportActionBar?.setTitle(getString(R.string.todoText))
        } else if (note!!.idea){
            supportActionBar?.setTitle(getString(R.string.idea))
        } else {
            supportActionBar?.setTitle(getString(R.string.normalNote))
        }
    }


    // Opening our JSON file for read values.
    fun jOpener(){
        mSerializer = JSONSerializer("BerkOrhan.json", applicationContext)

        try {
            noteList = mSerializer!!.load() // Loading Notes
        } catch (e: Exception) {
            noteList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }
    }


}