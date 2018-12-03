package com.nwp.rogueliketower.stores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

public class TextureStore {
    public Context context;
    // Textures in the game are identified by their unique name in plain text.
    public static final String [] names = {
        "carl_walk_right",  // 0.
        "carl_walk_left",   // 1.
        "black",            // 2.
        "white",            // 3.
        "background"        // 4.
    };
    // The textures themselves.
    public Map<String, Bitmap> bitmaps;
    // How many frames each texture contains.
    public Map<String, Integer> frames;

    public static final int colorFloor = -1;        // White.
    public static final int colorWall = -16777216;  // Black.
    public static final int colorCarl = -65536;     // Red.
    public static final int colorDoor = -10077903;  // Brown.

    public TextureStore(Context context) {
        this.context = context;
        this.bitmaps = new HashMap<>();
        this.frames = new HashMap<>();
        loadTexturesFromRawResource();
        genTextureInfo();
    }

    private void loadTexturesFromRawResource() {
        // No pre-scaling.
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        for (String name : names) {
            // Get the resource ID.
            int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
            // Read in and save the resource.
            bitmaps.put(name, BitmapFactory.decodeResource(context.getResources(), resourceId, options));
        }
    }

    private void genTextureInfo() {
        if (ParameterStore.SQUARE_32_TEXTURE) {
            for (String name : names) {
                // I assume that all of the frames in the animation are put on one line.
                frames.put(name, bitmaps.get(name).getWidth() / 32);
            }
        }
    }

}
