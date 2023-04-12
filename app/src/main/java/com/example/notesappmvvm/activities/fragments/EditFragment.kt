package com.example.notesappmvvm.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesappmvvm.R
import com.example.notesappmvvm.db.Note
import com.example.notesappmvvm.model.NoteViewModel

class EditFragment : Fragment() {

    private lateinit var noteTitleET: EditText
    private lateinit var noteDescriptionET: EditText
    private lateinit var applyBTN: Button
    private var noteId: Int = 0

    private val viewModel: NoteViewModel by activityViewModels()
    val args: EditFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Update Note"

        noteTitleET = view.findViewById(R.id.ETnoteTitle)
        noteDescriptionET = view.findViewById(R.id.ETnoteDescription)
        applyBTN = view.findViewById(R.id.BTNapply)

        fun fillEditTexts(){
                val noteTitle = args.noteTitle
                val noteDescription = args.noteDescription
                noteId = args.noteId

                noteTitleET.setText(noteTitle)
                noteDescriptionET.setText(noteDescription)
        }

        fillEditTexts()
        applyBTN.setOnClickListener{ updateData() }
    }

    private fun updateData(){
        val noteTitle = noteTitleET.text.toString()
        val noteDescription = noteDescriptionET.text.toString()

        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
            val updatedNote = Note(noteTitle, noteDescription, noteId)
            viewModel.update(updatedNote)
            Toast.makeText(activity, "Note Updated", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_editFragment_to_mainFragment)
        }else{
            Toast.makeText(activity, "Enter Data", Toast.LENGTH_SHORT).show()
        }
    }
}

