package br.edu.infnet.luis_barbosa_dr4_at

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.luis_barbosa_dr4_at.Util.EXTRA_DATA
import br.edu.infnet.luis_barbosa_dr4_at.Util.EXTRA_ID
import br.edu.infnet.luis_barbosa_dr4_at.Util.EXTRA_IMAGEM
import br.edu.infnet.luis_barbosa_dr4_at.Util.EXTRA_LOCAL
import br.edu.infnet.luis_barbosa_dr4_at.Util.EXTRA_TEXTO
import br.edu.infnet.luis_barbosa_dr4_at.Util.EXTRA_TITULO
import br.edu.infnet.luis_barbosa_dr4_at.model.Note
import br.edu.infnet.luis_barbosa_dr4_at.viewModel.NoteViewModel
import br.edu.infnet.luis_barbosa_dr4_at.viewModel.UserViewModel
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var auth: FirebaseAuth
    private val ADD_REQUEST_CODE = 71
    private var adapter: NoteAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act)
            .get(UserViewModel::class.java)
        }

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        adapter = NoteAdapter() {
                partItem: Note -> partItemClicked(partItem)
        }

        auth = FirebaseAuth.getInstance()

        setupRecyclerView()
        fillUserData()
        subscribe()
        setupListeners()
        bannerAd()
    }

    private fun setupRecyclerView() {
        rv_notes.layoutManager = LinearLayoutManager(activity)
        rv_notes.adapter = adapter
    }

    private fun subscribe() {
        noteViewModel.getAllNotes().observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                tv_lista_vazia.visibility = View.VISIBLE
            } else {
                tv_lista_vazia.visibility = View.GONE
            }
            adapter!!.setNotes(it)
        })
    }

    private fun setupListeners() {
        fab_add_note.setOnClickListener {
            val intent = Intent(context, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST_CODE)
        }
        btn_versao_premium.setOnClickListener {
            layout_adView.visibility = View.GONE
            Toast.makeText(context, "Versão Premium liberada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun partItemClicked(note: Note) {
        val intent = Intent(context, AddNoteActivity::class.java)
        intent.putExtra(EXTRA_ID, note.id)
        intent.putExtra(EXTRA_TITULO, note.titulo)
        intent.putExtra(EXTRA_DATA, note.data)
        intent.putExtra(EXTRA_IMAGEM, note.foto)
        intent.putExtra(EXTRA_LOCAL, note.localizacao)
        intent.putExtra(EXTRA_TEXTO, note.texto)

        startActivityForResult(intent, ADD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK){
                if (requestCode == ADD_REQUEST_CODE) {
                    data.let {
                        val id = it!!.getIntExtra(EXTRA_ID, 0)
                        val titulo = it.getStringExtra(EXTRA_TITULO)
                        val date = it.getStringExtra(EXTRA_DATA)
                        val imagem = it.getStringExtra(EXTRA_IMAGEM)!!
                        val localizacao = it.getStringExtra(EXTRA_LOCAL)
                        val texto = it.getStringExtra(EXTRA_TEXTO)

                        val note = Note(
                            id,
                            titulo!!,
                            date!!,
                            imagem,
                            localizacao!!,
                            texto!!
                        )
                        if (id == 0) {
                            noteViewModel.insert(note)
                        } else {
                            noteViewModel.updateNote(note)
                        }
                    }
                }
            }
    }

    private fun fillUserData() {
        userViewModel.nome.observe(viewLifecycleOwner, Observer {
            if(it != null){
                tv_nome_user.text = it.toString()
            }
        })
        userViewModel.email.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                tv_email_user.text = it.toString()
            }
        })
        userViewModel.dataAtual.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                tv_data_user.text = it.toString()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater!!.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                auth.signOut()
                findNavController().navigate(R.id.action_noteFragment_to_signInFragment, null)
            }
        }
        return true
    }

    private fun bannerAd() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

}