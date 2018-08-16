[ ![Download](https://api.bintray.com/packages/knobtviker/maven/blinkt/images/download.svg) ](https://bintray.com/knobtviker/maven/blinkt/_latestVersion)

Pimoroni Blinkt! driver for Android Things
==========================================

This driver supports [Pimoroni Blinkt!](https://shop.pimoroni.com/products/blinkt).


8 super-bright APA102 RGB LED indicators that are ideal for adding visual notifications to your Raspberry Pi without breaking the bank!
Each LED on Blinkt! is individually controllable and dimmable allowing you to create gradients, pulsing effects, or just flash them on and off.
The data and clock lines are connected to GPIO #23 and #24 but this driver abstracts that for you.

How to use the driver
---------------------

### Gradle dependency

To use the `blinkt` driver, simply add the line below to your project's `build.gradle`,
where `<version>` matches the last version of the driver available on [jcenter](https://bintray.com/knobtviker/maven/blinkt) .

```
dependencies {
    implementation 'com.knobtviker.android.things.contrib.community.device.blinkt:<version>'
}
```

### Sample usage

```java
import com.knobtviker.android.things.contrib.community.device.blinkt.Blinkt;

// Access the device:

Blinkt blinkt;

try {
    //default constructor
    blinkt = new Blinkt();
} catch (IOException e) {
    // couldn't configure the device...
}

// Set brightness and all LEDs to red color
try {
    blinkt.setBrightness(0.5f);
    blinkt.setAll(255, 0 ,0);
} catch (IOException e) {
    // error writing brightness and color
}
```