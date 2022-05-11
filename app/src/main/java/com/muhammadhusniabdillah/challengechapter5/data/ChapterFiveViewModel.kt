package com.muhammadhusniabdillah.challengechapter5.data

import androidx.lifecycle.*
import com.muhammadhusniabdillah.challengechapter5.data.dao.LoginDao
import com.muhammadhusniabdillah.challengechapter5.data.entity.Login
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Constant
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Helper

class ChapterFiveViewModel(private val loginDao: LoginDao) : ViewModel() {


    private val sharedPref: Helper = Helper

    private val _name: LiveData<String> = getUserName(sharedPref.getEmail(Constant.EMAIL_USER))
    val name: LiveData<String> = _name

    // register
    fun userProfile(name: String, email: String, password: String) {
        val data = dataEntry(name, email, password)
        insertToDatabase(data)
    }

    fun userProfile(id: Int, name: String, email: String, password: String): Int {
        val data = dataEntry(id, name, email, password)
        return updateData(data)
    }

    private fun dataEntry(id: Int, name: String, email: String, password: String): Login {
        return Login(id, name, email, password)
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

    fun getUserProfile(email: String?): Login {
        return loginDao.getProfile(email)
    }

    private fun getUserName(email: String?): LiveData<String> {
        return loginDao.getName(email)
    }

    private fun updateData(data: Login): Int {
        return loginDao.updateData(data)
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