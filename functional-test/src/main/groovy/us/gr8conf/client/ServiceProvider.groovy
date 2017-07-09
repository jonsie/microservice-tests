package us.gr8conf.client

import brave.http.HttpTracing
import brave.okhttp3.TracingInterceptor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.MDC
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class ServiceProvider {

    static String CORRELATION_ID_HEADER = "CORRELATION-ID"
    static String LOGGING_ID = "loggingId"

    static ObjectMapper objectMapper = new ObjectMapper()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    static Service service(String url, HttpTracing httpTracing) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient(httpTracing))
                .build()

        return RetrofitRequestLoggingProxy.create(url, retrofit.create(Service))
    }

    private static OkHttpClient okHttpClient(HttpTracing httpTracing) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(correlationLoggingInterceptor())
                .addInterceptor(httpLoggingInterceptor())
                .dispatcher(zipkinHttpTracingDispatcher(httpTracing))
                .addNetworkInterceptor(zipkinTracingInterceptor(httpTracing))
                .build()
    }

    private static Dispatcher zipkinHttpTracingDispatcher(HttpTracing httpTracing) {
        return new Dispatcher(httpTracing.tracing().currentTraceContext().executorService(new Dispatcher().executorService()))
    }

    private static Interceptor zipkinTracingInterceptor(HttpTracing httpTracing) {
        return TracingInterceptor.create(httpTracing)
    }

    private static Interceptor correlationLoggingInterceptor() {
        { chain ->
            Request original = chain.request()
            Request.Builder request = original.newBuilder().method(original.method(), original.body())

            if (MDC.get(LOGGING_ID) != null) {
                request.header(CORRELATION_ID_HEADER, MDC.get(LOGGING_ID))
            }

            return chain.proceed(request.build())
        }
    }

    private static HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
