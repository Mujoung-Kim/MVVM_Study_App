package company.domain.mvvmstudyapp

import android.app.Application

import company.domain.mvvmstudyapp.data.databases.AppDatabases
import company.domain.mvvmstudyapp.data.network.MyApi
import company.domain.mvvmstudyapp.data.network.NetworkConnectionInterceptor
import company.domain.mvvmstudyapp.data.repositories.UserRepository
import company.domain.mvvmstudyapp.ui.auth.AuthViewModelFactory

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabases(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }

    }
}