package com.tolmachevsv.drivers;

import com.codeborne.selenide.WebDriverProvider;
import com.tolmachevsv.config.CapabilitiesConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalMobileDriver implements WebDriverProvider {

    public static URL getAppiumServerUrl() {
        System.setProperty("deviceHost", "local");
        CapabilitiesConfig config = ConfigFactory.create(CapabilitiesConfig.class, System.getProperties());
        try {
            return new URL(config.url());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        System.setProperty("deviceHost", "local");
        CapabilitiesConfig config = ConfigFactory.create(CapabilitiesConfig.class, System.getProperties());

        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);

        options.setAutomationName(ANDROID_UIAUTOMATOR2)
                .setPlatformName(ANDROID)
                .setDeviceName(config.deviceName())
                .setPlatformVersion(config.version())
                .setApp(getAbsolutePath(config.app()))
                .setAppPackage(config.appPackage())
                .setAppActivity(config.appActivity());

        return new AndroidDriver(getAppiumServerUrl(), options);
    }

        private String getAbsolutePath(String filePath) {
            File file = new File(filePath);
            assertTrue(file.exists(), filePath + " not found");
            return file.getAbsolutePath();
        }
}
