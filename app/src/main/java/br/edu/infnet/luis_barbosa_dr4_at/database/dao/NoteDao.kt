package br.edu.infnet.luis_barbosa_dr4_at.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.edu.infnet.luis_barbosa_dr4_at.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: Note)

    @Update
    fun updateNote(notes: Note)

    @Delete
    fun deleteNote(notes: Note)

    @Query("DELETE FROM notes")
    fun deleteAll()

    @Query("SELECT * FROM notes")
    fun all() : LiveData<List<Note>>
}