package com.tecknobit.apimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code "@Structure"} annotation is applied to those classes that give a general structure and a general behavior in
 * a possible hierarchy and to its subclasses
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Structure {

}
