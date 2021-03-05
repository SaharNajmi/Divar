package RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("select * from RoomDataBase")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Insert
    fun addFavorite(favorite: FavoriteEntity?)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity?)
}
