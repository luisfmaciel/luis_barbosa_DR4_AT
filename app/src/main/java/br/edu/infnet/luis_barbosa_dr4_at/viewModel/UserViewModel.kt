package br.edu.infnet.luis_barbosa_dr4_at.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.util.*

class UserViewModel : ViewModel() {
    val nome = MutableLiveData<String>().apply { value = "" }
    val email = MutableLiveData<String>().apply { value = "" }
    val dataAtual = MutableLiveData<String>().apply { value = getDate() }


    private fun getDate(): String {
        val date = Calendar.getInstance().time
        return DateFormat.getDateInstance(DateFormat.LONG).format(date)
    }

}