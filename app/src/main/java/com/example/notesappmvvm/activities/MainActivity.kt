package com.example.notesappmvvm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappmvvm.R
import com.example.notesappmvvm.db.Note
import com.example.notesappmvvm.model.NoteViewModel
import com.example.notesappmvvm.rv_adapter.NoteClickDeleteInterface
import com.example.notesappmvvm.rv_adapter.NoteClickInterface
import com.example.notesappmvvm.rv_adapter.NoteRVadapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRV = findViewById(R.id.RVnotes)
        addFAB = findViewById(R.id.FABaddNote)

        notesRV.layoutManager = LinearLayoutManager(this)
        val noteRVadapter = NoteRVadapter(this, this, this)
        notesRV.adapter = noteRVadapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                noteRVadapter.updateList(it)
            }
        })

        addFAB.setOnClickListener{
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteClick(note: Note) {
        viewModel.delete(note)
        Toast.makeText(this, "${note.noteTitle} deleted", Toast.LENGTH_SHORT).show()
    }
}