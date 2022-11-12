package com.tecknobit.apimanager.formatters;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

/**
 * The {@code ScientificNotationParser} class is a useful tool class to get a numeric value without scientific notation
 *
 * @author Tecknobit N7ghtm4r3
 **/
public abstract class ScientificNotationParser {

    /** Method to parse a numeric value and format without scientific notation
     * @param value: value to fetch without scientific notation
     * @return value as {@link String} (es.) 9.2221111228E-6 -> 0.0000092221111228
     * **/
    public static String sNotationParse(Number value){
        if(value == null)
            throw new IllegalArgumentException("Value cannot be null");
        if(!(value instanceof Integer)) {
            String number = valueOf(value);
            if(number.contains("E")) {
                int zeroCounter = parseInt(number.split("-")[1]);
                StringBuilder zeroBuffer = new StringBuilder();
                for (int j=0; j < zeroCounter - 1; j++)
                    zeroBuffer.append("0");
                return "0." + zeroBuffer + valueOf((double)value * Math.pow(10, zeroCounter))
                        .replace(".", "");
            }else {
                int integers = number.split("\\.")[0].length();
                return sNotationParse(integers, (((number.length()) - integers) - 1), value);
            }
        }
        return valueOf(value);
    }

    /** Method to parse a numeric value and format without scientific notation
     * @param decimals: number of digits after comma
     * @param value: value to fetch without scientific notation
     * @return value as {@link String} (es.) 9.2221111228E-6 -> 0.0000092221111228
     * **/
    public static String sNotationParse(int decimals, Number value){
        if(decimals < 0)
            throw new IllegalArgumentException("Decimals digits value must be positive");
        if(value == null)
            throw new IllegalArgumentException("Value cannot be null");
        if(!(value instanceof Integer))
            return String.format("%." + decimals + "f", value).replace(",", ".");
        return valueOf(value);
    }

    /** Method to parse a numeric value and format without scientific notation
     * @param integers: number of digits before comma
     * @param decimals: number of digits after comma
     * @param value: value to fetch without scientific notation
     * @return value as {@link String} (es.) 9.2221111228E-6 -> 0.0000092221111228
     * **/
    public static String sNotationParse(int integers, int decimals, Number value){
        if(integers < 0)
            throw new IllegalArgumentException("Integers digits value must be positive");
        if(decimals < 0)
            throw new IllegalArgumentException("Decimals digits value must be positive");
        if(value == null)
            throw new IllegalArgumentException("Value cannot be null");
        if(!(value instanceof Integer))
            return String.format("%"+ integers + "." + decimals + "f", value).replace(",", ".");
        return valueOf(value);
    }

}
