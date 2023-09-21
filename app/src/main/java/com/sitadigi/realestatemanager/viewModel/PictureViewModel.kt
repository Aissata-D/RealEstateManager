package com.sitadigi.realestatemanager.viewModel

import androidx.lifecycle.ViewModel
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.repository.PictureRepository

class PictureViewModel(private val pictureRepository: PictureRepository): ViewModel() {

    suspend fun getAllPicture(): List<Picture> {
        return pictureRepository.getAllPicture()
    }

    suspend fun insert(picture: Picture) {
        return pictureRepository.insert(picture)
    }

    suspend fun getPictureById(id: Int): Picture? {
        return pictureRepository.getPictureById(id)
    }
}