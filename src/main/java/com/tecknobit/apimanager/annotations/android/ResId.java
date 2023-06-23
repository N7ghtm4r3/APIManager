package com.tecknobit.apimanager.annotations.android;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code "@ResId"} annotation is applied to those parameters that are a resource (id, string, drawable, etc.)
 * of the Android's project
 * <pre>
 *     {@code
 *
 *          @ResId(R.id.mySomeViewId) -> Id of the view to use
 *          private View someView;
 *
 *          @ResId(R.string.myMessage) -> String resource to use
 *          private String message;
 *
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote This annotation is useful in an Android's environment
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface ResId {

    /**
     * {@code id} of the resource to use
     *
     * @apiNote (id, string, drawable, etc.)
     */
    int id() default 0;

    /**
     * {@code ids} of the resources to use
     *
     * @apiNote (id, string, drawable, etc.)
     */
    int[] ids() default {};

}
