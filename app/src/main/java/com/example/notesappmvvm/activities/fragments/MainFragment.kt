package com.example.notesappmvvm.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappmvvm.R
import com.example.notesappmvvm.db.Note
import com.example.notesappmvvm.model.NoteViewModel
import com.example.notesappmvvm.rv_adapter.NoteClickDeleteInterface
import com.example.notesappmvvm.rv_adapter.NoteClickInterface
import com.example.notesappmvvm.rv_adapter.NoteRVadapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(), NoteClickInterface, NoteClickDeleteInterface {

    private lateinit var notesRV: RecyclerView
    private lateinit var addNoteFAB: FloatingActionButton
    private val viewModel: NoteViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "My Notes"

        addNoteFAB = view.findViewById(R.id.FABaddNote)
        notesRV = view.findViewById(R.id.RVnotes)

        addNoteFAB.setOnClickListener{ addNote() }

        //all for our recycler view
        notesRV.layoutManager = LinearLayoutManager(activity)
        val noteAdapter = NoteRVadapter(this.requireContext(), this, this)
        notesRV.adapter = noteAdapter

        //observer for our view model
        viewModel.allNotes.observe(viewLifecycleOwner, Observer{list ->
            list?.let{
                noteAdapter.updateList(it)
            }
        })

    }

    private fun addNote(){
        findNavController().navigate(R.id.action_mainFragment_to_addFragment)
    }

    override fun onNoteClick(note: Note) {
        val action_main_to_edit =
            MainFragmentDirections.actionMainFragmentToEditFragment(
                note.noteTitle, note.noteDescription, note.id
            )
        findNavController().navigate(action_main_to_edit)
    }

    override fun onDeleteClick(note: Note) {
        viewModel.delete(note)
        Toast.makeText(activity, "${note.noteTitle} deleted", Toast.LENGTH_SHORT)
    }
}