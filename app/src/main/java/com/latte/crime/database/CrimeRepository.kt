package com.latte.crime.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.latte.crime.Crime
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "CrimeRepository"
private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context){

    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
//    ).build()
    ).addMigrations(migration_1_2).build() // 2버전 빌드

    private val crimeDao = database.crimeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    // Main 스레드에 영향을 주지 않는다.
    fun updateCrime(crime: Crime){
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime) {
        executor.execute{
            Log.d(TAG,"addCrime")
            crimeDao.addCrime(crime)
        }
    }

    companion object{
        private var INSTANCE: CrimeRepository? = null

        fun init(context: Context){
            if(INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository{
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository init")
        }
    }
}