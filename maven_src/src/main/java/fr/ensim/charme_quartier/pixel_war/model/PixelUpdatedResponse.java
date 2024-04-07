package fr.ensim.charme_quartier.pixel_war.model;

public class PixelUpdatedResponse {
    private int x;
    private int y;
    private String canvasId;
    private int[] rgb;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    public String getCanvasId() {
        return canvasId;
    }

    public void setCanvasId(String canvasId) {
        this.canvasId = canvasId;
    }
}
