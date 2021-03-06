package com.muhammadhusniabdillah.challengechapter5.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
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
    fun getProfile(email: String?): Login

    @Query("Select name From Login Where email = :email")
    fun getName(email: String?): LiveData<String>

    //update
    @Update
    fun updateData(updatedData: Login): Int
    //delete
}