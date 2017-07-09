package us.gr8conf

import com.anotherchrisberry.spock.extensions.retry.RetryOnFailure
import spock.lang.Shared
import spock.lang.Specification
import us.gr8conf.client.Service
import us.gr8conf.client.ServiceProvider
import us.gr8conf.mdc.SpecMDC

@SpecMDC
@RetryOnFailure(times=2)
class FunctionalTestSpecification extends Specification {

    @Shared Service service

    def setupSpec() {
        service = ServiceProvider.service(serviceUrl())
    }

    def serviceUrl() {
        return "http://localhost:8118"
    }
}
