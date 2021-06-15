package br.edu.infnet.luis_barbosa_dr4_at.database.repository

import android.app.Application

import androidx.lifecycle.LiveData
import br.edu.infnet.luis_barbosa_dr4_at.Util.DELETE
import br.edu.infnet.luis_barbosa_dr4_at.Util.INSERT
import br.edu.infnet.luis_barbosa_dr4_at.Util.UPDATE
import br.edu.infnet.luis_barbosa_dr4_at.database.NoteDatabase
import br.edu.infnet.luis_barbosa_dr4_at.database.asyncTask.DeleteAllAsyncTask
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

    fun insert(rating: Note) {
        InsertAsyncTask(noteDao, INSERT).execute(rating)
    }

    fun deleteAll() {
        DeleteAllAsyncTask(noteDao).execute()
    }

    fun deleteNote(rating: Note) {
        InsertAsyncTask(noteDao, DELETE).execute(rating)
    }

    fun updateNote(rating: Note) {
        InsertAsyncTask(noteDao, UPDATE).execute(rating)
    }
}
