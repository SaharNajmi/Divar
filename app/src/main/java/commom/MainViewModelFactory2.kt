package commom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.db.RoomDatabase.FavoriteRepository
import feature.favorite.FavoriteViewModel

class MainViewModelFactory2(private val repository: FavoriteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
