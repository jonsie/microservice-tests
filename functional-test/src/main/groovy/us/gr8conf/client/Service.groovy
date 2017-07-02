package us.gr8conf.client

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import us.gr8conf.client.model.Model

interface Service {

    @POST("/post")
    abstract Call<Model> post(@Body Model model)

    @POST("/post")
    abstract Call<Model> post(@Body RequestBody requestBody)

    @GET("/get/{id}")
    abstract Call<Model> get(@Path("id") String id)
}
