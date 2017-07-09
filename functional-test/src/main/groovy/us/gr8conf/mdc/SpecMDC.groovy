package us.gr8conf.mdc

import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.METHOD])

@ExtensionAnnotation(SpecMDCExtension)
@interface SpecMDC {
    static final String SPEC_NAME_KEY = 'SpecName'
    static final String SPEC_CLASS_KEY = 'SpecClass'
}
