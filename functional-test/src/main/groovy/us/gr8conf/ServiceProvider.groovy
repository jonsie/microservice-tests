package us.gr8conf

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class ServiceProvider {

    static ObjectMapper objectMapper = new ObjectMapper()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    static Service service(String url, HttpLoggingInterceptor.Level logLevel) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
        logging.setLevel(logLevel)

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(new OkHttpClient().newBuilder().addInterceptor(logging).build())
                .build()
                .create(Service)
    }
}
