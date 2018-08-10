/*
 * Roguelike Tower
 * Copyright (C) 2018 NWP
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.nwp.rogueliketower.core;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.nwp.rogueliketower.scenes.DungeonScene;
import com.nwp.rogueliketower.stores.ParameterStore;
import com.nwp.rogueliketower.stores.TextureStore;
import com.nwp.rogueliketower.utils.Resources;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class GameData {
    public Activity activity;
    public ArrayList<TileGroup> tileGroups = new ArrayList<>();
    public TextureStore textureStore;


    // The current number of displayed tiles.
    public int nTiles;
    // The total number of tiles that the float array can currently hold.
    // TODO: Make the float array dynamically adjustable.
    public int maxTiles;
    // The motion event observed.
    public MotionEvent currentEvent;

    // Data holders.
    public float[] coordinateData;
    public float[] colorData;
    public float[] textureCoordinateData;
    public int[] textureID;
    public Bitmap[] textureBitmaps;

    // The buffers to pass the data to.
    public final FloatBuffer coordinates;
    public final FloatBuffer colors;
    public final FloatBuffer textureCoordinates;

    public GameData(Activity activity) {
        this.activity = activity;
        // FIXME: TexureStore should really be a singleton.
        this.textureStore = new TextureStore(activity);
        // Generate a dungeon and store its data in this GameData object.
        // FIXME: Eventually, TitleScene should be generated in the constructor instead.
        DungeonScene.genDungeon(this);

        // Renderer data starts with zero tiles.
        this.nTiles = 4;
        // I chose to set the initial max number of tiles to 1000.
        this.maxTiles = 1000;

        this.coordinateData = new float[]{
                // Tile 1.
                -0.1f,  0.1f*1080 / 1920, 0.0f,
                -0.1f, -0.1f*1080 / 1920, 0.0f,
                 0.1f,  0.1f*1080 / 1920, 0.0f,
                -0.1f, -0.1f*1080 / 1920, 0.0f,
                 0.1f, -0.1f*1080 / 1920, 0.0f,
                 0.1f,  0.1f*1080 / 1920, 0.0f,
                // Tile 2.
                -0.1f,  0.3f*1080 / 1920, 0.0f,
                -0.1f,  0.1f*1080 / 1920, 0.0f,
                 0.1f,  0.3f*1080 / 1920, 0.0f,
                -0.1f,  0.1f*1080 / 1920, 0.0f,
                 0.1f,  0.1f*1080 / 1920, 0.0f,
                 0.1f,  0.3f*1080 / 1920, 0.0f,
                // Tile 3.
                 0.1f,  0.1f*1080 / 1920, 0.0f,
                 0.1f, -0.1f*1080 / 1920, 0.0f,
                 0.3f,  0.1f*1080 / 1920, 0.0f,
                 0.1f, -0.1f*1080 / 1920, 0.0f,
                 0.3f, -0.1f*1080 / 1920, 0.0f,
                 0.3f,  0.1f*1080 / 1920, 0.0f,
                // Tile 4.
                 0.1f,  0.3f*1080 / 1920, 0.0f,
                 0.1f,  0.1f*1080 / 1920, 0.0f,
                 0.3f,  0.3f*1080 / 1920, 0.0f,
                 0.1f,  0.1f*1080 / 1920, 0.0f,
                 0.3f,  0.1f*1080 / 1920, 0.0f,
                 0.3f,  0.3f*1080 / 1920, 0.0f
        };
        for (int i = 0; i< this.coordinateData.length; i++)
            this.coordinateData[i] *= 2;

        this.colorData = new float[]{
                // Tile 1 (red).
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                // Tile 2 (green).
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                // Tile 3 (blue).
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                // Tile 4 (yellow).
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f
        };

        this.textureCoordinateData = new float[]{
                // Tile 1.
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f,
                // Tile 2.
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f,
                // Tile 3.
                0.0f, 0.0f,
                0.0f, 1.0f,
                1/4f, 0.0f,
                0.0f, 1.0f,
                1/4f, 1.0f,
                1/4f, 0.0f,
                // Tile 4.
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        textureID = new int[] {
                0, 1, 2, 0
        };
        // Pre-load all TextureStore.
        textureBitmaps = Resources.loadTexturesFromRawResource(activity, TextureStore.names);

        // Initialize the buffers.
        coordinates = ByteBuffer.allocateDirect(coordinateData.length * ParameterStore.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        coordinates.put(coordinateData).position(0);

        colors = ByteBuffer.allocateDirect(colorData.length * ParameterStore.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        colors.put(colorData).position(0);

        textureCoordinates = ByteBuffer.allocateDirect(textureCoordinateData.length * ParameterStore.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates.put(textureCoordinateData).position(0);

    }

}
