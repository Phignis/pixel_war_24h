package fr.ensim.charme_quartier.pixel_war.model;

import java.awt.*;

public enum EUseableColors {
    BLACK("black", new int[]{0, 0, 0}),
    WHITE("white", new int[]{255, 255, 255}),
    DEEP_BLACK("deep_black", new int[]{13, 23, 31}),
    DEEP_BLUE("deep_blue", new int[]{46, 70, 89}),
    BLUE_GREY("blue_grey", new int[]{67, 93, 115}),
    GREY_BLUE("grey_blue", new int[]{94, 120, 140}),
    BLUISH_GREY("bluish_grey", new int[]{122, 149, 167}),
    PALE_BLUE_GREY("pale_blue_grey", new int[]{153, 176, 191}),
    LIGHT_BLUE_GREY("light_blue_grey", new int[]{180, 197, 209}),
    VERY_LIGHT_BLUE_GREY("very_light_blue_grey", new int[]{208, 221, 228}),
    GREYISH_WHITE("greyish_white", new int[]{241, 244, 247}),
    JET_BLACK("jet_black", new int[]{13, 40, 41}),
    DARK_GREEN("dark_green", new int[]{21, 66, 55}),
    BOTTLE_GREEN("bottle_green", new int[]{35, 92, 68}),
    FIR_GREEN("fir_green", new int[]{49, 117, 69}),
    FOREST_GREEN("forest_green", new int[]{66, 143, 66}),
    OLIVE_GREEN("olive_green", new int[]{110, 168, 74}),
    LIME_GREEN("lime_green", new int[]{163, 194, 85}),
    PALE_LIME_GREEN("pale_lime_green", new int[]{207, 219, 114}),
    DARK_RED("dark_red", new int[]{117, 13, 16}),
    BRICK_RED("brick_red", new int[]{148, 36, 26}),
    RUST_RED("rust_red", new int[]{179, 68, 40}),
    ORANGE_RED("orange_red", new int[]{209, 102, 48}),
    ORANGE_BROWN("orange_brown", new int[]{230, 141, 62}),
    ORANGE_YELLOW("orange_yellow", new int[]{237, 172, 74}),
    GOLDEN_YELLOW("golden_yellow", new int[]{245, 203, 83}),
    LIGHT_YELLOW("light_yellow", new int[]{255, 234, 99}),
    CRIMSON_RED("crimson_red", new int[]{92, 30, 28}),
    REDDISH_BROWN("reddish_brown", new int[]{120, 54, 42}),
    BROWN_ORANGE("brown_orange", new int[]{145, 82, 68}),
    GOLDEN_BROWN("golden_brown", new int[]{173, 140, 68}),
    YELLOWISH_BROWN("yellowish_brown", new int[]{199, 140, 88}),
    LIGHT_BROWN("light_brown", new int[]{224, 171, 114}),
    PALE_BROWN("pale_brown", new int[]{235, 196, 138}),
    VERY_PALE_BROWN("very_pale_brown", new int[]{245, 217, 166}),
    DARK_VIOLET("dark_violet", new int[]{63, 26, 77}),
    VIOLET("violet", new int[]{109, 41, 117}),
    LIGHT_VIOLET("light_violet", new int[]{148, 57, 137}),
    PALE_VIOLET("pale_violet", new int[]{179, 80, 141}),
    PINK_VIOLET("pink_violet", new int[]{204, 107, 138}),
    PINK("pink", new int[]{230, 148, 143}),
    PALE_PINK("pale_pink", new int[]{245, 186, 169}),
    DARK_BLUE_VIOLET("dark_blue_violet", new int[]{29, 22, 82}),
    BLUE_VIOLET("blue_violet", new int[]{33, 40, 112}),
    LIGHT_BLUE_VIOLET("light_blue_violet", new int[]{44, 72, 143}),
    PALE_BLUE_VIOLET("pale_blue_violet", new int[]{57, 115, 173}),
    LIGHT_BLUE("light_blue", new int[]{83, 172, 204}),
    LIGHT_BLUE_SKY("light_blue_sky", new int[]{116, 206, 218}),
    PALE_BLUE("pale_blue", new int[]{165, 226, 230}),
    VERY_PALE_BLUE("very_pale_blue", new int[]{205, 241, 244});

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
}
