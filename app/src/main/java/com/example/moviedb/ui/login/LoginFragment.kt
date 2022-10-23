package com.example.moviedb.ui.login

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentLoginBinding
import com.example.moviedb.model.AppDatabase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val spLogin = "spLogin"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(spLogin, MODE_PRIVATE)

        val application = requireNotNull(this.activity).application
        val data = AppDatabase.getInstance(application).accountDatabaseDao()
        val viewModelFactory = LoginViewModelFactory(data, application)
        loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        binding.btnRegister.setOnClickListener{findNavController().navigate(R.id.action_loginFragment_to_registerFragment)}
        binding.btnLogin.setOnClickListener{login()}

        autoConnect()
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        loginViewModel.getAccountByEmail(email).observe(viewLifecycleOwner){
            if (it != null && it.email == email && it.password == password){
                findNavController().navigate(R.id.action_loginFragment_to_noteFragment)
                sharedPreferences.edit {
                    this.putString("username_key", it.username)
                    this.putString("email_key", it.email)
                    this.putString("password_key", it.password)
                }
            }
            else{
                Toast.makeText(requireContext(), "Akun tidak ditemukan atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun autoConnect(){

        val username = sharedPreferences.getString("username_key", null)

        if (username != null){
            findNavController().navigate(R.id.action_loginFragment_to_noteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}