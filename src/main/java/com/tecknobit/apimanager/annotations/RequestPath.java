package com.tecknobit.apimanager.annotations;

import com.tecknobit.apimanager.apis.APIRequest.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code "@RequestPath"} annotation is applied to those methods offered by libraries that allows to make a request. <br>
 * This annotation is useful to make the request path and its possible path parameters more readable
 * <pre>
 *     {@code
 *
 *         //with no parameters
 *         @RequestPath(method = GET, path = "https://play.google.com/store/apps/developer")
 *         public void sendRequest() {
 *              APIRequest apiRequest = new APIRequest();
 *              apiRequest.sendAPIRequest("https://play.google.com/store/apps/developer", GET);
 *         }
 *
 *         //with path parameters
 *         @RequestPath(method = GET, path = "https://play.google.com/store/apps/developer?{id}", path_parameters = "id")
 *         public void sendRequest() {
 *              APIRequest apiRequest = new APIRequest();
 *              apiRequest.sendAPIRequest("https://play.google.com/store/apps/developer?id=Tecknobit", GET);
 *         }
 *     }
 * </pre>
 *
 * @author Tecknobit N7ghtm4r3
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface RequestPath {

    /**
     * {@code method} of the request
     **/
    RequestMethod method();

    /**
     * {@code path} of the request
     **/
    String path() default "";

    /**
     * {@code path_parameters} path parameters of the request -> endpoint/{parameter}
     **/
    String path_parameters() default "";

    /**
     * {@code query_parameters} query parameters of the request -> endpoint?{parameter}&{parameter1}
     **/
    String query_parameters() default "";

    /**
     * {@code body_parameters} body parameters of the request ->
     * <pre>
     *     {@code
     *          {
     *              parameter : parameter value,
     *              parameter1 : parameter1 value
     *          }
     *     }
     * </pre>
     **/
    String body_parameters() default "";

}
