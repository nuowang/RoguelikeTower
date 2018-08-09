package com.nwp.rogueliketower.stores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TextureStore {
    public Context context;
    public Bitmap[] bitmaps;

    public static final String [] names = {
        "chris",
        "chris2",
        "carl"
    };

    public TextureStore(Context context) {
        this.context = context;
        loadTexturesFromRawResource();

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
        if(ParameterStore.SAME_SIZE_TEXTURE && ParameterStore.SQUARE_TEXTURE) {

        }
    }
}
