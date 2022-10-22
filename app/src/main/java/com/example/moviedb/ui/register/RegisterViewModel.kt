package com.example.moviedb.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.model.AccountDao
import com.example.moviedb.model.AccountEntity
import kotlinx.coroutines.launch

class RegisterViewModel (
    val database : AccountDao, application: Application) : AndroidViewModel(application) {
    fun insertAccount(account: AccountEntity) {
        viewModelScope.launch {
            getData(account)
        }
    }

    private suspend fun getData(account: AccountEntity) {
        database.insertAccount(account)
    }

}