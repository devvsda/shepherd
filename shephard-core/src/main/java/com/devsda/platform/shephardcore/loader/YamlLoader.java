package com.devsda.platform.shephardcore.loader;

import com.devsda.platform.shephardcore.exception.ClientInvalidRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.jackson.Jackson;

import java.io.File;
import java.io.IOException;

public class YamlLoader {

    public static <T> T load(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = Jackson.newObjectMapper(new YAMLFactory());//new ObjectMapper(new YAMLFactory());

        File file = new File(filePath);

        if( !(file.exists()  && file.isFile()) ) {
            throw new ClientInvalidRequestException(String.format("YAML file is not available to load. FilePath : {}", filePath));
        }

        T object = objectMapper.readValue(new File(filePath), clazz);

        return object;
    }
}
