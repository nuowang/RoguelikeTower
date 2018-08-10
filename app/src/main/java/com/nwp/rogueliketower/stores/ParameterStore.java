package com.nwp.rogueliketower.stores;

public class ParameterStore {
    /* Renderer parameters. */
    // Number of bytes bytes per float.
    public static final int BYTES_PER_FLOAT = 4;
    // Size of the coordinate data, 3 for x, y, z.
    public static final int COORDINATE_DATA_SIZE = 3;
    // Size of the color data, 4 for r, g, b, alpha.
    public static final int COLOR_DATA_SIZE = 4;
    // Size of the texture coordinate data, 2 for x, y.
    public static final int TEXTURE_COORDINATE_DATA_SIZE = 2;
    // Each tile has 6 vertices.
    public static final int TILE_DATA_SIZE = 6;

    /* Texture parameters. */
    // Currently, I assume that all frames in textures are squares and are size 32 by 32.
    public static final boolean SQUARE_32_TEXTURE = true;
}
