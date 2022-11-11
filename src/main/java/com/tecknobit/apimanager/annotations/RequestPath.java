package com.tecknobit.apimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation {@code "@RequestPath"} it is applied to those methods offered by libraries that allows to make a request. <br>
 * This annotation is useful to make the request path and its possible path parameters more readable
 * <pre>
 *     {@code
 *
 *         //with no parameters
 *         @RequestPath(path = "https://play.google.com/store/apps/developer")
 *         public void sendRequest() {
 *              APIRequest apiRequest = new APIRequest();
 *              apiRequest.sendAPIRequest("https://play.google.com/store/apps/developer", GET_METHOD);
 *         }
 *
 *         //with path parameters
 *         @RequestPath(path = "https://play.google.com/store/apps/developer?{id}")
 *         public void sendRequest() {
 *              APIRequest apiRequest = new APIRequest();
 *              apiRequest.sendAPIRequest("https://play.google.com/store/apps/developer?id=Tecknobit", GET_METHOD);
 *         }
 *     }
 * </pre>
 *
 * @author Tecknobit N7ghtm4r3
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface RequestPath {

    String path() default "";

}
