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

import org.json.JSONException
import org.json.JSONObject

class Note {


    var title: String? = null
    var description: String? = null
    var idea: Boolean = false
    var todo: Boolean = false
    var important: Boolean = false

    // Default empty Constructor
    constructor() {
    }

    /*

        Oluşturulması gereken yapı ;
                [
                    {
                        "title" : "başlık",
                        "description" : "içerik",
                        "boolean" : true / false
                    }
                ]

    */


    // JSON file variable names
    private val JSON_TITLE = "title"
    private val JSON_DESCRIPTION = "description"
    private val JSON_IDEA = "idea"
    private val JSON_TODO = "todo"
    private val JSON_IMPORTANT = "important"



    @Throws(JSONException::class)
    constructor(json: JSONObject) {

        // Getting Values inside of JSON file
        title = json.getString(JSON_TITLE)
        description = json.getString(JSON_DESCRIPTION)
        idea = json.getBoolean(JSON_IDEA)
        todo = json.getBoolean(JSON_TODO)
        important = json.getBoolean(JSON_IMPORTANT)
    }

    @Throws(JSONException::class)
    fun convertToJSON(): JSONObject {

        val json = JSONObject()
        // Putting Values to inside of JSON file
        json.put(JSON_TITLE, title)
        json.put(JSON_DESCRIPTION, description)
        json.put(JSON_IDEA, idea)
        json.put(JSON_TODO, todo)
        json.put(JSON_IMPORTANT, important)

        return json
    }

}