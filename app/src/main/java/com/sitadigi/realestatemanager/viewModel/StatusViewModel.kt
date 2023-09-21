package com.sitadigi.realestatemanager.viewModel

import androidx.lifecycle.ViewModel
import com.sitadigi.realestatemanager.model.Status
import com.sitadigi.realestatemanager.repository.StatusRepository

class StatusViewModel(private val statusRepository: StatusRepository): ViewModel() {

    suspend fun insert(status: Status) {
        return statusRepository.insert(status)
    }

    suspend fun getStatusById(id: Int): Status? {
        return statusRepository.getStatusById(id)
    }
}