package com.tecknobit.apimanager.trading;

import com.tecknobit.apimanager.apis.ResourcesHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code CryptocurrencyTool} class is a useful tool class that helps to work with cryptocurrencies details. <br>
 * This tool helps to get image logo of a crypto, get full name of a crypto or get symbol of a crypto. <br>
 *
 * @author N7ghtm4r3 - Tecknobit
 **/
public abstract class CryptocurrencyTool {

    /**
     * {@code SYMBOL_KEY} is the instance that contains symbol key for {@link #coins} map
     **/
    private static final String SYMBOL_KEY = "symbol";

    /**
     * {@code NAME_KEY} is the instance that contains name key for {@link #coins} map
     **/
    private static final String NAME_KEY = "name";

    /**
     * {@code IMAGE_URL_KEY} is the instance that contains image url key for {@link #coins} map
     **/
    private static final String IMAGE_URL_KEY = "img_url";

    /**
     * {@code coins} is the instance that contains cryptocurrencies details
     **/
    private static final JSONArray coins;

    static {
        try {
            coins = new JSONArray(new Scanner(ResourcesHelper.getResourceFromStream(CryptocurrencyTool.class,
                    "coins.json")).useDelimiter("\\Z").next());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to fetch cryptocurrency name
     *
     * @param symbol: symbol of the cryptocurrency to fetch es. BTC
     * @return name of the cryptocurrency es. Bitcoin
     **/
    public static String getCryptocurrencyName(String symbol) {
        return getValue(SYMBOL_KEY, symbol, NAME_KEY);
    }

    /**
     * Method to fetch cryptocurrency symbol
     *
     * @param name: name of the cryptocurrency to fetch es. Bitcoin
     * @return symbol of the cryptocurrency es. BTC
     **/
    public static String getCryptocurrencySymbol(String name) {
        return getValue(NAME_KEY, name, SYMBOL_KEY);
    }

    /**
     * Method to fetch cryptocurrency url logo image by cryptocurrency symbol
     *
     * @param symbol: symbol of the cryptocurrency to fetch logo image es. BTC
     * @return image url of the cryptocurrency
     **/
    public static String getUrlLogoBySymbol(String symbol) {
        return getValue(SYMBOL_KEY, symbol, IMAGE_URL_KEY);
    }

    /**
     * Method to fetch cryptocurrency url logo image by cryptocurrency name
     *
     * @param name: name of the cryptocurrency to fetch logo image es. Bitcoin
     * @return image url of the cryptocurrency
     **/
    public static String getUrlLogoByName(String name) {
        return getValue(NAME_KEY, name, IMAGE_URL_KEY);
    }

    /**
     * Method to fetch detail from coins map
     *
     * @param researchKey:   key used to research the compare key
     * @param researchValue: value of researchKey
     * @param keyToFetch:    key of the detail to fetch
     * @return value of keyToFetch if exists as {@link String} and null if not exists
     **/
    private static String getValue(String researchKey, String researchValue, String keyToFetch) {
        researchValue = researchValue.toLowerCase();
        for (int j = 0; j < coins.length(); j++) {
            JSONObject coin = coins.getJSONObject(j);
            if (coin.getString(researchKey).toLowerCase().equals(researchValue))
                return coin.getString(keyToFetch);
        }
        return null;
    }

    /**
     * Method to fetch the quote symbol from a symbol
     *
     * @param symbol: symbol from fetch the quote
     * @return quote symbol -> {@code "BTCETH"} -> {@code "ETH"}
     **/
    public static String getQuoteFromSymbol(String symbol) {
        return getValueFromSymbol(symbol, true);
    }

    /**
     * Method to fetch the base symbol from a symbol
     *
     * @param symbol: symbol from fetch the base
     * @return base symbol -> {@code "BTCETH"} -> {@code "BTC"}
     **/
    public static String getBaseFromSymbol(String symbol) {
        return getValueFromSymbol(symbol, false);
    }

    /**
     * Method to fetch the base or the quote symbol from a symbol
     *
     * @param symbol:  symbol from fetch the value
     * @param isQuote: whether the researched value is a quote or is a base
     * @return value as follows:
     * <ul>
     *      <li>
     *          {@code "isQuote"} = {@code "true"}
     *          quote symbol -> {@code "BTCETH"} -> {@code "ETH"}
     *      </li>
     *      <li>
     *          {@code "isQuote"} = {@code "false"}
     *          base symbol -> {@code "BTCETH"} -> {@code "BTC"}
     *      </li>
     * </ul>
     **/
    private static String getValueFromSymbol(String symbol, boolean isQuote) {
        if (symbol == null)
            throw new IllegalArgumentException("Symbol inserted is not a valid symbol");
        String value;
        int length = symbol.length();
        int middle = length / 2;
        int start, end;
        if (isQuote) {
            start = middle;
            end = length;
        } else {
            start = 0;
            end = middle;
        }
        if (length % 2 == 0)
            value = symbol.substring(start, end);
        else {
            if (isQuote)
                value = symbol.substring(start + 1, end);
            else
                value = symbol.substring(start, end + 1);
        }
        for (int j = start; j < length && getCryptocurrencyName(value) == null; j++) {
            if (isQuote)
                value = symbol.substring(j - 1, end);
            else
                value = symbol.substring(start, end - j);
        }
        return value;
    }

}
