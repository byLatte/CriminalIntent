package com.latte.crime

import android.app.Application
import android.util.Log
import com.latte.crime.database.CrimeRepository

private const val TAG = "CrimeApplication"
class CrimeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG," onCreate() !!")
        CrimeRepository.init(this)
    }
}