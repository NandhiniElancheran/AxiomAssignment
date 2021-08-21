package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {
    private FileInputStream fileInputStream;
    private String filePath;
    private Properties props;

    public PropertyFileReader() {
        this.filePath = System.getProperty("user.dir")+"/src/main/resources/config.properties";
        this.loadConfig();
    }

    public void setProperty(String key, String value) {
        this.props.setProperty(key, value);
    }

    /**
     * Method to retrieve the properties
     * @param key
     * @return property value
     */
    public String getProperty(String key) {
        return this.props.getProperty(key);
    }

    /**
     * Method to load the given configurations
     */
    public void loadConfig() {
        try {
            this.fileInputStream = new FileInputStream(filePath);
            this.props = new Properties();
            this.props.load(this.fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
