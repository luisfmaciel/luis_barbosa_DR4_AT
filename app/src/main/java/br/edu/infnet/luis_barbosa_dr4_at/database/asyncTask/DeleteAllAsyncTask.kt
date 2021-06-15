package br.edu.infnet.luis_barbosa_dr4_at.database.asyncTask

import android.os.AsyncTask
import br.edu.infnet.luis_barbosa_dr4_at.database.dao.NoteDao

class DeleteAllAsyncTask (
    private val mAsyncTaskDao: NoteDao
) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg params: Void?): Void? {

        mAsyncTaskDao.deleteAll()

        return null
    }
}