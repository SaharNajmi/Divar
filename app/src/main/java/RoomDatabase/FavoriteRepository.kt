package RoomDatabase

import android.content.Context
import androidx.lifecycle.LiveData

class FavoriteRepository {

    companion object {
        var favoriteRoomDB: FavoriteRoomDB? = null
        private var allFavorite: LiveData<List<FavoriteEntity>>? = null

        private fun initializeDB(context: Context): FavoriteRoomDB? {
            return FavoriteRoomDB.getAppDatabase(context)
        }

        fun getAllFav(context: Context) :LiveData<List<FavoriteEntity>>? {
            favoriteRoomDB = initializeDB(context)
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
}