package com.myspring.demo.user;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public final class UrlBuilder {


    public List<String> createfilter(String key, String[] whitelist) {
        
        Set<String> fromUri = Arrays.stream(key.split("/"))
                .collect(Collectors.toSet());

        List<String> whiteListed = Arrays.stream(whitelist)
                .filter(fromUri::contains)
                .toList();

        return whiteListed;
    }


    // Optimized
    private static String[] getParam(String path) {
        var result = new ArrayList<String>();
        int start = -1;

        for (int i = 0; i < path.length(); i++) {
            char ch = path.charAt(i);

            if (ch == ':') {
                start = i + 1;
            } else if (start != -1 && ch == '/') {
                result.add(path.substring(start, i));
                start = -1;
            }
        }

        if (start != -1) {
            result.add(path.substring(start));
        }

        return result.toArray(String[]::new);
    }



    // Optimized
    private static Map<String, String> verifyParams(
            String[] params,
            Map<String, String> receivedParams
    ) {
        var result = new LinkedHashMap<String, String>(params.length);

        for (String key : params) {
            if (key != null && !key.isEmpty()) {
                result.put(key, receivedParams.getOrDefault(key, ""));
            }
        }

        return result;
    }



    // Optimized
    private static String createUrl(
            String path,
            Map<String, String> buildParams
    ) {
        String newPath = path;

        for (var entry : buildParams.entrySet()) {
            String value = entry.getValue();

            if (value != null) {
                newPath = newPath.replace(
                        ":" + entry.getKey(),
                        value
                );
            }
        }

        return newPath;
    }


    // Equivalent to URLSearchParams.toString()
    private static String serialize(Map<String, ?> query) {
        if (query == null || query.isEmpty()) {
            return "";
        }

        var builder = new StringBuilder();

        for (var entry : query.entrySet()) {
            Object value = entry.getValue();

            if (value == null) {
                continue;
            }

            if (!builder.isEmpty()) {
                builder.append('&');
            }

            builder
                    .append(encode(entry.getKey()))
                    .append('=')
                    .append(encode(String.valueOf(value)));
        }

        return builder.isEmpty()
                ? ""
                : "?" + builder;
    }


    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8)
                .replace("+", "%20");
    }


    public static String urlBuilder(
            String path,
            Map<String, String> params,
            Map<String, ?> query
    ) {
        String[] pathParams = getParam(path);

        String serialized = query != null
                ? serialize(query)
                : "";

        if (params != null) {
            var verifiedParamObject =
                    verifyParams(pathParams, params);

            return serialized.isEmpty()
                    ? createUrl(path, verifiedParamObject)
                    : path + serialized;
        }

        return serialized.isEmpty()
                ? path
                : path + serialized;
    }
}


