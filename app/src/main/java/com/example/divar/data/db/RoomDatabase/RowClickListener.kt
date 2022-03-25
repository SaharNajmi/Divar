package com.example.divar.data.db.RoomDatabase

interface RowClickListener {
    fun onItemClick(item: FavoriteEntity)
    fun onDeleteClick(item: FavoriteEntity)
}