package com.tecknobit.apimanager.Tools.Trading;

import java.util.ArrayList;

import static java.lang.Double.*;
import static java.lang.String.format;

/**
 * The {@code TradingTools} class is a useful class tool that allow you to manage trading data
 * @author Tecknobit N7ghtm4r3
 * **/

public class TradingTools {

    /** Method to get percent between two values
     * @param #startValue: first value to make compare
     * @param #lastValue: last value to compare and get percent by first value
     * @return percent value as double es. 8 or -8
     * @throws IllegalArgumentException if startValue or lastValue are negative
     * **/
    public double computeAssetPercent(double startValue, double lastValue){
        if(startValue < 0 || lastValue < 0)
            throw new IllegalArgumentException("startValue and lastValue must be positive");
        return (((lastValue * 100) / startValue) - 100);
    }

    /** Method to get percent between two values and round it
     * @param #startValue: first value to make compare
     * @param #lastValue: last value to compare and get percent by first value
     * @param #decimalDigits: number of digits to round final percent value
     * @return percent value as double es. 8 or -8
     * @throws IllegalArgumentException if startValue or lastValue are negative
     * **/
    public double computeAssetPercent(double startValue, double lastValue, int decimalDigits){
        if(decimalDigits < 0)
            throw new IllegalArgumentException("Decimal digits number cannot be less than 0");
        if(startValue < 0 || lastValue < 0)
            throw new IllegalArgumentException("startValue and lastValue must be positive");
        return roundValue((((lastValue * 100) / startValue) - 100), decimalDigits);
    }

    /** Method to round a value
     * @param #value: value to round
     * @param #decimalDigits: number of digits to round final percent value
     * @return value rounded with decimalDigits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * **/
    public double roundValue(double value, int decimalDigits){
        if(decimalDigits < 0)
            throw new IllegalArgumentException("Decimal digits number cannot be less than 0");
        return parseDouble(format("%."+decimalDigits+"f", value));
    }

    /** Method to round a value
     * @param #historicalValues: value to round
     * @param #lastValue: number of digits to round final percent value
     * @return value rounded with decimalDigits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * **/
    public double computeTPTOPAsset(ArrayList<Double> historicalValues, double lastValue, int intervalDays, int offsetRange){
        if(lastValue < 0)
            throw new IllegalArgumentException("lastValue must be positive");
        if(intervalDays <= 0)
            throw new IllegalArgumentException("intervalDays's gap must be more than 0");
        return computeTPTOPAsset(historicalValues.toArray(new Double[historicalValues.size()]),lastValue,intervalDays,offsetRange);
    }

    public double computeTPTOPAsset(Double[] historicalValues, double lastValue, int intervalDays, int offsetRange){
        if(lastValue < 0)
            throw new IllegalArgumentException("lastValue must be positive");
        if(intervalDays <= 0)
            throw new IllegalArgumentException("intervalDays's gap must be more than 0");
        double[] trends = new double[historicalValues.length];
        double sumTrends;
        double offset = (((lastValue * offsetRange) / 100) + lastValue);
        for (int j=0, i = 0; j < historicalValues.length; j++){
            double value = historicalValues[j];
            if(value <= offset && (j + intervalDays) < historicalValues.length){
                trends[i] = computeAssetPercent(value, historicalValues[j+intervalDays]);
                i++;
                j += j + intervalDays - 1;
            }
        }
        sumTrends = 0;
        for (double trend : trends)
            sumTrends += trend;
        return sumTrends/trends.length;
    }


}
