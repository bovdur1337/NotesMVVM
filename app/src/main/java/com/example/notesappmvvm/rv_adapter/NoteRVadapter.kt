package com.example.notesappmvvm.rv_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappmvvm.R
import com.example.notesappmvvm.db.Note

class NoteRVadapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteDeleteInterface: NoteClickDeleteInterface
): RecyclerView.Adapter<NoteRVadapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val noteTitleTV = itemView.findViewById<TextView>(R.id.TVnoteTitle)
        val deleteIV = itemView.findViewById<ImageView>(R.id.IVdelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRVadapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteRVadapter.ViewHolder, position: Int) {
        holder.noteTitleTV.setText(allNotes.get(position).noteTitle)
        holder.deleteIV.setOnClickListener{ noteDeleteInterface.onDeleteClick(allNotes.get(position)) }
        holder.itemView.setOnClickListener{ noteClickInterface.onNoteClick(allNotes.get(position)) }
    }

    override fun getItemCount(): Int { return allNotes.size }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickInterface{
    fun onNoteClick(note: Note)
}

interface NoteClickDeleteInterface{
    fun onDeleteClick(note: Note)
}