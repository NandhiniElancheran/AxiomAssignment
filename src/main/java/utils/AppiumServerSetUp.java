package utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.URL;

public class AppiumServerSetUp {
    public static AppiumDriverLocalService service;
    public static PropertyFileReader propertyFileReader = new PropertyFileReader();

    /**
     * Run appium server programmatically
     */
    public static AppiumServiceBuilder startAppium() {
        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.withIPAddress("127.0.0.1");
        appiumServiceBuilder.usingPort(getPort());
        appiumServiceBuilder.withAppiumJS(new File(propertyFileReader.getProperty("appiumJS")));
        appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
        service.start();
        return appiumServiceBuilder;
    }

    /**
     * @return the local server port
     */
    public static int getPort() {
        ServerSocket socket = null;
        int port = 0;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return port;
    }


    /**
     * @return appium url from the server
     */
    public static URL getAppiumUrl() {
        return service.getUrl();
    }

    /**
     * Stop the appium server
     */
    public static void stopAppium() {
        if (service != null) {
            try {
                service.stop();
            } catch (Exception e) {
                if (service.isRunning()) {
                    service.stop();
                }
            }
        }
    }
}
