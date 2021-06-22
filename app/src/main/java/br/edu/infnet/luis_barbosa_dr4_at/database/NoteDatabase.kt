package br.edu.infnet.luis_barbosa_dr4_at.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.infnet.luis_barbosa_dr4_at.Util
import br.edu.infnet.luis_barbosa_dr4_at.database.dao.NoteDao
import br.edu.infnet.luis_barbosa_dr4_at.model.Note
import br.edu.infnet.luis_barbosa_dr4_at.viewModel.UserViewModel

@Database (
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "NoteDB")
            .build()
    }

}