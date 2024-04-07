package fr.ensim.charme_quartier.pixel_war.model;

public class CanvasCoords {
    private final int absoluteXCoord;
    private final int absoluteYCoord;

    private static final int NB_CHUNK_X = 7;
    private static final int NB_CHUNK_Y = 5;

    private static final int NB_PIXEL_CHUNK_X = 50;
    private static final int NB_PIXEL_CHUNK_Y = 50;

    /**
     * create an object from coords from the canvas absolute coordinates system
     * @param absoluteXCoord abscissa of pixel
     * @param absoluteYCoord ordinate of pixel
     */
    public CanvasCoords(int absoluteXCoord, int absoluteYCoord) {
        this.absoluteXCoord = absoluteXCoord;
        this.absoluteYCoord = absoluteYCoord;
    }

    /**
     * create an object from coords inside given chunk
     *
     * @param relativeXCoord abscissa inside of given chunk
     * @param relativeYCoord ordinate inside of given chunk
     * @param chunkId chunk where pixel is
     */
    public CanvasCoords(int relativeXCoord, int relativeYCoord, int chunkId) {
        // chunk id start at 1, so we remove 1 to be good
        // dividing by default the chunk id with number of chunk in Y will give chunk index in column
        this.absoluteXCoord = Math.floorDiv(chunkId - 1, NB_CHUNK_Y) * NB_PIXEL_CHUNK_X + relativeXCoord;
        this.absoluteYCoord = (chunkId - 1) % NB_CHUNK_Y * NB_PIXEL_CHUNK_Y + relativeYCoord;
    }

    /**
     * return absolution canvas coordinate of a pixel
     * @return array containing in order x coordinate, then y
     */
    public int[] getAbsoluteCoords() {
        return new int[] {absoluteXCoord, absoluteYCoord};
    }

    /**
     * return relative canvas coordinate of a pixel, regarding of a chunk
     * @return array containing in order x coordinate, then y and finally the chunk id
     */
    public int[] getRelativeCoords() {
        int indexChunkX = Math.floorDiv(absoluteXCoord, NB_PIXEL_CHUNK_X);
        int indexChunkY = Math.floorDiv(absoluteYCoord, NB_PIXEL_CHUNK_Y);

        // add 1 because chunkId start at 1
        int chunkId = 1 + indexChunkY + indexChunkX * NB_CHUNK_Y;

        int relativeXCoord = absoluteXCoord - NB_PIXEL_CHUNK_X * indexChunkX;
        int relativeYCoord = absoluteYCoord - NB_PIXEL_CHUNK_Y * indexChunkY;

        return new int[] {relativeXCoord, relativeYCoord, chunkId};
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(o == this) {
            return true;
        }

        return o instanceof CanvasCoords && equals((CanvasCoords) o);
    }

    private boolean equals(CanvasCoords c) {
        int[] absoluteCoords = c.getAbsoluteCoords();
        return absoluteCoords[0] == this.absoluteXCoord && absoluteCoords[1] == this.absoluteYCoord;
    }
}
