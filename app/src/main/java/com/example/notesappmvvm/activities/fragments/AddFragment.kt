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
import com.example.notesappmvvm.R
import com.example.notesappmvvm.db.Note
import com.example.notesappmvvm.model.NoteViewModel

class AddFragment : Fragment() {

    private lateinit var noteTitleET: EditText
    private lateinit var noteDescriptionET: EditText
    private lateinit var applyBTN: Button

    private val viewModel: NoteViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "New Note"

        noteTitleET = view.findViewById(R.id.ETnoteTitle)
        noteDescriptionET = view.findViewById(R.id.ETnoteDescription)
        applyBTN = view.findViewById(R.id.BTNapply)

        applyBTN.setOnClickListener{ insertData() }
    }

    private fun insertData(){
        val noteTitle = noteTitleET.text.toString()
        val noteDescription = noteDescriptionET.text.toString()

        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
            viewModel.insert(Note(noteTitle, noteDescription))
            Toast.makeText(activity, "Note Added", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addFragment_to_mainFragment)
        }else{
            Toast.makeText(activity, "Enter Data", Toast.LENGTH_SHORT).show()
        }
    }
}