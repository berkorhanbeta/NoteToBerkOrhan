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



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ise308.orhan.berk.notetoberkorhan.utils.JSONSerializer
import ise308.orhan.berk.notetoberkorhan.utils.Note
import ise308.orhan.berk.notetoberkorhan.utils.NoteAdapter


class MainActivity : AppCompatActivity() {


    private var tempNote = Note()
    private var mSerializer: JSONSerializer? = null
    private var noteList: ArrayList<Note>? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null


    private var lockNotes: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))



        // What's happening when user clicked the FAB button.
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
         //   val dialog = NewNote()
         //   dialog.show(supportFragmentManager, "")
            val intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }

        // Creating/Opening BerkOrhan JSON File
        mSerializer = JSONSerializer("BerkOrhan.json", applicationContext)

        try {
            noteList = mSerializer!!.load() // Loading Notes
        } catch (e: Exception) {
            noteList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }

        sharedPref()
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        adapter = NoteAdapter(this, this.noteList!!, lockNotes)


        val layoutManager = LinearLayoutManager(applicationContext)

        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()

        recyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    // This function open ShowNote Activity
    // Also this function send note position value
    // We use this value for find and show the correct note
    fun showNote(positionID: Int) {
        val intent = Intent(this, ShowNote::class.java)
        intent.putExtra("positionID",positionID)
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
        adapter!!.notifyDataSetChanged()

            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)


    }

    fun sharedPref(){
        val prefs = getSharedPreferences("NoteToBerkOrhan", Context.MODE_PRIVATE)
        lockNotes = prefs.getBoolean("lockNotes", false)
    }

}
