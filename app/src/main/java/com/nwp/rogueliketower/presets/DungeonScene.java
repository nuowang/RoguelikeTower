package com.nwp.rogueliketower.presets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nwp.rogueliketower.core.GameData;
import com.nwp.rogueliketower.stores.ParameterStore;

/*
 * For dungeon generation, I use an index coordinate system that starts from index 0.
 * Each individual component of the dungeon, such as room or hallway, are created in their own
 * model index coordinate system. They are then rotated and translated to be pieced together.
 */
public class DungeonScene {
    public static void genDungeon(Context context, GameData gd, String mapName) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        int resourceId = context.getResources().getIdentifier(mapName, "drawable", context.getPackageName());
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        // Generate floor.
        for (int i = 0; i < ParameterStore.FLOOR_WIDTH - 2; i++) {
            for (int j = 0; j < ParameterStore.FLOOR_HEIGHT - 2; j++) {
                // gd.tg.tgs[]
            }
        }
        // Generate walls.
    }


//            this.coordinateData = new float[]{
//                // Tile 1.
//                -0.1f,  0.1f*1080 / 1920, 0.0f,
//                -0.1f, -0.1f*1080 / 1920, 0.0f,
//                0.1f,  0.1f*1080 / 1920, 0.0f,
//                -0.1f, -0.1f*1080 / 1920, 0.0f,
//                0.1f, -0.1f*1080 / 1920, 0.0f,
//                0.1f,  0.1f*1080 / 1920, 0.0f,
//                // Tile 2.
//                -0.1f,  0.3f*1080 / 1920, 0.0f,
//                -0.1f,  0.1f*1080 / 1920, 0.0f,
//                0.1f,  0.3f*1080 / 1920, 0.0f,
//                -0.1f,  0.1f*1080 / 1920, 0.0f,
//                0.1f,  0.1f*1080 / 1920, 0.0f,
//                0.1f,  0.3f*1080 / 1920, 0.0f,
//                // Tile 3.
//                0.1f,  0.1f*1080 / 1920, 0.0f,
//                0.1f, -0.1f*1080 / 1920, 0.0f,
//                0.3f,  0.1f*1080 / 1920, 0.0f,
//                0.1f, -0.1f*1080 / 1920, 0.0f,
//                0.3f, -0.1f*1080 / 1920, 0.0f,
//                0.3f,  0.1f*1080 / 1920, 0.0f,
//                // Tile 4.
//                0.1f,  0.3f*1080 / 1920, 0.0f,
//                0.1f,  0.1f*1080 / 1920, 0.0f,
//                0.3f,  0.3f*1080 / 1920, 0.0f,
//                0.1f,  0.1f*1080 / 1920, 0.0f,
//                0.3f,  0.1f*1080 / 1920, 0.0f,
//                0.3f,  0.3f*1080 / 1920, 0.0f
//    };
//        for (int i = 0; i< this.coordinateData.length; i++)
//            this.coordinateData[i] *= 2;
//
//        this.colorData = new float[]{
//        // Tile 1 (red).
//        1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                // Tile 2 (green).
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                // Tile 3 (blue).
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                // Tile 4 (yellow).
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f,
//                1.0f, 1.0f, 1.0f, 0.0f
//    };
//
//        this.textureCoordinateData = new float[]{
//        // Tile 1.
//                0.0f, 0.0f,
//                0.0f, 1.0f,
//                1.0f, 0.0f,
//                0.0f, 1.0f,
//                1.0f, 1.0f,
//                1.0f, 0.0f,
//                // Tile 2.
//                0.0f, 0.0f,
//                0.0f, 1.0f,
//                1.0f, 0.0f,
//                0.0f, 1.0f,
//                1.0f, 1.0f,
//                1.0f, 0.0f,
//                // Tile 3.
//                0.0f, 0.0f,
//                0.0f, 1.0f,
//                1/4f, 0.0f,
//                0.0f, 1.0f,
//                1/4f, 1.0f,
//                1/4f, 0.0f,
//                // Tile 4.
//                0.0f, 0.0f,
//                0.0f, 1.0f,
//                1.0f, 0.0f,
//                0.0f, 1.0f,
//                1.0f, 1.0f,
//                1.0f, 0.0f
//    };
//
//    textureID = new int[] {
//        0, 1, 2, 0
//    };
//    // Pre-load all TextureStore.
//    textureBitmaps = Resources.loadTexturesFromRawResource(activity, TextureStore.names);
}
