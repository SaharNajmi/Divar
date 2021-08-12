package data.db.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("select * from RoomDataBase")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    //اگه لیست علاقه مندی این آیتم با این ای دی از قبل در دیتابیس موجود باشد replace میکنیم چون دیتای قبلی ممکنه عوض بشه و باید آپدیت باشه
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: FavoriteEntity?)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity?)
}

