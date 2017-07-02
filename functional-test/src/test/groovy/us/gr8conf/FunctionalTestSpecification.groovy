package us.gr8conf

import okhttp3.logging.HttpLoggingInterceptor
import spock.lang.Shared
import spock.lang.Specification

class FunctionalTestSpecification extends Specification {

    @Shared Service service

    def setupSpec() {
        service = ServiceProvider.service(serviceUrl(), HttpLoggingInterceptor.Level.BODY)
    }

    def serviceUrl() {
        return "http://localhost:8118"
    }
}
