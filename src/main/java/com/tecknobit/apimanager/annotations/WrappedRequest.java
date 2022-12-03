package com.tecknobit.apimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code "@WrappedRequest"} annotation is applied to those methods offered by libraries where the original request of the
 * API service involved are {@code "wrapped"} by the methods of the library, that is, the possible combinations with any parameters
 * of the original request are easily added to this request with the {@code "wrapped"} method marked with this annotation
 * <pre>
 *     {@code
 *         // original params (NOT MANDATORY) of the API service: maxResults (int), includeAnyThing (boolean)
 *         // original request
 *         public void () {
 *             sendApiRequest();
 *         }
 *
 *         @WrappedRequest
 *         public void makeRequest(int maxResults){
 *             sendApiRequest(maxResults);
 *         }
 *
 *         @WrappedRequest
 *         public void makeRequest(boolean includeAnyThing){
 *             sendApiRequest(includeAnyThing);
 *         }
 *
 *         @WrappedRequest
 *         public void makeRequest(int maxResults, boolean includeAnyThing){
 *             sendApiRequest(maxResults, includeAnyThing);
 *         }
 *     }
 * </pre>
 *
 * @author Tecknobit N7ghtm4r3
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface WrappedRequest {
}
