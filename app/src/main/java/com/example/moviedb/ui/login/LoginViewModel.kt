package com.example.moviedb.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedb.model.AccountDao
import com.example.moviedb.model.AccountEntity
import kotlinx.coroutines.launch

class LoginViewModel (
    val database : AccountDao, application: Application ) : AndroidViewModel(application) {
    fun getAccountByEmail(email: String) : LiveData<AccountEntity> {
        val dummy = MutableLiveData <AccountEntity>()
        viewModelScope.launch {
            dummy.value = getData(email)
        }
        return dummy
    }

    private suspend fun getData(email: String) : AccountEntity? {
        return database.getAccountyByEmail(email)
    }
}