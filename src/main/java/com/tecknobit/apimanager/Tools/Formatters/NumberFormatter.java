package com.tecknobit.apimanager.Tools.Formatters;

import static java.lang.String.valueOf;

public abstract class NumberFormatter {

    public static  <T extends Number> String format(T value, boolean numericFormat){
        if(value == null)
            throw new IllegalArgumentException("Value cannot be null");
        if(!(value instanceof Integer)) {
            final String[] valueDetails = valueOf(value).split("\\.");
            System.out.println(valueDetails[0].length());
            System.out.println("a" + valueDetails[1]);
            return format(valueDetails[0].length(), valueDetails[1].length(), value, numericFormat);
        }
        return valueOf(value);
    }

    public static <T extends Number> String format(int integers, int decimals, T value, boolean numericFormat){
        if(integers < 0)
            throw new IllegalArgumentException("Integers digits value must be positive");
        if(decimals < 0)
            throw new IllegalArgumentException("Decimals digits value must be positive");
        if(value == null)
            throw new IllegalArgumentException("Value cannot be null");
        if(!(value instanceof Integer)) {
            String formattedNumber = String.format("%"+ integers + "." + decimals + "f", value);
            if(numericFormat)
                return formattedNumber.replace(",", ".");
            return formattedNumber;
        }
        return valueOf(value);
    }

}
