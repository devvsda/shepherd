package com.devsda.platform.shepherdclient.loader;

import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class YAMLLoader {

    public static <T> T load(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        File file = new File(filePath);

        if( !(file.exists()  && file.isFile()) ) {
            throw new ClientInvalidRequestException(String.format("YAML file is not available to load. FilePath : %s", filePath));
        }

        T object = objectMapper.readValue(new File(filePath), clazz);

        return object;
    }
}
