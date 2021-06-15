package br.edu.infnet.luis_barbosa_dr4_at.database.asyncTask

import android.os.AsyncTask
import br.edu.infnet.luis_barbosa_dr4_at.Util.DELETE
import br.edu.infnet.luis_barbosa_dr4_at.Util.INSERT
import br.edu.infnet.luis_barbosa_dr4_at.Util.UPDATE
import br.edu.infnet.luis_barbosa_dr4_at.database.dao.NoteDao
import br.edu.infnet.luis_barbosa_dr4_at.model.Note

class InsertAsyncTask (
    private val mAsyncTaskDao: NoteDao,
    private val DBOperation: Int
) : AsyncTask<Note, Void, Void>()
{
    override fun doInBackground(vararg params: Note): Void? {
        when (DBOperation) {
            INSERT -> mAsyncTaskDao.insert(params[0])
            UPDATE -> mAsyncTaskDao.updateNote(params[0])
            DELETE -> mAsyncTaskDao.deleteNote(params[0])
        }
        return null
    }
}