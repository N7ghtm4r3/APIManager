package com.tecknobit.apimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code "@Wrapper"} annotation is applied to those wrapper methods that wrap the main method facilitating access to
 * this last one avoiding passing some useless arguments
 * <pre>
 *     {@code
 *          //This method is an example wrapper method
 *          //In this case the useless argument is the 0
 *         @Wrapper(wrapper_of = "sum(double x, double y, int decimals)")
 *         public double sum(double x, double y) {
 *              return sum(x, y, 0);
 *         }
 *
 *          //This method is an example wrapped method
 *          //In this case the wrapped argument is the decimals
 *         public double sum(double x, double y, int decimals) {
 *              return TradingTools.roundValue(x + y, decimals);
 *         }
 *
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit 
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Wrapper {

    /**
     * {@code wrapper_of} method wrapped by the method with this annotation
     */
    String wrapper_of() default "";

}
