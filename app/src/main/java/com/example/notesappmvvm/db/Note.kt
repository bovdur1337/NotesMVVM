package com.example.notesappmvvm.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notesTable")
data class Note(

    @ColumnInfo("title")
    val noteTitle: String,

    @ColumnInfo("description")
    val noteDescription: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)