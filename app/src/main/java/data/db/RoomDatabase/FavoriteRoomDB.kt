package data.db.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteRoomDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        var favoriteRoomDB: FavoriteRoomDB? = null
        fun getAppDatabase(context: Context): FavoriteRoomDB? {

            if (favoriteRoomDB == null) {
                synchronized(FavoriteRoomDB::class.java) {
                    favoriteRoomDB = Room.databaseBuilder(
                        context,
                        FavoriteRoomDB::class.java,
                        "FAVORITE"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return favoriteRoomDB
        }
    }
}