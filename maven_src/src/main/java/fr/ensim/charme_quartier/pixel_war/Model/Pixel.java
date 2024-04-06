package fr.ensim.charme_quartier.pixel_war.Model;

public class Pixel {
    private String _id;
    private String name;
    private int width;
    private int height;
    private int chunkWidth;
    private int chunkHeight;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getChunkWidth() {
        return chunkWidth;
    }

    public void setChunkWidth(int chunkWidth) {
        this.chunkWidth = chunkWidth;
    }

    public int getChunkHeight() {
        return chunkHeight;
    }

    public void setChunkHeight(int chunkHeight) {
        this.chunkHeight = chunkHeight;
    }
}
