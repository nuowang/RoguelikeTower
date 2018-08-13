package com.nwp.rogueliketower.scenes;

import com.nwp.rogueliketower.core.GameData;
import com.nwp.rogueliketower.core.Tile;
import com.nwp.rogueliketower.core.TileGroup;
import com.nwp.rogueliketower.stores.TextureStore;
import com.nwp.rogueliketower.utils.Resources;

public class DungeonScene {
    public static void genDungeon(GameData gameData) {

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
