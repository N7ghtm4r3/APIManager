package com.tecknobit.apimanager.interfaces;

import com.tecknobit.apimanager.apis.APIRequest;

/**
 * The {@code Manager} interface is useful to give the base behavior of a manager that uses {@link APIRequest}
 * as way to make the request to the API services
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see APIRequest
 * @since 2.1.5
 */
public interface Manager {

    /**
     * Method to get the status code of a request <br>
     * No-any params required
     *
     * @return status code of a request as int
     */
    int getStatusResponse();

    /**
     * Method to get the response af a request <br>
     * No-any params required
     *
     * @return response of a request as {@link String}
     */
    String getResponse();

    /**
     * Method to get the error response af a request <br>
     * No-any params required
     *
     * @return error response of a request as {@link String}
     */
    String getErrorResponse();

    /**
     * Method to get the error response of a request formatted in JSON <br>
     * No-any params required
     *
     * @return error response of a request formatted in JSON as {@link T}
     */
    <T> T getJSONErrorResponse();

    /**
     * Method to print the error response of a request <br>
     * No-any params required
     */
    void printErrorResponse();

}
