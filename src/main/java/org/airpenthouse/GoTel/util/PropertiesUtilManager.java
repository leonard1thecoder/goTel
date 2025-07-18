package org.airpenthouse.GoTel.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


/*
 * The Value annotation from Spring  doesn't work and also System.getProperty ()
 * Both returns null
 * */
public class PropertiesUtilManager {

    private final static String applicationPropertiesPath = "src/main/resources/application.properties";
    private final static Map<String, String> storeProperties = new TreeMap<>();
    private static String ownPropertiesFilePath;
    private static boolean useOwnPath;

    public static void setOwnPropertiesFilePath(String propertiesFilePath) {
        if (propertiesFilePath.endsWith(".properties")) {
        ownPropertiesFilePath = propertiesFilePath;
        useOwnPath = true;
        } else
            throw new RuntimeException("The file it's not properties file suffix is not .properties");
    }

    public static String getPropertiesValue(String key) {
        File applicationPropertiesFile;

        if (!useOwnPath)
            applicationPropertiesFile = new File(applicationPropertiesPath);
        else
            applicationPropertiesFile = new File(ownPropertiesFilePath);

        String keyFinder = System.getProperty(key);
        try (FileReader reader = new FileReader(applicationPropertiesFile); BufferedReader bReader = new BufferedReader(reader)) {

            while (true) {
                String keyValuePair = bReader.readLine();
                if (keyValuePair.contains(key)) {

                    int index = keyValuePair.indexOf('=') + 1;
                    return keyValuePair.substring(index);
                }
            }
        } catch (IOException e) {

            if (keyFinder != null) {
                return keyFinder;
            } else {
                throw new IllegalArgumentException("The  applicationPropertiesPath variable is not pointing to correct path, check the path and the System.getProperty returns null");
            }


        } catch (NullPointerException e) {

            if (keyFinder != null) {
                return keyFinder;
            } else {
                if (storeProperties.containsKey(key)) {
                    return storeProperties.get(key);
                } else {
                    throw new NullPointerException("No value found in application.properties or the key is not there");
                }
            }
        }

    }

    public static void setProperties(String key, String value) {
        System.getProperty(key, value);
        storeProperties.put(key, value);
    }

}
