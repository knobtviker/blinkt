package com.knobtviker.android.things.contrib.community.device.blinkt;

import android.support.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.knobtviker.android.things.contrib.community.device.blinkt.RgbValue.MAX;
import static com.knobtviker.android.things.contrib.community.device.blinkt.RgbValue.MIN;

@Retention(RetentionPolicy.SOURCE)
@IntRange(from = MIN, to = MAX)
public @interface RgbValue {
    int MIN = 0;
    int MAX = 255;
}
