package org.main.selenium.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonDataReader {
    
    private static final String TEST_DATA_PATH = ConfigReader.getTestDataPath();
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> getTestData(String fileName, String testType) {
        try {
            File file = new File(TEST_DATA_PATH + fileName);
            Map<String, Object> data = objectMapper.readValue(file, Map.class);
            return (List<Map<String, Object>>) data.get(testType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read test data from file: " + fileName);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getTestDataByTestName(String fileName, String testType, String testName) {
        List<Map<String, Object>> testDataList = getTestData(fileName, testType);
        
        return testDataList.stream()
                .filter(data -> testName.equals(data.get("testName")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test data not found for test name: " + testName));
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getCommonTestData(String fileName) {
        try {
            File file = new File(TEST_DATA_PATH + fileName);
            Map<String, Object> data = objectMapper.readValue(file, Map.class);
            return (Map<String, Object>) data.get("commonTestData");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read common test data from file: " + fileName);
        }
    }
    
    public static String getStringValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }
    
    public static int getIntValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        throw new RuntimeException("Value for key '" + key + "' is not an integer");
    }
    
    public static boolean getBooleanValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        throw new RuntimeException("Value for key '" + key + "' is not a boolean");
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getNestedObject(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        throw new RuntimeException("Value for key '" + key + "' is not a nested object");
    }
}
