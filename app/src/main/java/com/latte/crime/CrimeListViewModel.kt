package com.latte.crime

import androidx.lifecycle.ViewModel
import com.latte.crime.database.CrimeRepository

class CrimeListViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()

    val crimeListLiveData = crimeRepository.getCrimes()
}