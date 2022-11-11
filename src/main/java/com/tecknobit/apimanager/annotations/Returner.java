package com.tecknobit.apimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation {@code "@Returner"} it is applied to those methods offered by libraries in which the response of an API request
 * is processed and formatted according to a specific type of format
 * <pre>
 *     {@code
 *          //This method is an example returner method
 *          //ReturnFormat is for the example, but could be any type of formatter
 *         @Returner
 *         private <T> T returnResponseFormatted(T responseDetails, ReturnFormat format) {
 *              switch (format) {
 *                  case JSON:
 *                       return // response formatted as JSON
 *                  case LIBRARY_OBJECT:
 *                       return // response formatted as object given by the library
 *                  default: // this case is the STRING case
 *                      return // response formatted as simple string
 *              }
 *         }
 *     }
 * </pre>
 *
 * @author Tecknobit N7ghtm4r3
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Returner {

    /**
     * List of formatters offered by library to format the responses as user wants
     *
     * @apiNote <ul>
     * <li>
     * STRING -> returns the response formatted as {@link String}
     * </li>
     * <li>
     * JSON -> returns the response formatted as {@code "JSON"}
     * </li>
     * <li>
     * LIBRARY_OBJECT -> returns the response formatted as custom object offered by library that uses this list
     * </li>
     * </ul>
     **/
    enum ReturnFormat {
        STRING,
        JSON,
        LIBRARY_OBJECT
    }

}
