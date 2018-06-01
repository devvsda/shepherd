package com.devsda.platform.shephardcore.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.jackson.Jackson;

import java.io.File;
import java.io.IOException;

public class YamlLoader {

    public static <T> T load(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = Jackson.newObjectMapper(new YAMLFactory());//new ObjectMapper(new YAMLFactory());


        T object = objectMapper.readValue(new File(filePath), clazz);

        return object;
    }
}
