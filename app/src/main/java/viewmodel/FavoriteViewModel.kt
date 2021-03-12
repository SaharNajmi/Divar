package viewmodel

import RoomDatabase.FavoriteEntity
import RoomDatabase.FavoriteRepository
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel : ViewModel() {

    private var liveDataFav: LiveData<List<FavoriteEntity>>? = null

    fun getAllFavorite(context: Context): LiveData<List<FavoriteEntity>>? {
        liveDataFav = FavoriteRepository(context).getAllFav()

        return liveDataFav
    }

    fun insertInformation(favorite: FavoriteEntity, context: Context) {
        FavoriteRepository(context).insertFav(favorite)
    }

    fun deleteInformation(favorite: FavoriteEntity, context: Context) {
        FavoriteRepository(context).deleteFav(favorite)
    }
}