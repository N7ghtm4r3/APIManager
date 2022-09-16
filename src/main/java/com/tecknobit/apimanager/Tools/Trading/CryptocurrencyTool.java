package com.tecknobit.apimanager.Tools.Trading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The {@code CryptocurrencyTool} class is a useful class tool that helps to work with cryptocurrencies details. <br>
 * This tool helps to get image logo of a crypto, get full name of a crypto or get symbol of a crypto. <br>
 *
 * @author Tecknobit N7ghtm4r3
 **/

public abstract class CryptocurrencyTool {

    /**
     * {@code coins} is the instance that contains cryptocurrencies details
     **/
    private static final JSONArray coins;

    static {
        try {
            coins = new JSONArray(loadCoinsMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
     * Method to load coins map for workflow of the tool <br>
     * Any params required
     *
     * @return map loaded as {@link String}
     **/
    private static String loadCoinsMap() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(CryptocurrencyTool.class
                .getClassLoader().getResourceAsStream("coins.json")));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line);
        return stringBuilder.toString();
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

}
