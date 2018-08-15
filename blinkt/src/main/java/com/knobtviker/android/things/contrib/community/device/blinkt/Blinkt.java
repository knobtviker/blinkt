package com.knobtviker.android.things.contrib.community.device.blinkt;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class Blinkt implements AutoCloseable {

    @NonNull
    private Gpio dataPin;

    @NonNull
    private Gpio clockPin;

    private int[] pixels;

    @Brightness
    private int brightness;

    public Blinkt() throws IOException {
        final PeripheralManager peripheralManager = PeripheralManager.getInstance();
        dataPin = peripheralManager.openGpio(Pin.DATA);
        clockPin = peripheralManager.openGpio(Pin.CLOCK);

        dataPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        clockPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

        pixels = new int[Pixels.MAX + 1];

        reset();
        show();
    }

    @Override
    public void close() throws Exception {
        reset();
        show();
    }

    public void setBrightness(@BrightnessGlobal final float brightness) {
        if (brightness < BrightnessGlobal.MIN || brightness > BrightnessGlobal.MAX) {
            throw new IllegalArgumentException("Invalid brightness " + brightness);
        }

        this.brightness = Math.round(brightness * Brightness.MAX);
    }

    @BrightnessGlobal
    public float getBrightness() {
        return brightness / (float) Brightness.MAX;
    }

    public void show() throws IOException {
        for (int i = 0; i < 4; ++i) {
            writeByte((byte) 0);
        }

        for (int i = Pixels.MIN; i < Pixels.MAX + 1; ++i) {
            writeLed(pixels[i]);
        }

        latch();
    }

    public void reset() {
        clearAll();
        setBrightness(0.0f);
    }

    public void clearAll() {
        for (int i = Pixels.MIN; i < Pixels.MAX + 1; ++i) {
            pixels[i] = 0;
        }
    }

    public void clear(@Pixels final int n) {
        if (n < Pixels.MIN || n > Pixels.MAX) {
            throw new IllegalArgumentException("n must be larget than " + Pixels.MIN + " and smaller than " + Pixels.MAX);
        }
        pixels[n] = 0;
    }

    public void setAll(
        @RgbValue final int red,
        @RgbValue final int green,
        @RgbValue final int blue,
        @BrightnessGlobal final float brightness
    ) {
        if (red < RgbValue.MIN || red > RgbValue.MAX) {
            throw new IllegalArgumentException("red must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }
        if (green < RgbValue.MIN || green > RgbValue.MAX) {
            throw new IllegalArgumentException("green must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }
        if (blue < RgbValue.MIN || blue > RgbValue.MAX) {
            throw new IllegalArgumentException("blue must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }
        if (brightness < BrightnessGlobal.MIN || brightness > BrightnessGlobal.MAX) {
            throw new IllegalArgumentException("Invalid brightness " + brightness);
        }

        for (int i = 0; i < Pixels.MAX + 1; ++i) {
            set(i, red, green, blue, brightness);
        }
    }

    public void set(
        @Pixels final int n,
        @RgbValue final int red,
        @RgbValue final int green,
        @RgbValue final int blue,
        @BrightnessGlobal final float brightness
    ) {
        if (n < Pixels.MIN || n > Pixels.MAX) {
            throw new IllegalArgumentException("n must be larget than " + Pixels.MIN + " and smaller than " + Pixels.MAX);
        }
        if (brightness < BrightnessGlobal.MIN || brightness > BrightnessGlobal.MAX) {
            throw new IllegalArgumentException("Invalid brightness " + brightness);
        }
        if (red < RgbValue.MIN || red > RgbValue.MAX) {
            throw new IllegalArgumentException("red must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }
        if (green < RgbValue.MIN || green > RgbValue.MAX) {
            throw new IllegalArgumentException("green must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }
        if (blue < RgbValue.MIN || blue > RgbValue.MAX) {
            throw new IllegalArgumentException("blue must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }

        pixels[n] = ((int) (brightness * Brightness.MAX) << 24) | (red << 16) | (green << 8) | blue;
    }

    public void setAll(final int red, final int green, final int blue) {
        for (int i = 0; i < Pixels.MAX + 1; ++i) {
            set(i, red, green, blue);
        }
    }

    public void set(@Pixels final int n, final int red, final int green, final int blue) {
        if (n < Pixels.MIN || n > Pixels.MAX) {
            throw new IllegalArgumentException("n must be larget than " + Pixels.MIN + " and smaller than " + Pixels.MAX);
        }
        if (red < RgbValue.MIN || red > RgbValue.MAX) {
            throw new IllegalArgumentException("red must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }
        if (green < RgbValue.MIN || green > RgbValue.MAX) {
            throw new IllegalArgumentException("green must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }
        if (blue < RgbValue.MIN || blue > RgbValue.MAX) {
            throw new IllegalArgumentException("blue must be between " + RgbValue.MIN + " and " + RgbValue.MAX);
        }

        pixels[n] = (brightness << 24) | (red << 16) | (green << 8) | blue;
    }

    public void setAll(@NonNull final Color color) {
        for (int i = 0; i < Pixels.MAX + 1; ++i) {
            set(i, color);
        }
    }

    public void set(@Pixels final int n, @NonNull final Color color) {
        if (n < Pixels.MIN || n > Pixels.MAX) {
            throw new IllegalArgumentException("n must be larget than " + Pixels.MIN + " and smaller than " + Pixels.MAX);
        }
        final int red = Math.round(color.red());
        final int green = Math.round(color.green());
        final int blue = Math.round(color.blue());

        pixels[n] = (brightness << 24) | (red << 16) | (green << 8) | blue;
    }

    public int[] getAll() {
        return pixels;
    }

    public int get(@Pixels final int n) {
        if (n < Pixels.MIN || n > Pixels.MAX) {
            throw new IllegalArgumentException("n must be larget than " + Pixels.MIN + " and smaller than " + Pixels.MAX);
        }
        return pixels[n];
    }

    private void writeByte(final byte out) throws IOException {
        for (int i = 7; i >= 0; --i) {
            dataPin.setValue((out & (1 << i)) != 0);

            clockPin.setValue(true);
            clockPin.setValue(false);
        }
    }

    private void writeLed(final int data) throws IOException {
        writeByte((byte) (0xe0 | ((data >> 24) & 0x1f)));
        writeByte((byte) (data));
        writeByte((byte) (data >> 8));
        writeByte((byte) (data >> 16));
    }

    private void latch() throws IOException {
        dataPin.setValue(false);

        for (int i = 0; i < 36; ++i) {
            clockPin.setValue(true);
            clockPin.setValue(false);
        }
    }
}
