package com.knobtviker.android.things.contrib.community.device.blinkt;

import android.support.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.knobtviker.android.things.contrib.community.device.blinkt.Pixels.MAX;
import static com.knobtviker.android.things.contrib.community.device.blinkt.Pixels.MIN;

@Retention(RetentionPolicy.SOURCE)
@IntRange(from = MIN, to = MAX)
public @interface Pixels {
    int MIN = 0;
    int MAX = 7;
}
