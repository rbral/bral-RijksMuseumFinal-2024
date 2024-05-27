package bral.museum;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Retrieve the API Key in apikey.properties or from environment variable on GitHub
 */
public class ApiKey {

    private final String key;

    public ApiKey() {
        Properties properties = new Properties();
        InputStream in = ApiKey.class.getResourceAsStream(
                "/apikey.properties");
        if (in != null) {
            try {
                properties.load(in);
                key = properties.getProperty("apikey");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            key = System.getenv("apikey");
        }

        if (key == null) {
            throw new NullPointerException("apikey is null");
        }
    }

    @Override
    public String toString() {
        return key;
    }
}