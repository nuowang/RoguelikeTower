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
        "chris",
        "chris2",
        "carl"
    };
    // The actual texture.
    public static Bitmap[] bitmaps;
    // How many frames each texture contains.
    public static Map frames;

    public TextureStore(Context context) {
        this.context = context;
        loadTexturesFromRawResource();
        genTextureInfo();
    }

    private void loadTexturesFromRawResource() {
        bitmaps = new Bitmap[names.length];

        for (int i = 0; i < names.length; i++){
            // Read the texture.
            int resourceId = context.getResources().getIdentifier(names[i],
                    "drawable", context.getPackageName());

            final BitmapFactory.Options options = new BitmapFactory.Options();
            // No pre-scaling.
            options.inScaled = false;

            // Read in the resource.
            bitmaps[i] = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        }
    }

    private void genTextureInfo() {
        if(ParameterStore.SQUARE_32_TEXTURE) {
            frames = new HashMap();

            for(int i=0; i<names.length; i++) {
                // I assume that all of the frames in the animation are put on one line.
                frames.put(names[i], bitmaps[i].getWidth() / 32);
            }
        }
    }

}
