package com.devsda.platform.shephardcore.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.jackson.Jackson;

import java.io.File;
import java.io.IOException;

public class JSONLoader {

    public static String stringify(Object object) throws IOException {

        ObjectMapper objectMapper = Jackson.newObjectMapper();//new ObjectMapper(new YAMLFactory());


        String stringify = objectMapper.writeValueAsString(object);

        return stringify;
    }
}
