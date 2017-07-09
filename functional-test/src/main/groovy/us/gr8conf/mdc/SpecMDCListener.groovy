package us.gr8conf.mdc

import org.slf4j.MDC
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.FeatureInfo

class SpecMDCListener extends AbstractRunListener {

    boolean allFeatures = false
    List features = []

    SpecMDCListener(boolean allFeatures, List features) {
        this.allFeatures = allFeatures
        this.features = features
    }

    void beforeFeature(FeatureInfo featureInfo) {
        if (allFeatures || features.contains(featureInfo.name)) {
            MDC.put(SpecMDC.SPEC_NAME_KEY, featureInfo.name)
            MDC.put(SpecMDC.SPEC_CLASS_KEY, featureInfo.spec.name)
            MDC.put("loggingId", UUID.randomUUID().toString())
        }
    }

    void afterFeature(FeatureInfo featureInfo) {
        MDC.remove(SpecMDC.SPEC_NAME_KEY)
        MDC.remove(SpecMDC.SPEC_CLASS_KEY)
        MDC.remove("loggingId")
    }
}
