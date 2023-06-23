package com.tecknobit.apimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code "@RequestWeight"} annotation is applied to those methods offered by libraries that allow to make a request. <br>
 * This annotation is useful to display the request's weight on the API backend service
 * <pre>
 *     {@code
 *
 *         @RequestWeight(weight = "8")
 *         public void sendRequest() {
 *              APIRequest apiRequest = new APIRequest();
 *              apiRequest.sendAPIRequest("request_url", GET);
 *         }
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface RequestWeight {

    /**
     * {@code weight} value of the request
     */
    String weight();

}
