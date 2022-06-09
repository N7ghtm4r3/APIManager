package com.tecknobit.apimanager.Tools.Trading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The {@code CryptocurrencyTool} class is a useful class tool that help work with cryptocurrencies details. <br>
 * This tool help to get image logo of a crypto, get full name of a crypto or get symbol of a crypto. <br>
 * @implNote this tool is based on the official Github library <a href="https://github.com/ErikThiart/cryptocurrency-icons">https://github.com/ErikThiart/cryptocurrency-icons</a>
 * from where cryptocurrencies details have been taken.
 * @author Tecknobit N7ghtm4r3
 * **/

public class CryptocurrencyTool {

    /**
     * {@code coins} is the instance that contains cryptocurrencies details
     * **/
    private final JSONArray coins;

    /**
     * {@code SYMBOL_KEY} is the instance that contains symbol key for {@link #coins} map
     * **/
    private static final String SYMBOL_KEY = "symbol";

    /**
     * {@code NAME_KEY} is the instance that contains name key for {@link #coins} map
     * **/
    private static final String NAME_KEY = "name";

    /**
     * {@code IMAGE_URL_KEY} is the instance that contains image url key for {@link #coins} map
     * **/
    private static final String IMAGE_URL_KEY = "img_url";

    /**
     * {@code SLUG_KEY} is the instance that contains slug key for {@link #coins} map
     * **/
    private static final String SLUG_KEY = "slug";

    /** Constructor to init APIRequest manager <br>
     * Any params required
     * **/
    public CryptocurrencyTool() throws IOException {
        coins = new JSONArray(loadCoinsMap());
    }

    /** Method to load coins map for workflow of the tool <br>
     * Any params required
     * @return map loaded as {@link String}
     * **/
    private String loadCoinsMap() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("coins.json")));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line);
        return stringBuilder.toString();
    }

    /** Method to fetch cryptocurrency name
     * @param symbol: symbol of the cryptocurrency to fetch es. BTC
     * @return name of the cryptocurrency es. Bitcoin
     * **/
    public String getCryptocurrencyName(String symbol){
        return getValue(SYMBOL_KEY, symbol, NAME_KEY);
    }

    /** Method to fetch cryptocurrency symbol
     * @param name: name of the cryptocurrency to fetch es. Bitcoin
     * @return symbol of the cryptocurrency es. BTC
     * **/
    public String getCryptocurrencySymbol(String name){
        return getValue(NAME_KEY, name, SYMBOL_KEY);
    }

    /** Method to fetch cryptocurrency url logo image by cryptocurrency symbol
     * @param symbol: symbol of the cryptocurrency to fetch logo image es. BTC
     * @return image url of the cryptocurrency
     * **/
    public String getUrlLogoBySymbol(String symbol){
        return getValue(SYMBOL_KEY, symbol, IMAGE_URL_KEY);
    }

    /** Method to fetch cryptocurrency url logo image by cryptocurrency name
     * @param name: name of the cryptocurrency to fetch logo image es. Bitcoin
     * @return image url of the cryptocurrency
     * **/
    public String getUrlLogoByName(String name){
        return getValue(NAME_KEY, name, IMAGE_URL_KEY);
    }

    /** Method to fetch cryptocurrency slug by cryptocurrency symbol
     * @param symbol: name of the cryptocurrency to fetch slug es. BTC
     * @return slug of the cryptocurrency
     * **/
    public String getSlugBySymbol(String symbol){
        return getValue(SYMBOL_KEY, symbol, SLUG_KEY);
    }

    /** Method to fetch cryptocurrency slug by cryptocurrency name
     * @param name: name of the cryptocurrency to fetch slug es. Bitcoin
     * @return slug of the cryptocurrency
     * **/
    public String getSlugByName(String name){
        return getValue(NAME_KEY, name, SLUG_KEY);
    }

    /** Method to fetch detail from coins map
     * @param researchKey: key used to research the compare key
     * @param researchValue: value of researchKey
     * @param keyToFetch: key of the detail to fetch
     * @return value of keyToFetch if exists as {@link String} and null if not exists
     * **/
    private String getValue(String researchKey, String researchValue, String keyToFetch){
        for (int j=0; j < coins.length(); j++){
            JSONObject coin = coins.getJSONObject(j);
            if(coin.getString(researchKey).equals(researchValue))
                return coin.getString(keyToFetch);
        } /**a*/
        return null;
    }

}
