package br.com.hivecode.supergames.data.api

import br.com.hivecode.supergames.BuildConfig
import br.com.hivecode.supergames.data.api.response.TopGamesResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object GameAPI{

    private const val ONE_MINUTE: Long = 60

    private val client = OkHttpClient.Builder()
        .readTimeout(ONE_MINUTE, TimeUnit.SECONDS)
        .connectTimeout(ONE_MINUTE, TimeUnit.SECONDS)
        .addInterceptor(createInterceptor())
        .build()

    private fun createInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain?.request()?.newBuilder()?.addHeader("Client-ID", "q8f67qp225g6s7f6oep37u8hjf39ox")?.build();
            chain?.proceed(request) }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()

    val gamesService = retrofit.create<GameService>(GameService::class.java)

    interface GameService {
        @Headers("accept:  application/vnd.twitchtv.v5+json")
        @GET("games/top/")
        fun getTopGames(@Query("offset") offset: Int = 0): Deferred<TopGamesResponse>
    }
}