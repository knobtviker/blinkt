package com.knobtviker.android.things.contrib.community.device.blinkt;

import android.support.annotation.FloatRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.knobtviker.android.things.contrib.community.device.blinkt.BrightnessGlobal.MAX;
import static com.knobtviker.android.things.contrib.community.device.blinkt.BrightnessGlobal.MIN;

@Retention(RetentionPolicy.SOURCE)
@FloatRange(from = MIN, to = MAX)
public @interface BrightnessGlobal {
    float MIN = 0.0f;
    float MAX = 1.0f;
}
