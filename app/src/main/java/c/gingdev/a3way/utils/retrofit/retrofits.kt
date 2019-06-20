package c.gingdev.a3way.utils.retrofit

import c.gingdev.a3way.model.ArrivalData
import retrofit2.Call
import retrofit2.http.GET

class retrofits {
    private val retrofit = retrofitInjection
        .provideRetrofit("https://eef5dd73.ngrok.io")

    fun getRetrofitService() : retrofitService
            = retrofit.create(retrofitService::class.java)

    companion object {
        private var retrofitInstance: retrofits? = null

        fun getInstance(): retrofits
                = retrofitInstance ?: synchronized(this) {
            retrofitInstance ?: buildRetrofitInstance().also { retrofitInstance = it }
        }

        private fun buildRetrofitInstance(): retrofits
                = retrofits()
    }
}

interface retrofitService {
    @GET("/bus")
    fun getDatas(): Call<ArrivalData>
}
