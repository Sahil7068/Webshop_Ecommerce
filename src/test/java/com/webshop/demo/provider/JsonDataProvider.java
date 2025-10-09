package com.webshop.demo.provider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.demo.annotations.JsonData;
import com.webshop.demo.utils.RandomDataUtils;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class JsonDataProvider {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String TEST_RESOURCES_PATH = "src/test/resources/data/Register.json";

    @DataProvider(name = "jsonProvider")
    public static Object[][] provideTestData(Method method) {
        JsonData jsonData = method.getAnnotation(JsonData.class);
        if (jsonData == null) {
            throw new IllegalArgumentException("Test method must be annotated with @JsonData");
        }

        if (jsonData.targetClass() == Object.class) {
            throw new IllegalArgumentException("Please specify targetClass in @JsonData annotation");
        }

        String filePath = getFilePath(jsonData.file());
        try {
            if (jsonData.key().isEmpty()) {
                return readJsonArray(filePath, jsonData.targetClass());
            } else {
                return readJsonMap(filePath, jsonData.key(), jsonData.targetClass());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from: " + filePath, e);
        }
    }

    /**
     * Replaces previous non-generic array reader.
     * Reads a JSON array and converts each item to the provided targetClass,
     * then injects placeholders (e.g. RANDOM_EMAIL) if present.
     */
    private static <T> Object[][] readJsonArray(String filePath, Class<T> targetClass) throws IOException {
        List<Map<String, Object>> data = mapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});
        return data.stream()
                .map(itemMap -> {
                    T obj = mapper.convertValue(itemMap, targetClass);
                    injectRandomPlaceholders(obj);
                    return new Object[]{obj};
                })
                .toArray(Object[][]::new);
    }

    private static <T> Object[][] readJsonMap(String filePath, String key, Class<T> targetClass) throws IOException {
        Map<String, List<Map<String, Object>>> dataMap = mapper.readValue(
                new File(filePath),
                new TypeReference<Map<String, List<Map<String, Object>>>>() {}
        );

        List<Map<String, Object>> testData = dataMap.get(key);
        if (testData == null) {
            throw new IllegalArgumentException("No test data found for key: " + key);
        }

        return testData.stream()
                .map(item -> {
                    T obj = mapper.convertValue(item, targetClass);
                    injectRandomPlaceholders(obj);
                    return new Object[]{obj};
                })
                .toArray(Object[][]::new);
    }




//    private static Object[][] readJsonArray(String filePath) throws IOException {
//        List<Object> data = mapper.readValue(new File(filePath), new TypeReference<List<Object>>() {});
//        return data.stream()
//                .map(item -> new Object[]{item})
//                .toArray(Object[][]::new);
//    }
//
//    private static <T> Object[][] readJsonMap(String filePath, String key, Class<T> targetClass) throws IOException {
//        Map<String, List<Map<String, Object>>> dataMap = mapper.readValue(
//                new File(filePath),
//                new TypeReference<Map<String, List<Map<String, Object>>>>() {}
//        );
//
//        List<Map<String, Object>> testData = dataMap.get(key);
//        if (testData == null) {
//            throw new IllegalArgumentException("No test data found for key: " + key);
//        }
//
//        return testData.stream()
//                .map(item -> new Object[]{mapper.convertValue(item, targetClass)})
//                .toArray(Object[][]::new);
//    }

    /**
     * Simple placeholder injector:
     * - Looks for getEmail()/setEmail(String) on the POJO and if email equals "RANDOM_EMAIL" (case-insensitive),
     *   replaces it with RandomDataUtils.generateRandomEmail().
     * - Quietly skips if getter/setter not present.
     */
    private static void injectRandomPlaceholders(Object obj) {
        if (obj == null) return;

        Class<?> cls = obj.getClass();
        try {
            Method getEmail = null;
            Method setEmail = null;
            try {
                getEmail = cls.getMethod("getEmail");
            } catch (NoSuchMethodException ignored) { }

            try {
                setEmail = cls.getMethod("setEmail", String.class);
            } catch (NoSuchMethodException ignored) { }

            if (getEmail != null && setEmail != null) {
                Object current = getEmail.invoke(obj);
                if (current instanceof String) {
                    String s = ((String) current).trim();
                    if ("RANDOM_EMAIL".equalsIgnoreCase(s)) {
                        String random = RandomDataUtils.generateRandomEmail();
                        setEmail.invoke(obj, random);
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to inject random placeholders into object of type " + cls.getName(), e);
        }
    }

    private static String getFilePath(String fileName) {
        return System.getProperty("user.dir") + "/" +
                (fileName.contains("/") ?
                        fileName :
                        TEST_RESOURCES_PATH.replace("Register.json", fileName));
    }
}
