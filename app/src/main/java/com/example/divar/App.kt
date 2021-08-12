package com.example.divar

import android.app.Application
import android.os.Bundle
import data.repository.BannerDataRepository
import data.repository.source.local.BannerLocalDataSource
import data.repository.source.remote.BannerRemoteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import service.ApiService
import service.createApiServiceInstance
import ui.home.BannerDetailViewModel
import ui.home.BannerViewModel

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val myModule = module {
            single<ApiService> { createApiServiceInstance() }
            single<BannerDataRepository> {
                BannerDataRepository(
                    BannerLocalDataSource(),
                    BannerRemoteDataSource(get())
                )
            }
            viewModel { (city: String, category: String) -> BannerViewModel(get(), city, category) }
            viewModel { (bundle: Bundle) -> BannerDetailViewModel(bundle) }
//            viewModel { (bundle: Bundle) -> BannerDetailViewModel(bundle) }
        }
        startKoin {
            androidContext(this@App)
            modules(myModule)
        }
/*       val myModule = module {

            single<ApiService> { createApiServiceInstance() }
            single<BannerRepository> {
                BannerRepositoryImplement(
                    BannerRemoteDataSource(get()),
                    BannerLocalDataSource()
                )
            }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }
*/
    }
}