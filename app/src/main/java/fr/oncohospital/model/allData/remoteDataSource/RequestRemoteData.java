package fr.oncohospital.model.allData.remoteDataSource;


/**
 * Created by Assia HACHICHI on 10/02/2021
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * This class gives a method, which send a POST request to a remote server, by specifying its parameters
 */
public class RequestRemoteData {


    /**
     * This method sens a request to a given server url, by specifying some parameters
     * @param serverUrl represents the Url of a given server
     * @param parameters represents some parameters of this request
     * @return represents a full response of this request
     */

    public static String sendPostRequest(String serverUrl, HashMap<String, String> parameters) {
        
        URL url;
        HttpURLConnection connection;
        OutputStream os;
        BufferedWriter writer;
        String outputResult = "";

        try {
            url = new URL(serverUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            os = connection.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(parameters));
            writer.flush();
            writer.close();
            os.close();
            return getResponseString(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputResult;
    }

    /**
    * This method gets a response from the given connection
    * @param connection represents the given Http Connection
    * @return represents a response of this given connection
    */
    private static String getResponseString(HttpURLConnection connection) {
        BufferedReader br;
        StringBuilder fullResponse;
        String response;

        fullResponse = new StringBuilder();
        int responseCode = 0;
        try {
            responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((response = br.readLine()) != null) {
                    fullResponse.append(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullResponse.toString();
    }


    /**
    * This method convert a given HashMap into a query string parameters, in order to send it to a server.
    * @param params a given HashMap, which contains some pair of data, look like (key, value)
    * @return represents a query string parameters, corresponding to th input HashMap
    */
    private static String getPostDataString(HashMap<String, String> params) {
        StringBuilder queryParameters;
        boolean isFirstArgument;

        queryParameters = new StringBuilder();
        try {
            isFirstArgument = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (isFirstArgument) {
                    isFirstArgument = false;
                }else {
                    queryParameters.append("&");
                }
            queryParameters.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            queryParameters.append("=");
            queryParameters.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return queryParameters.toString();
    }
}