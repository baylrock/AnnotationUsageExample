package com.baylrock;

/**
 * Created by baylrock on 28.11.2016.
 */
public class BeanTwo {

    @Annotated
    String some;

    String notsome;

    @Annotated("bean in {0}")
    Bean bean;
}
