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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ise308.orhan.berk.notetoberkorhan.MainActivity
import ise308.orhan.berk.notetoberkorhan.R

class NoteAdapter(
        private val mainActivity: MainActivity, private val noteList: List<Note>, private val lockNotes : Boolean): RecyclerView.Adapter<NoteAdapter.ListItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_notes, parent, false)

        return ListItemHolder(itemView)

    }

    override fun getItemCount(): Int {

        return noteList.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {

            // Position valaue works like note ID.
            // We do not add any id value on JSON file, so when we want to find a note.
            // We use position value instead of note ID.
            val note = noteList[position]

            holder.title.text = note.title // Set Title

            // If Description Shorter than 14 characters
            if(note.description!!.length <= 14) {
                // Show all the characters in the TextView
                holder.description.text = note.description!!.substring(0, note.description!!.length)

            } else  if(note.description!!.length >= 15){ // If Description Longer or Equal 15 Characters
                // Show only first 15 characters in the TextView
                holder.description.text = note.description!!.substring(0, 15)

            }

            // Check Note Status
            if (note.idea) {
                holder.status.text = mainActivity.resources.getString(R.string.idea) // Set Note Status
                holder.cardView.setCardBackgroundColor(mainActivity.resources.getColor(R.color.idea)) // Change Note Background Color
            } else if (note.important){
                holder.status.text = mainActivity.resources.getString(R.string.importantText) // Set Note Status
                holder.cardView.setCardBackgroundColor(mainActivity.resources.getColor(R.color.important)) // Change Note Background Color
            } else if (note.todo){
                holder.status.text = mainActivity.resources.getString(R.string.todoText) // Set Note Status
                holder.cardView.setCardBackgroundColor(mainActivity.resources.getColor(R.color.todo)) // Change Note Background Color
            } else {
                holder.status.text = mainActivity.resources.getString(R.string.normalNote) // Set Note Status
                holder.title.setTextColor(mainActivity.resources.getColor(R.color.black)) // Change Text Color
                holder.description.setTextColor(mainActivity.resources.getColor(R.color.black)) // Change Text Color
            }


    }

    inner class ListItemHolder(view: View) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {

        internal var title = view.findViewById<View>(R.id.ViewTitle) as TextView

        internal var description = view.findViewById<View>(R.id.ViewDescription) as TextView

        internal var status = view.findViewById<View>(R.id.ViewStatus) as TextView

        internal var cardView = view.findViewById<View>(R.id.cardView) as CardView

        init {

            if (lockNotes){
                view.isClickable = false
            } else {
                view.isClickable = true
                view.setOnClickListener(this)
            }

        }

        override fun onClick(view: View) {
            // When user click a item,
            // We are sending item position to Show Note function in MainActivity
            mainActivity.showNote(adapterPosition)
        }

    }
}