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
import android.os.SystemClock;

public class Game {
    public Activity activity;
    public GameData data;
    public int counter = 0;

    public Game(Activity activity, GameData gameData) {
        this.activity = activity;
        this.data = gameData;
    }

    public void update() {
        float x = 1/10;
        float y = 1/10;
        if (data.currentEvent != null) {
            x = (data.currentEvent.getRawX() + 1 - 540) / 540;
            y = (-data.currentEvent.getRawY() - 1 + 960) / 960;
        }
        float centerx = 0;
        float centery = 0;
        for (int i = 0; i< data.coordinateData.length / 3; i++) {
            centerx += data.coordinateData[3*i+0];
            centery += data.coordinateData[3*i+1];
        }
        centerx = centerx / (data.coordinateData.length / 3);
        centery = centery / (data.coordinateData.length / 3);
        for (int i = 0; i< data.coordinateData.length / 3; i++) {
            data.coordinateData[3*i+0] += (x - centerx)/10;
            data.coordinateData[3*i+1] += (y - centery)/10;
        }

        counter += 1;
        // Do a move every ? seconds.
        if (counter % 6 == 0) {
            if (data.textureCoordinateData[24] == 0.0f) {
                data.textureCoordinateData[24] = 1/4f;
                data.textureCoordinateData[26] = 1/4f;
                data.textureCoordinateData[30] = 1/4f;
                data.textureCoordinateData[28] = 2/4f;
                data.textureCoordinateData[32] = 2/4f;
                data.textureCoordinateData[34] = 2/4f;
            }
            else if (data.textureCoordinateData[24] == 1/4f) {
                data.textureCoordinateData[24] = 2/4f;
                data.textureCoordinateData[26] = 2/4f;
                data.textureCoordinateData[30] = 2/4f;
                data.textureCoordinateData[28] = 3/4f;
                data.textureCoordinateData[32] = 3/4f;
                data.textureCoordinateData[34] = 3/4f;
            }
            else if (data.textureCoordinateData[24] == 2/4f) {
                data.textureCoordinateData[24] = 3/4f;
                data.textureCoordinateData[26] = 3/4f;
                data.textureCoordinateData[30] = 3/4f;
                data.textureCoordinateData[28] = 4/4f;
                data.textureCoordinateData[32] = 4/4f;
                data.textureCoordinateData[34] = 4/4f;
            }
            else {
                data.textureCoordinateData[24] = 0.0f;
                data.textureCoordinateData[26] = 0.0f;
                data.textureCoordinateData[30] = 0.0f;
                data.textureCoordinateData[28] = 1/4f;
                data.textureCoordinateData[32] = 1/4f;
                data.textureCoordinateData[34] = 1/4f;
            }
        }

        data.coordinates.put(data.coordinateData).position(0);
        data.textureCoordinates.put(data.textureCoordinateData).position(0);


    }

}
