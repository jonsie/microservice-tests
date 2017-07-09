package us.gr8conf.mdc

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

class SpecMDCExtension extends AbstractAnnotationDrivenExtension<SpecMDC> {
    def allFeatures = false
    def features = []

    void visitFeatureAnnotation(SpecMDC annotation, FeatureInfo feature) {
        features << feature.name
    }

    void visitSpecAnnotation(SpecMDC annotation, SpecInfo spec) {
        allFeatures = true
    }

    void visitSpec(SpecInfo spec) {
        spec.addListener(new SpecMDCListener(allFeatures, features))
    }
}
