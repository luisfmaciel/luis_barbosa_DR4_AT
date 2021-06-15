package br.edu.infnet.luis_barbosa_dr4_at

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.edu.infnet.luis_barbosa_dr4_at.model.User
import br.edu.infnet.luis_barbosa_dr4_at.viewModel.UserViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act)
            .get(UserViewModel::class.java)
        }

        setUpListeners()
    }

    private fun setUpListeners() {
        btnSalvarCadastro.setOnClickListener {
            if (checkPassword()) {
                if (email_register_input.text!!.isNotEmpty() && name_register_input.text!!.isNotEmpty()) {
                    doRegister()
                } else {
                    Toast.makeText(context, "Fill all Fields", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Password Mismatch", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPassword(): Boolean {
        val passwordInput = senha_register_input.text.toString()
        val confirmInput = confirmaSenha_register_input.text.toString()
        val passwordREGEX: Pattern = Pattern.compile("""^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%¨&*()_+=-])(?=\S+$).{8,12}$""")
        val match : Matcher = passwordREGEX.matcher(passwordInput)
        var flag = true

        if (passwordInput != confirmInput) {
            flag = false
        }
        if(passwordInput.isEmpty()){
            flag = false
        }
        if(!match.matches()){
            flag = false
        }
        return flag
    }

    private fun doRegister() {
        auth.createUserWithEmailAndPassword(email_register_input.text.toString(), senha_register_input.text.toString())
            .addOnSuccessListener {
                val nome = name_register_input.text.toString()
                val user = auth.currentUser
                val profileUpdates = userProfileChangeRequest {
                    displayName = nome
                }
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                        }
                    }
                findNavController().navigate(R.id.action_registerFragment_to_signInFragment, null)
            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> Toast.makeText(context, "  Password length error", Toast.LENGTH_SHORT).show()
                    is FirebaseAuthUserCollisionException -> Toast.makeText(context, "E-mail already registered", Toast.LENGTH_SHORT).show()
                    is FirebaseNetworkException -> Toast.makeText(context, " Internet error", Toast.LENGTH_SHORT).show()
                    else ->  Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

}