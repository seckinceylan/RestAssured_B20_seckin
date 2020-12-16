package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties properties;
    static {
        try (FileInputStream file = new FileInputStream("configuration.properties")) {
            properties = new Properties();
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.out.println("Unable to find configuration.properties file!"+ e.getMessage());
        }
    }
    public static String getProperty(String keyWord) {
        return properties.getProperty(keyWord);
    }
}
