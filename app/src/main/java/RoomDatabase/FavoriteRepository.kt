package RoomDatabase

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData

class FavoriteRepository(val context: Context) {
    private val favoriteRoomDB = initializeDB(context)
    private var allFavorite: LiveData<List<FavoriteEntity>>? = null

    private fun initializeDB(context: Context): FavoriteRoomDB? {
        return FavoriteRoomDB.getAppDatabase(context)
    }

    fun getAllFav(): LiveData<List<FavoriteEntity>>? {
        allFavorite = favoriteRoomDB!!.favoriteDao().getAllFavorite()
        return allFavorite
    }


    fun insertFav(favorite: FavoriteEntity) {
        favoriteRoomDB!!.favoriteDao().addFavorite(favorite)
    }

    fun deleteFav(favorite: FavoriteEntity) {
        favoriteRoomDB!!.favoriteDao().deleteFavorite(favorite)
    }
}