package br.edu.infnet.luis_barbosa_dr4_at.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.edu.infnet.luis_barbosa_dr4_at.R
import br.edu.infnet.luis_barbosa_dr4_at.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private var mUser: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act)
            .get(UserViewModel::class.java)
        }
        fillUserData()
        setUpListeners()
    }

    private fun setUpListeners(){
        btnLogin.setOnClickListener {
            if (etEmailLogin.text!!.isNotEmpty() && etSenhaLogin.text!!.isNotEmpty()) {
                doLogIn()
            } else {
                Toast.makeText(context, "Fill all Fields", Toast.LENGTH_SHORT).show()
            }
        }
        tv_not_member.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registerFragment, null)
        }
    }

    private fun fillUserData() {
        userViewModel.email.observe(viewLifecycleOwner, Observer {
            if(it != null){
                etEmailLogin.setText(it.toString())
            }
        })
    }

    fun doLogIn() {
        auth.signInWithEmailAndPassword(etEmailLogin.text.toString(), etSenhaLogin.text.toString())
            .addOnSuccessListener {
                Toast.makeText(context, "Authentication Success", Toast.LENGTH_SHORT).show()
                mUser = auth.currentUser
                updateUI()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Authentication Failure", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onStart() {
        super.onStart()
        mUser = auth.currentUser
        updateUI()
    }

    private fun updateUI(){
        if (mUser != null) {
            userViewModel.email.value = mUser!!.email.toString()
            userViewModel.nome.value = mUser!!.displayName.toString()
            findNavController().navigate(R.id.action_signInFragment_to_noteFragment, null)
        }
    }
}