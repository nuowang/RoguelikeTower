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
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;

import com.nwp.rogueliketower.stores.ParameterStore;
import com.nwp.rogueliketower.stores.TextureStore;
import com.nwp.rogueliketower.utils.Resources;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GameData {
    public Activity activity;

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

    // Screen size.
    public int screenHeight;
    public int screenWidth;
    public float screenAspectRatio;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public GameData(Activity activity) {
        this.activity = activity;

        // Renderer data starts with zero tiles.
        this.nTiles = 4;
        // I chose to set the initial max number of tiles to 1000.
        this.maxTiles = 1000;

        // Get the screen size.
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        this.screenWidth = size.x;
        this.screenHeight = size.y;
        this.screenAspectRatio = (float) this.screenWidth / (float) this.screenHeight;;

        this.coordinateData = new float[]{
                // Tile 1.
                -0.1f,  0.3f * screenAspectRatio, 0.0f,
                -0.1f,  0.1f * screenAspectRatio, 0.0f,
                 0.1f,  0.3f * screenAspectRatio, 0.0f,
                -0.1f,  0.1f * screenAspectRatio, 0.0f,
                 0.1f,  0.1f * screenAspectRatio, 0.0f,
                 0.1f,  0.3f * screenAspectRatio, 0.0f,
                // Tile 2.
                -0.1f,  0.1f * screenAspectRatio, 0.1f,
                -0.1f, -0.1f * screenAspectRatio, 0.1f,
                 0.1f,  0.1f * screenAspectRatio, 0.1f,
                -0.1f, -0.1f * screenAspectRatio, 0.1f,
                 0.1f, -0.1f * screenAspectRatio, 0.1f,
                 0.1f,  0.1f * screenAspectRatio, 0.1f,
                // Tile 3.
                 0.1f,  0.1f * screenAspectRatio, 0.0f,
                 0.1f, -0.1f * screenAspectRatio, 0.0f,
                 0.3f,  0.1f * screenAspectRatio, 0.0f,
                 0.1f, -0.1f * screenAspectRatio, 0.0f,
                 0.3f, -0.1f * screenAspectRatio, 0.0f,
                 0.3f,  0.1f * screenAspectRatio, 0.0f,
                // Tile 4.
                 0.1f,  0.3f * screenAspectRatio, 0.0f,
                 0.1f,  0.1f * screenAspectRatio, 0.0f,
                 0.3f,  0.3f * screenAspectRatio, 0.0f,
                 0.1f,  0.1f * screenAspectRatio, 0.0f,
                 0.3f,  0.1f * screenAspectRatio, 0.0f,
                 0.3f,  0.3f * screenAspectRatio, 0.0f
        };
        for (int i = 0; i< this.coordinateData.length; i++)
            this.coordinateData[i] *= 2;

        this.colorData = new float[]{
                // Tile 1.
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                // Tile 2.
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                // Tile 3.
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                // Tile 4.
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f
        };

        this.textureCoordinateData = new float[]{
                // Tile 1.
                0.0f, 0.0f,  // Triangle 1
                0.0f, 1.0f,  // Triangle 1
                1/8f, 0.0f,  // Triangle 1
                0.0f, 1.0f,  // Triangle 2
                1/8f, 1.0f,  // Triangle 2
                1/8f, 0.0f,  // Triangle 2
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
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f,
                // Tile 4.
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        textureID = new int[] {
                0, 2, 2, 2
        };
        // Pre-load all Textures.
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