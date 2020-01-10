package com.sngular.core.di

import com.sngular.core.arch.executor.JobThread
import com.sngular.core.arch.executor.UIThread
import com.sngular.core.arch.threads.JobScheduler
import com.sngular.core.arch.threads.UIScheduler
import com.sngular.core.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun providesHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient().newBuilder()
        .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS).addInterceptor(interceptor)
        .addInterceptor {
            val request = it.request()
            val builder = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
            it.proceed(builder.build())
        }.build()
}

fun providesRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


val coreModule = module {
    single { providesHttpClient() }
    single { providesRetrofit(get()) }
    single<JobScheduler> { JobThread() }
    single<UIScheduler> { UIThread() }
}