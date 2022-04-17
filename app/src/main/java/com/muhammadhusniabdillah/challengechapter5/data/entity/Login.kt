package com.muhammadhusniabdillah.challengechapter5.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Login(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo val name: String,
    @ColumnInfo val email: String,
    @ColumnInfo val password: String
): Parcelable
