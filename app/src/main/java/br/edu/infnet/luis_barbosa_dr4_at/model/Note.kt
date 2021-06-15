package br.edu.infnet.luis_barbosa_dr4_at.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "notes"
)
class Note (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val titulo: String = "",
    val data: String = "",
    val foto: String = "",
    val localizacao: String = "",
    val texto: String = ""
)


