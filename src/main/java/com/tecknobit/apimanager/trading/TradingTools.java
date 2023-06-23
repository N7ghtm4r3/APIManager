package com.tecknobit.apimanager.trading;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.String.format;

/**
 * The {@code TradingTools} class is a useful tool class that allows you to manage trading data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
public abstract class TradingTools {

    /**
     * Method to get percent between two values
     *
     * @param startValue: first value to make compare
     * @param lastValue:  last value to compare and get percent by first value
     * @return percent value as double es. 8 or -8
     * @throws IllegalArgumentException if startValue or lastValue are negative
     */
    public static double computeAssetPercent(double startValue, double lastValue) {
        if (startValue < 0 || lastValue < 0)
            throw new IllegalArgumentException("Start value and last value must be positive");
        if (startValue == 0 && lastValue == 0)
            return 0;
        return (((lastValue * 100) / startValue) - 100);
    }

    /**
     * Method to get percent between two values and round it
     *
     * @param startValue:    first value to make compare
     * @param lastValue:     last value to compare and get percent by first value
     * @param decimalDigits: number of digits to round final percent value
     * @return percent value as double es. 8 or -8
     * @throws IllegalArgumentException if startValue or lastValue are negative
     */
    public static double computeAssetPercent(double startValue, double lastValue, int decimalDigits) {
        return roundValue(computeAssetPercent(startValue, lastValue), decimalDigits);
    }

    /**
     * Method to get percent between two values and textualize it
     *
     * @param startValue: first value to make compare
     * @param lastValue:  last value to compare and get percent by first value
     * @return percent value es. +8% or -8% as {@link String}
     */
    public static String textualizeAssetPercent(double startValue, double lastValue) {
        return textualizeAssetPercent(computeAssetPercent(startValue, lastValue));
    }

    /**
     * Method to get percent between two values and textualize it
     *
     * @param startValue:    first value to make compare
     * @param lastValue:     last value to compare and get percent by first value
     * @param decimalDigits: number of digits to round final percent value
     * @return percent value es. +8% or -8% as {@link String}
     */
    public static String textualizeAssetPercent(double startValue, double lastValue, int decimalDigits) {
        return textualizeAssetPercent(computeAssetPercent(startValue, lastValue, decimalDigits));
    }

    /**
     * Method get percent between two values and textualize it
     *
     * @param percent:       value to compute
     * @param decimalDigits: number of digits to round final value
     * @return percent value formatted es. +8% or -8% as {@link String}
     */
    public static String textualizeAssetPercent(double percent, int decimalDigits) {
        return textualizeAssetPercent(roundValue(percent, decimalDigits));
    }

    /**
     * Method to get proportion between two values
     *
     * @param startValue: first value to make compare
     * @param lastValue:  last value to compare and calculate proportion with start value
     * @return proportion value as double
     * @throws IllegalArgumentException if startValue or lastValue are negative
     */
    public static double computeProportion(double startValue, double lastValue) {
        return computeAssetPercent(startValue, lastValue) + 100;
    }

    /**
     * Method to get proportion between two values and round it
     *
     * @param startValue:    first value to make compare
     * @param lastValue:     last value to compare and calculate proportion with start value
     * @param decimalDigits: number of digits to round final percent value
     * @return proportion value as double
     * @throws IllegalArgumentException if startValue or lastValue are negative
     */
    public static double computeProportion(double startValue, double lastValue, int decimalDigits) {
        return computeAssetPercent(startValue, lastValue, decimalDigits) + 100;
    }

    /**
     * Method get percent between two values and textualize it
     *
     * @param percent: value to compute
     * @return percent value formatted es. +8% or -8% as {@link String}
     */
    public static String textualizeAssetPercent(double percent) {
        if (percent > 0)
            return "+" + percent + "%";
        else if (percent < 0)
            return percent + "%";
        else
            return "=" + percent + "%";
    }

    /**
     * Method to round a value
     *
     * @param value:         value to round
     * @param decimalDigits: number of digits to round final value
     * @return value rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public static double roundValue(double value, int decimalDigits) {
        if (decimalDigits < 0)
            throw new IllegalArgumentException("Decimal digits number cannot be less than 0");
        return parseDouble(format("%." + decimalDigits + "f", value).replace(",","."));
    }

    /** Method to percentualize a value
     * @param startValue: value to percentualize
     * @param percentualizer: percent slice to add or remove from start value
     * @return percentualized value as double es. <br>
     * <table>
     *     <th>StartValue</th>
     *     <th>Percentualizer</th>
     *     <th>Result</th>
     *     <tr>
     *          <td>
     *              100
     *          </td>
     *          <td>
     *              +10
     *          </td>
     *          <td>
     *              110
     *          </td>
     *     </tr>
     *     <tr>
     *          <td>
     *              100
     *          </td>
     *          <td>
     *              -10
     *          </td>
     *          <td>
     *              90
     *          </td>
     *     </tr>
     * </table>
     * */
    public static double getPercentualizedValue(double startValue, double percentualizer){
        if(startValue < 0)
            throw new IllegalArgumentException("Start value must be positive");
        if(percentualizer < -100)
            throw new IllegalArgumentException("Percentualizer cannot be lesser than -100");
        double slice = startValue * Math.abs(percentualizer) / 100;
        if(percentualizer > 0)
            return startValue + slice;
        else
            return startValue - slice;
    }

    /** Method to percentualize a value
     * @param startValue: value to percentualize
     * @param percentualizer: percent slice to add or remove from start value
     * @param decimalDigits: number of digits to round final value
     * @return percentualized value as double es. <br>
     * <table>
     *     <th>StartValue</th>
     *     <th>Percentualizer</th>
     *     <th>Result</th>
     *     <th>Decimal digits</th>
     *     <tr>
     *          <td>
     *              101
     *          </td>
     *          <td>
     *              +2.5
     *          </td>
     *          <td>
     *              103.52
     *          </td>
     *          <td>
     *              2
     *          </td>
     *     </tr>
     *     <tr>
     *          <td>
     *              100
     *          </td>
     *          <td>
     *              -10
     *          </td>
     *          <td>
     *              98.47
     *          </td>
     *          <td>
     *              2
     *          </td>
     *     </tr>
     * </table>
     * */
    public static double getPercentualizedValue(double startValue, double percentualizer, int decimalDigits) {
        return roundValue(getPercentualizedValue(startValue, percentualizer), decimalDigits);
    }

    /**
     * Method to get forecast of an asset in base of days gap inserted
     *
     * @param historicalValues: previous values of asset used to compute forecast
     * @param lastValue:        last value of the asset to compare and fetch forecast
     * @param intervalDays:     days gap for the forecast range
     * @param offsetRange:      tolerance for select similar value compared to lastValue inserted
     * @return forecast value as a double es. 8 or -8
     * @throws IllegalArgumentException if lastValue is negative or intervalDays are less or equal to 0
     */
    public static double computeTPTOPIndex(ArrayList<Double> historicalValues, double lastValue, int intervalDays,
                                           double offsetRange) {
        if (lastValue < 0)
            throw new IllegalArgumentException("Last asset value must be positive");
        if (intervalDays <= 0)
            throw new IllegalArgumentException("Interval days value gap must be more than 0");
        return computeTPTOPIndex(historicalValues.toArray(new Double[historicalValues.size()]), lastValue, intervalDays,
                offsetRange);
    }

    /**
     * Method to get forecast of an asset in base of days gap inserted and round it
     *
     * @param historicalValues: previous values of asset used to compute forecast
     * @param lastValue:        last value of the asset to compare and fetch forecast
     * @param intervalDays:     days gap for the forecast range
     * @param offsetRange:      tolerance for select similar value compared to lastValue inserted
     * @param decimalDigits:    number of digits to round final forecast value
     * @return forecast value as a double es. 8 or -8
     * @throws IllegalArgumentException if lastValue is negative or intervalDays are less or equal to 0
     */
    public static double computeTPTOPIndex(ArrayList<Double> historicalValues, double lastValue, int intervalDays,
                                           double offsetRange, int decimalDigits) {
        return roundValue(computeTPTOPIndex(historicalValues, lastValue, intervalDays, offsetRange), decimalDigits);
    }

    /**
     * Method to get forecast of an asset in base of days gap inserted
     *
     * @param historicalValues: previous values of asset used to compute forecast
     * @param lastValue:        last value of the asset to compare and fetch forecast
     * @param intervalDays:     days gap for the forecast range
     * @param offsetRange:      tolerance for select similar value compared to lastValue inserted
     * @return forecast value as a double es. 8 or -8
     * @throws IllegalArgumentException if lastValue is negative or intervalDays are less or equal to 0
     */
    public static double computeTPTOPIndex(Double[] historicalValues, double lastValue, int intervalDays,
                                           double offsetRange) {
        if (lastValue < 0)
            throw new IllegalArgumentException("Last asset value must be positive");
        if (intervalDays <= 0)
            throw new IllegalArgumentException("Interval days value gap must be more than 0");
        double[] trends = new double[historicalValues.length];
        double sumTrends;
        double offset = (((lastValue * offsetRange) / 100) + lastValue);
        for (int j = 0, i = 0; j < historicalValues.length; j++) {
            double value = historicalValues[j];
            if (value <= offset && (j + intervalDays) < historicalValues.length) {
                trends[i] = computeAssetPercent(value, historicalValues[j + intervalDays]);
                i++;
                j += j + intervalDays - 1;
            }
        }
        sumTrends = 0;
        for (double trend : trends)
            sumTrends += trend;
        return sumTrends / trends.length;
    }

    /**
     * Method to get forecast of an asset in base of days gap inserted and round it
     *
     * @param historicalValues: previous values of asset used to compute forecast
     * @param lastValue:        last value of the asset to compare and fetch forecast
     * @param intervalDays:     days gap for the forecast range
     * @param offsetRange:      tolerance for select similar value compared to lastValue inserted
     * @param decimalDigits:    number of digits to round final forecast value
     * @return forecast value as a double es. 8 or -8
     * @throws IllegalArgumentException if lastValue is negative or intervalDays are less or equal to 0
     */
    public static double computeTPTOPIndex(Double[] historicalValues, double lastValue, int intervalDays, double offsetRange,
                                           int decimalDigits) {
        return roundValue(computeTPTOPIndex(historicalValues, lastValue, intervalDays, offsetRange), decimalDigits);
    }

}
