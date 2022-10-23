package com.example.moviedb.ui.profile

import android.app.Application
import androidx.lifecycle.*
import com.example.moviedb.model.AccountDao
import com.example.moviedb.model.AccountEntity
import com.example.moviedb.model.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val dataStoreManager: DataStoreManager, application: Application) : ViewModel() {
    fun updateAccount(account: AccountEntity) {
        viewModelScope.launch {
            getData(account)
        }
    }

    private suspend fun getData(account: AccountEntity) {
        database.updateAccount(account)
    }

    fun uploadImage(image: String) {
        viewModelScope.launch {
            dataStoreManager.setImage(image)
        }
    }
}