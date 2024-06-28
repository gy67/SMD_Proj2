package lucky;

import java.util.Properties;

public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "properties/game1.properties";
    private static final String Log_Result = "logResult = ";

    public static void main(String[] args) {
        final Properties properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PROPERTIES_PATH);
        String logResult = new LuckyThirdteen(properties).runApp();
        System.out.println(Log_Result + logResult);
    }

}
