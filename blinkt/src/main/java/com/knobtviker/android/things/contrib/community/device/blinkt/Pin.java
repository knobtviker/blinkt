package com.knobtviker.android.things.contrib.community.device.blinkt;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({Pin.DATA, Pin.CLOCK})
public @interface Pin {
    String DATA = "BCM23";
    String CLOCK = "BCM24";
}
