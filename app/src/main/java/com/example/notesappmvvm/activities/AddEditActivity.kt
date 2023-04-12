package com.example.notesappmvvm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notesappmvvm.R
import com.example.notesappmvvm.db.Note
import com.example.notesappmvvm.model.NoteViewModel

class AddEditActivity : AppCompatActivity() {

    lateinit var noteTitleET: EditText
    lateinit var noteDesctiptionET: EditText
    lateinit var applyBTN: Button
    lateinit var viewModel: NoteViewModel
    var noteId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        noteTitleET = findViewById(R.id.ETnoteTitle)
        noteDesctiptionET = findViewById(R.id.ETnoteDescription)
        applyBTN = findViewById(R.id.BTNaddUpdate)
        val noteType = intent.getStringExtra("noteType")

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)

        fun isEditIntentCheck(){
            if (noteType.equals("Edit")){
                val noteTitle = intent.getStringExtra("noteTitle")
                val noteDescription = intent.getStringExtra("noteDescription")
                noteId = intent.getIntExtra("noteId", 0)

                noteTitleET.setText(noteTitle)
                noteDesctiptionET.setText(noteDescription)
            }
        }

        isEditIntentCheck()

        fun upsertData() {
            if (noteType.equals("Edit")) { updateData() } else { insertData() }
        }

        applyBTN.setOnClickListener{ upsertData() }
    }

    private fun insertData(){
        val noteTitle = noteTitleET.text.toString()
        val noteDescription = noteDesctiptionET.text.toString()

        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
            viewModel.insert(Note(noteTitle, noteDescription))
            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }else{
            Toast.makeText(this, "Enter Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData(){
        val noteTitle = noteTitleET.text.toString()
        val noteDescription = noteDesctiptionET.text.toString()

        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
            val updatedNote = Note(noteTitle, noteDescription, noteId)
            viewModel.update(updatedNote)
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }else{
            Toast.makeText(this, "Enter Data", Toast.LENGTH_SHORT).show()
        }
    }
}