package com.baylrock;

import java.lang.annotation.*;

@Retention( RetentionPolicy.RUNTIME )
@Target( {ElementType.FIELD, ElementType.PARAMETER} )
@Documented
public @interface Annotated {

    /**
     * Annotated Field name
     */
    String value() default "{0}"; //string will be formatted with MessageFormat where param - field name
}
