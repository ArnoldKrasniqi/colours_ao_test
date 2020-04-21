package com.example.colours.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.colours.view.ColoursActivity
import com.example.colours_ao_test.com.BASE_URL
import com.example.colours_ao_test.online_repo.OnlineWordClient
import com.example.colours_ao_test.online_repo.OnlineWordRepository
import com.example.colours_ao_test.view_model.ColoursActivityViewModel
import com.example.colours_ao_test.view_model.ColoursActivityViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ColoursActivityViewModelModule(private val coloursActivity: ColoursActivity){

    @Provides
    @Singleton
    fun providesViewModel(factory: ColoursActivityViewModelFactory) : ColoursActivityViewModel {
        return ViewModelProvider(coloursActivity,factory)
            .get(ColoursActivityViewModel::class.java)
    }

    @Provides
    fun providesViewModelFactory(onlineRepo: OnlineWordRepository) : ColoursActivityViewModelFactory {
        return ColoursActivityViewModelFactory(onlineRepo)
    }

    @Provides
    fun providesOnlineRepos(onlineWordClient : OnlineWordClient) : OnlineWordRepository {
        return OnlineWordRepository(onlineWordClient)
    }

    @Provides
    @Singleton
    fun providesClient(retrofit: Retrofit): OnlineWordClient {
        return retrofit.create(OnlineWordClient::class.java)
    }


    @Provides
    @Singleton
    fun providesRetrofit(client : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor : HttpLoggingInterceptor): OkHttpClient {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun providesInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }




}