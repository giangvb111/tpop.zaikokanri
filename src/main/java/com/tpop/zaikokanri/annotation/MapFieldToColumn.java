package com.tpop.zaikokanri.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MapFieldToColumn {

    /**
     * name select database
     * @return String
     */
    String value() default "";

    /**
     * The header of the column code
     * @return String
     */
    String keyLabel() default "";

    /**
     * code multi-language
     * @return String
     */
    String keyLanguage() default "";
}
