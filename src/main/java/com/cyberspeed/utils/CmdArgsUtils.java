package com.cyberspeed.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class CmdArgsUtils {
    public static Map<String, String> parseArgs(String[] args) {
        Map<String, String> paramMap = new HashMap<>();
        for (int i = 0; i < args.length - 1; i += 2) {
            paramMap.put(args[i], args[i + 1]);
        }
        return paramMap;
    }
}
