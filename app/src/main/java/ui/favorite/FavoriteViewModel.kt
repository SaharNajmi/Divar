package ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import data.db.RoomDatabase.FavoriteEntity
import data.db.RoomDatabase.FavoriteRepository

class FavoriteViewModel : ViewModel {
    var favoriteRepository: FavoriteRepository

    constructor(_data: FavoriteRepository) {
        favoriteRepository = _data
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>>? {
        return favoriteRepository.getAllFav()
    }

    fun insertInformation(favorite: FavoriteEntity) {
        favoriteRepository.insertFav(favorite)
    }

    fun deleteInformation(favorite: FavoriteEntity) {
        favoriteRepository.deleteFav(favorite)
    }
}