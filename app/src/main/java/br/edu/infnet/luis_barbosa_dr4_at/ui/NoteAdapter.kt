package br.edu.infnet.luis_barbosa_dr4_at.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.luis_barbosa_dr4_at.R
import br.edu.infnet.luis_barbosa_dr4_at.model.Note

class NoteAdapter(
    val onClickListener: (Note) -> Unit
)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var notesList: List<Note>? = null

    class NoteViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {

        var titulo: TextView = itemView.findViewById(R.id.tv_título_note)
        var data: TextView = itemView.findViewById(R.id.tv_date_note)
        var update: LinearLayout = itemView.findViewById(R.id.btn_enter_update)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.notes_card,
                parent,
                false
            )
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList!![position]

        holder.titulo.text = note.titulo
        holder.data.text = note.data

        holder.update.setOnClickListener {
            onClickListener(note)
        }
    }

    override fun getItemCount(): Int {
        return if (notesList != null){
            notesList!!.size
        } else {
            0
        }
    }

    fun setNotes(notes: List<Note>){
        notesList = notes
        notifyDataSetChanged()
    }
}