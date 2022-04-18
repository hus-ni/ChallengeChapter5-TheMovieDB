package com.muhammadhusniabdillah.challengechapter5.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammadhusniabdillah.challengechapter5.data.dao.LoginDao
import com.muhammadhusniabdillah.challengechapter5.data.entity.Login

class ChapterFiveViewModel(private val loginDao: LoginDao) : ViewModel() {

    // register
    fun addUserProfile(name: String, email: String, password: String) {
        val data = dataEntry(name, email, password)
        insertToDatabase(data)
    }

    private fun dataEntry(name: String, email: String, password: String): Login {
        return Login(
            id = null, name, email, password
        )
    }

    private fun insertToDatabase(data: Login) {
        loginDao.registerUser(data)
    }

    fun checkUserExists(email: String, password: String): Boolean {
        return loginDao.getUser(email, password)
    }

    fun getUserProfile(email: String) {
        loginDao.getProfile(email)
    }

    fun isInputEmpty(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Boolean {
        return !(name.isBlank() || email.isBlank() || password.isBlank() || passwordConfirm.isBlank())
    }

    fun isInputEmpty(
        email: String,
        password: String
    ): Boolean {
        return !(email.isBlank() || password.isBlank())
    }
}

class ChapterFiveViewModelFactory(private val loginDao: LoginDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChapterFiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChapterFiveViewModel(loginDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}