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



package ise308.orhan.berk.notetoberkorhan.utils

import android.content.Context

import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.ArrayList



class JSONSerializer(
    private val filename: String,
    private val context: Context) {

    @Throws(IOException::class, JSONException::class)
    fun save(notes: List<Note>) {

       // Creating array
        val jArray = JSONArray()

        // Putting Notes
        for (n in notes)
            jArray.put(n.convertToJSON())


        var writer: Writer? = null
        try {
            // Creating Private file
            // File name comes from MainActivity!
            val out = context.openFileOutput(filename,
                Context.MODE_PRIVATE)

            writer = OutputStreamWriter(out)
            writer.write(jArray.toString())
        } finally {
            if (writer != null) {
                // After writing the notes into JSON file, we are closing private file.
                writer.close()
            }
        }
    }

    @Throws(IOException::class, JSONException::class)
    fun load(): ArrayList<Note> {
        val noteList = ArrayList<Note>()
        var reader: BufferedReader? = null

        try {

            val `in` = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(`in`))
            val jsonString = StringBuilder()

            for (line in reader.readLine()) {
                jsonString.append(line)
            }

            val jArray = JSONTokener(jsonString.toString()).
            nextValue() as JSONArray

            for (i in 0 until jArray.length()) {
                noteList.add(Note(jArray.getJSONObject(i)))
            }
        } catch (e: FileNotFoundException) {

        } finally {
            reader!!.close()
        }

        return noteList
    }


}