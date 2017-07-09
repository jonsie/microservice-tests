package us.gr8conf

import brave.Span
import brave.Tracer
import brave.Tracing
import brave.http.HttpTracing
import brave.sampler.Sampler
import com.anotherchrisberry.spock.extensions.retry.RetryOnFailure
import spock.lang.Shared
import spock.lang.Specification
import us.gr8conf.client.Service
import us.gr8conf.client.ServiceProvider
import us.gr8conf.mdc.SpecMDC
import zipkin.reporter.Reporter

@SpecMDC
@RetryOnFailure(times=2)
class FunctionalTestSpecification extends Specification {

    @Shared Service service
    @Shared HttpTracing httpTracing

    Span span
    Tracer.SpanInScope spanInScope

    def setupSpec() {
        Tracing tracing = Tracing.newBuilder()
                .localServiceName("service")
                .reporter(getSpanReporter())
                .sampler(Sampler.ALWAYS_SAMPLE)
                .build()

        httpTracing = HttpTracing.create(tracing)

        service = ServiceProvider.service(serviceUrl(), httpTracing)
    }

    void cleanupSpec() {
        // No zipkin reporter to call flush on, since there is no Zipkin server to write too.
        // reporter.flush()
    }

    def setup() {
        span = httpTracing.tracing().tracer().newTrace()
                .kind(Span.Kind.SERVER)
                .name(specificationContext.currentSpec.name + "-" + specificationContext.currentFeature.name)
                .start()
        spanInScope = httpTracing.tracing().tracer().withSpanInScope(span)
    }

    void cleanup() {
        span.finish()
        spanInScope.close()
    }

    //There is not a Zipkin server to report spans too, since this is an example project.
    //In the real world, you would build up a reporter here.
    Reporter<zipkin.Span> getSpanReporter() {
        return Reporter.NOOP
    }

    def serviceUrl() {
        return "http://localhost:8118"
    }
}
