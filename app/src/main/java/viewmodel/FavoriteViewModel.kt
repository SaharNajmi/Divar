package viewmodel

import RoomDatabase.FavoriteEntity
import RoomDatabase.FavoriteRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

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