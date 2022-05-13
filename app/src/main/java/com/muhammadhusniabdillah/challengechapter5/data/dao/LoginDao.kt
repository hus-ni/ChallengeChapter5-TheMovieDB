package com.muhammadhusniabdillah.challengechapter5.data.dao

import androidx.room.*
import com.muhammadhusniabdillah.challengechapter5.data.entity.Login
import kotlinx.coroutines.flow.Flow

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
    fun getName(email: String?): Flow<String>

    //update
    @Update
    fun updateData(updatedData: Login): Int

    //insert profile picture
    @Query("Update Login Set profilePict = :imgUri Where email = :email")
    fun updateProfilePicture(imgUri: String, email: String?)

    //taking the path
    @Query( "Select profilePict From Login Where email = :email")
    fun getPicturesPath(email: String?): Flow<String>

}


// Trash bin Trash bin Trash bin
//@Update(entity = Login::class)
//fun updateNameEmailPassword(obj: NameEmailPassword): Int
//
//@Entity
//class NameEmailPassword (
//    val name: String,
//    val email: String,
//    val password: String
//)

