package com.muhammadhusniabdillah.challengechapter5.data

import android.app.Application

class ChapterFiveApplication : Application() {

    val database by lazy { ChapterFiveDatabase.getDatabase(this) }
}