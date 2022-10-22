package com.example.moviedb.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentProfileBinding
import com.example.moviedb.model.AccountEntity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val spLogin = "spLogin"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(spLogin, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            editor.clear()
            editor.apply()
        }

        binding.btnUpdate.setOnClickListener{ update() }

    }

    private fun update() {
        val username = binding.etUsername.text.toString()
        val email = sharedPreferences.getString("email_key", null)
        val password = sharedPreferences.getString("password_key", null)

//        profileViewModel.updateAccount(
//            AccountEntity(
//                username = username,
//                email = email,
//                password = password)
//        )

        sharedPreferences.edit {
            this.putString("username_key", username)
        }
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

}