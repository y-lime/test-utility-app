package com.example.testutilityapp.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SampleProperties {

    @Value("${myproperties.main:default}")
    private String mypropertiesMain;

    @Value("${myproperties.test:default}")
    private String mypropertiesTest;

    @Value("${myproperties.empty:default}")
    private String mypropertiesEmpty;

    @Value("${myproperties.undefied:default}")
    private String mypropertiesUndefined;

    @Value("${myproperties.override:default}")
    private String mypropertiesOverride;

    @Value("${myproperties.remove:default}")
    private String mypropertiesRemove;

    public String getMypropertiesMain() {
        return mypropertiesMain;
    }

    public String getMypropertiesTest() {
        return mypropertiesTest;
    }

    public String getMypropertiesEmpty() {
        return mypropertiesEmpty;
    }

    public String getMypropertiesUndefined() {
        return mypropertiesUndefined;
    }

    public String getMypropertiesOverride() {
        return mypropertiesOverride;
    }

    public String getMypropertiesRemove() {
        return mypropertiesRemove;
    }
}
