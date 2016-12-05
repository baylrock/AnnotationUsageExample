package com.baylrock;

/**
 * Example Bean
 */
public class Bean {
    @Annotated("Test: {0}")
    String name;

    @Annotated("my grandmas {0}")
    String age;

    @Annotated("inner {0}")
    BeanTwo bean;


}
