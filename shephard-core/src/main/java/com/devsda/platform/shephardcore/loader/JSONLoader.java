package com.devsda.platform.shephardcore.loader;

import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class helps to load json from file/string and vice-versa.
 */
public class JSONLoader {

    /**
     * This method helps to convert any object to stringified json.
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static String stringify(Object object) throws IOException {

        ObjectMapper objectMapper = Jackson.newObjectMapper();

        String stringify = objectMapper.writeValueAsString(object);

        return stringify;
    }

    /**
     * This method helps to load json file into given data class object.
     *
     * @param filePath
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T load(String filePath, Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(filePath);

        if (!(file.exists() && file.isFile())) {
            throw new ClientInvalidRequestException(String.format("Json file is not available to load. FilePath : {}", filePath));
        }

        FileInputStream fileInputStream = new FileInputStream(file);

        T obj = objectMapper.readValue(fileInputStream, clazz);

        return obj;
    }

    /**
     * This method helps to load json file from resources folder into given data class object.
     *
     * @param fileName
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T loadFromResources(String fileName, Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        InputStream inputStream = JSONLoader.class.getClassLoader().getResourceAsStream(fileName);
        T obj = objectMapper.readValue(inputStream, clazz);

        return obj;
    }

    public static <T> T loadFromStringifiedObject(String stringifiedObject, Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        T obj = objectMapper.readValue(stringifiedObject, clazz);

        return obj;
    }

}
