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
    suspend fun getPictureLastId(): Int {
        return pictureRepository.getPictureLastId()
    }

    suspend fun getListOfPictureByFkId(fk_id : Int): List<Picture> {
        return pictureRepository.getListOfPictureByFkId(fk_id)
    }

    suspend fun deletePictureById(id : Int){
        return pictureRepository.deletePictureById(id)
    }


}