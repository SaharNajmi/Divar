package viewmodel

import RoomDatabase.FavoriteDao
import RoomDatabase.FavoriteEntity
import RoomDatabase.FavoriteRepository
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel : ViewModel() {

    private var liveDataFav: LiveData<List<FavoriteEntity>>? = null
    private val favoriteDao: FavoriteDao? = null

    fun getAllFavorite(context: Context): LiveData<List<FavoriteEntity>>? {
        liveDataFav = FavoriteRepository.getAllFav(context)
        return liveDataFav
    }

    fun insertInformation(favorite: FavoriteEntity) {
        FavoriteRepository.insertFav(favorite)
    }

    fun deleteInformation(favorite: FavoriteEntity) {
        FavoriteRepository.deleteFav(favorite)
    }
}