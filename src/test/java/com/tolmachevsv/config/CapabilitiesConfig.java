package com.tolmachevsv.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:${deviceHost}.properties"})
public interface CapabilitiesConfig extends Config {
    @Key("username")
    String username();
    @Key("password")
    String password();
    @Key("url")
    String url();
    @Key("app")
    String app();
    @Key("deviceName")
    String deviceName();
    @Key("version")
    String version();
    @Key("appPackage")
    String appPackage();
    @Key("appActivity")
    String appActivity();
}
