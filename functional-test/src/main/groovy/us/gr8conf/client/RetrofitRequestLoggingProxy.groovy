package us.gr8conf.client

import org.slf4j.MDC
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.OPTIONS
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

import java.lang.annotation.Annotation
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class RetrofitRequestLoggingProxy implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RetrofitRequestLoggingProxy.class)

    private Object service
    private String baseUrl

    static <T> T create(String baseUrl, Object service) {
        return (T) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new RetrofitRequestLoggingProxy(baseUrl, service))
    }

    private RetrofitRequestLoggingProxy(String baseUrl, Object service) {
        this.baseUrl = baseUrl
        this.service = service
    }

    @Override
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] annotations = method.getAnnotations()
        String relativeUrl = null
        String httpMethod = null
        for (Annotation annotation : annotations) {
            if (annotation instanceof DELETE) {
                relativeUrl = ((DELETE) annotation).value()
                httpMethod = "DELETE"
                break
            } else if (annotation instanceof GET) {
                relativeUrl = ((GET) annotation).value()
                httpMethod = "GET"
                break
            } else if (annotation instanceof HEAD) {
                relativeUrl = ((HEAD) annotation).value()
                httpMethod = "HEAD"
                break
            } else if (annotation instanceof PATCH) {
                relativeUrl = ((PATCH) annotation).value()
                httpMethod = "PATCH"
                break
            } else if (annotation instanceof POST) {
                relativeUrl = ((POST) annotation).value()
                httpMethod = "POST"
                break
            } else if (annotation instanceof PUT) {
                relativeUrl = ((PUT) annotation).value()
                httpMethod = "PUT"
                break
            } else if (annotation instanceof OPTIONS) {
                relativeUrl = ((OPTIONS) annotation).value()
                httpMethod = "OPTIONS"
                break
            }
        }

        if (relativeUrl != null) {
            MDC.put("API-httpMethod", httpMethod)
            MDC.put("API-relativeUrl", relativeUrl)
            MDC.put("API-baseUrl", baseUrl)
            LOG.info("invoking API httpMethod={} relativeUrl={} baseUrl={}", httpMethod, relativeUrl, baseUrl)
        }

        Object result
        try {
            result = method.invoke(service, args)
        } catch (InvocationTargetException e) {
            throw e.getTargetException()
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage())
        } finally {
            MDC.remove("API-httpMethod")
            MDC.remove("API-relativeUrl")
            MDC.remove("API-baseUrl")
        }
        return result
    }
}
