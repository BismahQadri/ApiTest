package com.example.apiintegrationtask

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Sairah
 * Created by EResolute on 8/11/2018.
 */
class ApiClient {
    companion object {
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://hn.algolia.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpBuilder().build())
                .build()
        }

        /**
         * @param timeout TimeOut in seconds
         */
        private fun getOkHttpBuilder(timeout: Int = 120): OkHttpClient.Builder {

            val builder = OkHttpClient.Builder()
            val dispatcher = Dispatcher()
            builder.dispatcher(dispatcher)

            builder.connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
            builder.readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(timeout.toLong(), TimeUnit.SECONDS)

            builder.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }

            return builder
        }

        private fun getOkHttpBuilder(
            token: String,
            sessionId: String,
            connectTimeout: Int = 120,
            readTimeout: Int = 120,
            writeTimeout: Int = 120
        ): OkHttpClient.Builder {

            val builder = OkHttpClient.Builder()
            val dispatcher = Dispatcher()
            builder.dispatcher(dispatcher)

            builder.connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
            builder.readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)

            builder.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("x-access-token", token)
                    .header("session-id", sessionId)
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }

            return builder
        }

        @Volatile private var instance: Retrofit? = null

        fun getInstance(
            token: String,
            sessionId: String,
            connectTimeout: Int = 120,
            readTimeout: Int = 120,
            writeTimeout: Int = 120
        ): Retrofit = instance ?: synchronized(this) {
            Retrofit.Builder()
                .baseUrl("https://hn.algolia.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpBuilder(token, sessionId, connectTimeout, readTimeout, writeTimeout).build())
                .build()
                .also {
                    instance = it
                }
        }

        fun clearInstance() {
            instance = null
        }

    }
}