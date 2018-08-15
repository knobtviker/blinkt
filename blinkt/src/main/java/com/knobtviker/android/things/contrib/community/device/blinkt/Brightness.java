package com.knobtviker.android.things.contrib.community.device.blinkt;

import android.support.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.knobtviker.android.things.contrib.community.device.blinkt.Brightness.MAX;
import static com.knobtviker.android.things.contrib.community.device.blinkt.Brightness.MIN;

@Retention(RetentionPolicy.SOURCE)
@IntRange(from = MIN, to = MAX)
public @interface Brightness {
    int MIN = 0;
    int MAX = 31;
}
