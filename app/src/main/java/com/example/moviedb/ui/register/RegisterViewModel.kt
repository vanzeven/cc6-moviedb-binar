package com.example.moviedb.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.model.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val dataStoreManager: DataStoreManager): ViewModel() {
    fun saveAccount(username: String, password: String, email: String) {
        viewModelScope.launch {
            dataStoreManager.setUsername(username)
            dataStoreManager.setPassword(password)
            dataStoreManager.setEmail(email)
        }
    }

}