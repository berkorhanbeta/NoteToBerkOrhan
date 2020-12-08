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
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {


    private var lockNotes: Boolean = false
    lateinit var hideCB : CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(getString(R.string.settings))


        hideCB = findViewById(R.id.hideCheckBox)


        getSharedPref()
        if (lockNotes == true){
            hideCB.isChecked = true
        } else {
            hideCB.isChecked = false
        }


        hideCB.setOnClickListener {
            if (hideCB.isChecked){
                lockNotes = true
                setSharedPref()
            } else if (!hideCB.isChecked){
                lockNotes = false
                setSharedPref()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }

    fun setSharedPref(){
        val prefs = getSharedPreferences("NoteToBerkOrhan", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("lockNotes", lockNotes)
        editor.apply()
    }

    fun getSharedPref(){
        val prefs = getSharedPreferences("NoteToBerkOrhan", Context.MODE_PRIVATE)
        lockNotes = prefs.getBoolean("lockNotes", false)
    }
}