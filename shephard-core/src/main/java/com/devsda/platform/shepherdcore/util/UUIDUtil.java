package com.devsda.platform.shepherdcore.util;

import java.util.UUID;

public class UUIDUtil {

    public static String UUIDGenerator() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
