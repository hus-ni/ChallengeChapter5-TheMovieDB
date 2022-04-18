package com.muhammadhusniabdillah.challengechapter5.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammadhusniabdillah.challengechapter5.data.entity.Login

@Dao
interface LoginDao {

    //register
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun registerUser(userData: Login)

    //cek user exist
    @Query("Select Exists(Select * From Login Where email = :email And password = :password)")
    fun getUser(email: String, password: String): Boolean

    //get user data on profile page
    @Query("Select * From Login Where email = :email")
    fun getProfile(email: String): List<Login>

    //update
    //delete
}