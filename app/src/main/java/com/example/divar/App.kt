package com.example.divar

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.room.Room
import com.example.divar.data.db.AppDataBase
import com.example.divar.data.repository.BannerRepository
import com.example.divar.data.repository.UserRepository
import com.example.divar.data.repository.source.local.UserLocalDataSource
import com.example.divar.data.repository.source.remote.BannerRemoteDataSource
import com.example.divar.data.repository.source.remote.UserRemoteDataSource
import com.example.divar.service.ApiService
import com.example.divar.service.FrescoImageLoadingService
import com.example.divar.service.ImageLoadingService
import com.example.divar.service.createApiServiceInstance
import com.example.divar.ui.auth.UserViewModel
import com.example.divar.ui.favorite.FavoriteViewModel
import com.example.divar.ui.home.BannerDetailViewModel
import com.example.divar.ui.home.BannerViewModel
import com.example.divar.ui.message.MessageViewModel
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModule = module {
            single<ApiService> { createApiServiceInstance() }

            //room dao
            single { Room.databaseBuilder(this@App, AppDataBase::class.java, "db").build() }

            factory<BannerRepository> {
                BannerRepository(
                    get<AppDataBase>().bannerDao(),
                    BannerRemoteDataSource(get())
                )
            }

            //sharedPreferences
            single { this@App.getSharedPreferences("app", Context.MODE_PRIVATE) }

            single {
                UserRepository(
                    UserRemoteDataSource(get()),
                    UserLocalDataSource(get())
                )
            }

            //Fresco
            single<ImageLoadingService> { FrescoImageLoadingService() }

            viewModel { (city: String, category: String) -> BannerViewModel(get(), city, category) }
            viewModel { (bundle: Bundle) -> BannerDetailViewModel(bundle) }
            viewModel { FavoriteViewModel(get()) }
            viewModel { UserViewModel(get()) }
            viewModel { (myPhone: String, bannerId: Int) ->
                MessageViewModel(
                    get(),
                    myPhone,
                    bannerId
                )
            }
        }
        startKoin {
            androidContext(this@App)
            modules(myModule)
        }

        //auto login
        val userRepository: UserRepository = get()
        userRepository.checkLogin()
    }
}