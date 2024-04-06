package fr.ensim.charme_quartier.pixel_war.model;

import java.awt.*;

public enum EUseableColors {
    RED("red", new int[]{255, 0, 0}),
    BLACK("black", new int[]{0, 0, 0}),
    WHITE("white", new int[]{255, 255, 255}),
    DEEP_BLACK("deep_black", new int[]{13, 23, 31}),
    DEEP_BLUE("deep_blue", new int[]{46, 70, 89}),
    BLUE_GREY("Blue Grey", new int[]{67, 93, 115}),
    GREY_BLUE("Grey Blue", new int[]{94, 120, 140}),
    BLUISH_GREY("Bluish Grey", new int[]{122, 149, 167}),
    PALE_BLUE_GREY("Pale Blue Grey", new int[]{153, 176, 191}),
    LIGHT_BLUE_GREY("Light Blue Grey", new int[]{180, 197, 209}),
    VERY_LIGHT_BLUE_GREY("Very Light Blue Grey", new int[]{208, 221, 228}),
    GREYISH_WHITE("Greyish White", new int[]{241, 244, 247}),
    JET_BLACK("Jet Black", new int[]{13, 40, 41}),
    DARK_GREEN("Dark Green", new int[]{21, 66, 55}),
    BOTTLE_GREEN("Bottle Green", new int[]{35, 92, 68}),
    FIR_GREEN("Fir Green", new int[]{49, 117, 69}),
    FOREST_GREEN("Forest Green", new int[]{66, 143, 66}),
    OLIVE_GREEN("Olive Green", new int[]{110, 168, 74}),
    LIME_GREEN("Lime Green", new int[]{163, 194, 85}),
    PALE_LIME_GREEN("Pale Lime Green", new int[]{207, 219, 114}),
    DARK_RED("Dark Red", new int[]{117, 13, 16}),
    BRICK_RED("Brick Red", new int[]{148, 36, 26}),
    RUST_RED("Rust Red", new int[]{179, 68, 40}),
    ORANGE_RED("Orange Red", new int[]{209, 102, 48}),
    ORANGE_BROWN("Orange Brown", new int[]{230, 141, 62}),
    ORANGE_YELLOW("Orange Yellow", new int[]{237, 172, 74}),
    GOLDEN_YELLOW("Golden Yellow", new int[]{245, 203, 83}),
    LIGHT_YELLOW("Light Yellow", new int[]{255, 234, 99}),
    CRIMSON_RED("Crimson Red", new int[]{92, 30, 28}),
    REDDISH_BROWN("Reddish Brown", new int[]{120, 54, 42}),
    BROWN_ORANGE("Brown Orange", new int[]{145, 82, 68}),
    GOLDEN_BROWN("Golden Brown", new int[]{173, 140, 68}),
    YELLOWISH_BROWN("Yellowish Brown", new int[]{199, 140, 88}),
    LIGHT_BROWN("Light Brown", new int[]{224, 171, 114}),
    PALE_BROWN("Pale Brown", new int[]{235, 196, 138}),
    VERY_PALE_BROWN("Very Pale Brown", new int[]{245, 217, 166}),
    DARK_VIOLET("Dark Violet", new int[]{63, 26, 77}),
    VIOLET("Violet", new int[]{109, 41, 117}),
    LIGHT_VIOLET("Light Violet", new int[]{148, 57, 137}),
    PALE_VIOLET("Pale Violet", new int[]{179, 80, 141}),
    PINK_VIOLET("Pink Violet", new int[]{204, 107, 138}),
    PINK("Pink", new int[]{230, 148, 143}),
    PALE_PINK("Pale Pink", new int[]{245, 186, 169}),
    DARK_BLUE_VIOLET("Dark Blue Violet", new int[]{29, 22, 82}),
    BLUE_VIOLET("Blue Violet", new int[]{33, 40, 112}),
    LIGHT_BLUE_VIOLET("Light Blue Violet", new int[]{44, 72, 143}),
    PALE_BLUE_VIOLET("Pale Blue Violet", new int[]{57, 115, 173}),
    LIGHT_BLUE("Light Blue", new int[]{83, 172, 204}),
    LIGHT_BLUE_SKY("Light Blue Sky", new int[]{116, 206, 218}),
    PALE_BLUE("Pale Blue", new int[]{165, 226, 230}),
    VERY_PALE_BLUE("Very Pale Blue", new int[]{205, 241, 244});

    private final String key;
    private final Color value;

    EUseableColors(String key, int[] color) {
        this.key = key;
        this.value = new Color(color[0],color[1],color[2]);
    }

    public String getKey() {
        return key;
    }

    public Color getValue() {
        return value;
    }

    public EUseableColors getFromString(String colorName) {
        for(EUseableColors eu : values()) {
            if(eu.key.equals(colorName)) return eu;
        }
        return null;
    }
}
