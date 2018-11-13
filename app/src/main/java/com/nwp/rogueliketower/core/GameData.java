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
import android.view.MotionEvent;

import com.nwp.rogueliketower.presets.DungeonScene;
import com.nwp.rogueliketower.stores.ParameterStore;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GameData {
    public Activity activity;
    // Stores the tiles in the game.
    public TileGroup tg = new TileGroup();
    // The motion event observed.
    public MotionEvent currentEvent;
    // GLES data holders.
    public float[] coordinateData;
    public float[] colorData;
    public float[] textureCoordinateData;
    public String[] textureNames;
    // GLES data buffer.
    public FloatBuffer coordinates;
    public FloatBuffer colors;
    public FloatBuffer textureCoordinates;

    public GameData(Activity activity) {
        this.activity = activity;

        // TODO: Decide how to model different groups.
        // 1: Menu group, z = 4.
        tg.tgs.add(new TileGroup());
        // 2: Effects group, z = 3.
        tg.tgs.add(new TileGroup());
        // 3: Enemy group, z = 2
        tg.tgs.add(new TileGroup());
        // 4. Hero group, z = 1.
        tg.tgs.add(new TileGroup());
        // 5. Dungeon group, z = 0.
        tg.tgs.add(new TileGroup());

        // Generate a dungeon and store its data in this GameData object.
        // FIXME: Eventually, TitleScene should be generated in this constructor instead.
        // TODO: Choose from available map IDs based on certain rules.
        String mapName = "map1";
        DungeonScene.genDungeon(activity,this, mapName);
    }

    public void updateBuffers() {
        // TODO: I am not doing any explicit garbage collection for float buffers and float arrays.
        // TODO: Is there a better way?

        // Delete inactive tiles first.
        deleteInactive(tg);
        // Get the number of active tiles in the game.
        int nTiles = getNoOfTiles(tg);

        // Initialize the buffers.
        coordinates = ByteBuffer.allocateDirect(coordinateData.length * ParameterStore.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        colors = ByteBuffer.allocateDirect(colorData.length * ParameterStore.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates = ByteBuffer.allocateDirect(textureCoordinateData.length * ParameterStore.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        // Fill in the float arrays.
        float[][] arraysData = genFloatArrayFromTg(tg);
        coordinateData = arraysData[0];
        colorData = arraysData[1];
        textureCoordinateData = arraysData[2];
        textureNames = genStringArrayFromTg(tg);

        // Attach array to buffers.
        coordinates.put(coordinateData).position(0);
        colors.put(colorData).position(0);
        textureCoordinates.put(textureCoordinateData).position(0);

    }

    /**
     * Recursively delete all of the inactive tiles in a TileGroup.
     */
    private static void deleteInactive(TileGroup tg) {
        // TODO.
    }

    /**
     * Recursively sum up the number of tiles in the tileGroups.
     */
    private static int getNoOfTiles(TileGroup tg) {
        int nTiles = tg.ts.size();
        for (TileGroup tgIn : tg.tgs) {
            nTiles += tgIn.ts.size() + getNoOfTiles(tgIn);
        }
        return nTiles;

    }

    /**
     * Recursively fill in arrays.
     */
    private static float [][] genFloatArrayFromTg(TileGroup tg) {
        int nTiles = getNoOfTiles(tg);
        // Return zero element array if there is no tile.
        if (nTiles <= 0) {
            return new float[0][0];
        }

        float[][] arrays = {new float[nTiles * ParameterStore.TILE_COORDINATE_DATA_SIZE],
                            new float[nTiles * ParameterStore.TILE_COLOR_DATA_SIZE],
                            new float[nTiles * ParameterStore.TILE_TEXTURE_COORDINATE_DATA_SIZE]};
        int currentIndex1 = 0;
        int currentIndex2 = 0;
        int currentIndex3 = 0;
        for (Tile t : tg.ts) {
            for (float f : t.genGLESCoordinates()) {
                arrays[0][currentIndex1] = f;
                currentIndex1++;
            }
            for (float f : t.genGLESColor()) {
                arrays[1][currentIndex2] = f;
                currentIndex2++;
            }
            for (float f : t.genGLESTextureCoordinate()) {
                arrays[2][currentIndex3] = f;
                currentIndex3++;
            }
        }
        for (TileGroup tgIn : tg.tgs) {
            for (Tile t : tgIn.ts) {
                for (float f : t.genGLESCoordinates()) {
                    arrays[0][currentIndex1] = f;
                    currentIndex1++;
                }
                for (float f : t.genGLESColor()) {
                    arrays[1][currentIndex2] = f;
                    currentIndex2++;
                }
                for (float f : t.genGLESTextureCoordinate()) {
                    arrays[2][currentIndex3] = f;
                    currentIndex3++;
                }
            }
        }
        for (TileGroup tgIn : tg.tgs) {
            float[][] arrays2 = genFloatArrayFromTg(tgIn);
            for (float f : arrays2[0]) {
                arrays[0][currentIndex1] = f;
                currentIndex1++;
            }
            for (float f : arrays2[1]) {
                arrays[1][currentIndex2] = f;
                currentIndex2++;
            }
            for (float f : arrays2[2]) {
                arrays[2][currentIndex3] = f;
                currentIndex3++;
            }
        }

        return arrays;
    }

    /**
     * Recursively fill in arrays.
     */
    private static String [] genStringArrayFromTg(TileGroup tg) {
        int nTiles = getNoOfTiles(tg);
        // Return zero element array if there is no tile.
        if (nTiles <= 0) {
            return new String[0];
        }

        String[] array = new String[nTiles];
        int currentIndex = 0;
        for (Tile t : tg.ts) {
            array[currentIndex] = t.texture;
            currentIndex++;
        }
        for (TileGroup tgIn : tg.tgs) {
            for (Tile t : tgIn.ts) {
                array[currentIndex] = t.texture;
                currentIndex++;
            }
        }
        for (TileGroup tgIn : tg.tgs) {
            String[] array2 = genStringArrayFromTg(tgIn);
            for (String s : array2) {
                array[currentIndex] = s;
                currentIndex++;
            }
        }

        return array;
    }

}
