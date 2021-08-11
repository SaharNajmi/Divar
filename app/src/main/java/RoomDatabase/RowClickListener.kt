package RoomDatabase

interface RowClickListener {
    fun onItemClick(item: FavoriteEntity)
    fun onDeleteClick(item: FavoriteEntity)
}