package br.edu.infnet.luis_barbosa_dr4_at.database.asyncTask

import android.os.AsyncTask
import br.edu.infnet.luis_barbosa_dr4_at.database.dao.NoteDao

class DeleteTitleAsyncTask (
    private val mAsyncTaskDao: NoteDao,
) : AsyncTask<String, Unit, Unit >()
{
    override fun doInBackground(vararg params: String?) {
        mAsyncTaskDao.deleteNoteFromTitle(params[0]!!)

    }
}