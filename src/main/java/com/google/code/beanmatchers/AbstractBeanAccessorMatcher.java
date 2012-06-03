package com.google.code.beanmatchers;

import org.hamcrest.DiagnosingMatcher;

public abstract class AbstractBeanAccessorMatcher<T> extends DiagnosingMatcher<T> {
    TypeBasedValueGenerator valueGenerator;

    AbstractBeanAccessorMatcher(TypeBasedValueGenerator valueGenerator) {
        this.valueGenerator = valueGenerator;
    }

    protected boolean beanHasValidGetterAndSetterForProperty(JavaBean bean, String property) {
        try {
            Class<?> propertyType = bean.propertyType(property);
            Object testValue = valueGenerator.generate(propertyType);
            bean.setProperty(property, testValue);
            Object result = bean.getProperty(property);
            return (testValue.equals(result));
        } catch (AccessorMissingException e) {
            return false;
        }
    }
}
