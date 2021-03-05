package viewmodel

import RoomDatabase.FavoriteDao
import RoomDatabase.FavoriteEntity
import RoomDatabase.FavoriteRoomDB
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteRoomDBViewModel:ViewModel() {
    private val favoriteDao: FavoriteDao
    val favoriteRoomDB: FavoriteRoomDB
    private val allFavorite: LiveData<List<FavoriteEntity>>

    init {
        favoriteRoomDB = FavoriteRoomDB.getAppDatabase(Application())!!
        favoriteDao = favoriteRoomDB.favoriteDao()
       // allFavorite = MutableLiveData()
      allFavorite = favoriteDao.getAllFavorite()
    }

    fun allFavorite(): LiveData<List<FavoriteEntity>> {
        return allFavorite
    }

    fun insertInformation(favorite: FavoriteEntity) {
        favoriteDao.addFavorite(favorite)
    }

    fun deleteInformation(favorite: FavoriteEntity) {
        favoriteDao.deleteFavorite(favorite)
    }
}