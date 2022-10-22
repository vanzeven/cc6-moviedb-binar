package com.example.moviedb.ui.profile

import android.app.Application
import androidx.lifecycle.*
import com.example.moviedb.model.AccountDao
import com.example.moviedb.model.AccountEntity
import kotlinx.coroutines.launch

class ProfileViewModel (
    val database : AccountDao, application: Application) : AndroidViewModel(application) {
    fun updateAccount(account: AccountEntity) {
        viewModelScope.launch {
            getData(account)
        }
    }

    private suspend fun getData(account: AccountEntity) {
        database.updateAccount(account)
    }
}