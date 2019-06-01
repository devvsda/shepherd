package com.devsda.platform.shepherdcore.loader;

import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.jackson.Jackson;

import java.io.File;
import java.io.IOException;

/**
 * This class helps to load YAML from file/string and vice-versa.
 */
public class YamlLoader {

    /**
     * This method helps to load YAML from given file into given data class.
     *
     * @param filePath
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T load(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = Jackson.newObjectMapper(new YAMLFactory());

        File file = new File(filePath);

        if (!(file.exists() && file.isFile())) {
            throw new ClientInvalidRequestException(String.format("YAML file is not available to load. FilePath : {}", filePath));
        }

        T object = objectMapper.readValue(new File(filePath), clazz);

        return object;
    }
}
