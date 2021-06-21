package br.edu.infnet.luis_barbosa_dr4_at.database.repository

import android.app.Application

import androidx.lifecycle.LiveData
import br.edu.infnet.luis_barbosa_dr4_at.Util.DELETE
import br.edu.infnet.luis_barbosa_dr4_at.Util.INSERT
import br.edu.infnet.luis_barbosa_dr4_at.Util.UPDATE
import br.edu.infnet.luis_barbosa_dr4_at.database.NoteDatabase
import br.edu.infnet.luis_barbosa_dr4_at.database.asyncTask.InsertAsyncTask
import br.edu.infnet.luis_barbosa_dr4_at.database.dao.NoteDao
import br.edu.infnet.luis_barbosa_dr4_at.model.Note

class NotesRepository(application: Application) {

    var noteDao: NoteDao

    var mAllNotes: LiveData<List<Note>>

    init {
        val db: NoteDatabase = NoteDatabase.invoke(application)
        noteDao = db.noteDao()
        mAllNotes = noteDao.all()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return mAllNotes
    }

    fun insert(note: Note) {
        InsertAsyncTask(noteDao, INSERT).execute(note)
    }

    fun deleteNote(note: Note) {
        InsertAsyncTask(noteDao, DELETE).execute(note)
    }

    fun updateNote(note: Note) {
        InsertAsyncTask(noteDao, UPDATE).execute(note)
    }
}
