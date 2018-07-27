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

import android.view.MotionEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GameData {
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
    // The buffers to pass the data to.
    public final FloatBuffer coordinates;
    public final FloatBuffer colors;
    public final FloatBuffer textureCoordinates;

    public GameData() {
        // Renderer data starts with zero tiles.
        this.nTiles = 4;
        // I chose to set the initial max number of tiles to 1000.
        this.maxTiles = 1000;

        this.coordinateData = new float[]{
                // Tile 1.
//                0.0f, 50.f, 0.0f,
//                0.0f, 0.0f, 0.0f,
//                50.f, 50.f, 0.0f,
//                0.0f, 0.0f, 0.0f,
//                50.f, 0.0f, 0.0f,
//                50.f, 50.f, 0.0f,
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

        this.colorData = new float[]{
                // Tile 1 (red).
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                // Tile 2 (green).
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                // Tile 3 (blue).
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                // Tile 4 (yellow).
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f
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

        // Initialize the buffers.
        coordinates = ByteBuffer.allocateDirect(coordinateData.length * Constants.bytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        coordinates.put(coordinateData).position(0);

        colors = ByteBuffer.allocateDirect(colorData.length * Constants.bytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        colors.put(colorData).position(0);

        textureCoordinates = ByteBuffer.allocateDirect(textureCoordinateData.length * Constants.bytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates.put(textureCoordinateData).position(0);

    }

    public int update() {
        float x = 1/10;
        float y = 1/10;
        if (currentEvent != null) {
            x = (currentEvent.getRawX() + 1 - 540) / 540;
            y = (-currentEvent.getRawY() - 1 + 960) / 960;
        }
        float centerx = 0;
        float centery = 0;
        for (int i = 0; i< coordinateData.length / 3; i++) {
            centerx += coordinateData[3*i+0];
            centery += coordinateData[3*i+1];
        }
        centerx = centerx / (coordinateData.length / 3);
        centery = centery / (coordinateData.length / 3);
        for (int i = 0; i< coordinateData.length / 3; i++) {
            coordinateData[3*i+0] += (x - centerx)/10;
            coordinateData[3*i+1] += (y - centery)/10;
        }

        //System.out.println(centerx);
        //System.out.println(centery);

        coordinates.put(coordinateData).position(0);
        return nTiles;
    }
}
