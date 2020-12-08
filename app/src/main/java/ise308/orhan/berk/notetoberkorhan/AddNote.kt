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

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ise308.orhan.berk.notetoberkorhan.utils.JSONSerializer
import ise308.orhan.berk.notetoberkorhan.utils.Note

class AddNote : AppCompatActivity() {

    lateinit var noteTitle : EditText
    lateinit var noteDescription : EditText
    lateinit var cbIdea : CheckBox
    lateinit var cbTodo : CheckBox
    lateinit var cbImportant : CheckBox
    lateinit var btnOk : Button

    private var mSerializer: JSONSerializer? = null
    private var noteList: ArrayList<Note>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(getString(R.string.newNote))


        noteTitle = findViewById(R.id.addTitle)
        noteDescription = findViewById(R.id.addDescription)
        cbIdea = findViewById(R.id.checkBox_idea)
        cbTodo = findViewById(R.id.checkBox_todo)
        cbImportant = findViewById(R.id.checkBox_important)
        btnOk = findViewById(R.id.addOk)


        // Creating/Opening BerkOrhan JSON File
        mSerializer = JSONSerializer("BerkOrhan.json", applicationContext)

        try {
            noteList = mSerializer!!.load() // Loading Notes
        } catch (e: Exception) {
            noteList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }




        btnOk.setOnClickListener {

            // Calling addNote Function
            addNote()
            // Close the Alert Dialog
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun addNote(){
        // Create a new note
        val newNote = Note()

        // Converting Title to string and adding to JSON file
        newNote.title = noteTitle.text.toString()

        // Converting Description to string and adding to JSON file
        newNote.description = noteDescription.text.toString()

        // Which one is checked?
        newNote.idea = cbIdea.isChecked
        newNote.todo = cbTodo.isChecked
        newNote.important = cbImportant.isChecked

        // Calling a function from the Main Activity
        createNewNote(newNote)
    }


    fun createNewNote(n: Note) {
        noteList!!.add(n)
        Log.i("@@@@@@@@@@@@ : ", n.toString())
        saveNotes()
    }

    private fun saveNotes() {
        try {
            // Save the note into JSON File
            mSerializer!!.save(this.noteList!!)

        } catch (e: Exception) {
            Log.e("Error Saving Notes", "", e)
        }
    }

}