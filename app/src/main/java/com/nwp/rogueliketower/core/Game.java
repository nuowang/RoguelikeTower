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
        float x = data.currentEventX;
        float y = data.currentEventY;

        if (x > -999) {
            float centerx = 0;
            float centery = 0;
            for (int i = 0; i < 6; i++) {
                centerx += data.coordinateData[3 * i];
            }
            for (int i = 0; i < data.coordinateData.length / 3; i++) {
                centery += data.coordinateData[3 * i + 1];
            }
            centerx = centerx / 6;
            centery = centery / (data.coordinateData.length / 3);

            float xDist = (x - centerx) * (float) data.screenWidth;
            float yDist = (y - centery) * (float) data.screenHeight;
            float distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);
            if (distance > 10) {
                for (int i = 0; i < data.coordinateData.length / 3; i++) {
                    data.coordinateData[3 * i + 0] += 10 * xDist / distance / ((float) data.screenWidth);
                    data.coordinateData[3 * i + 1] += 10 * yDist / distance / ((float) data.screenHeight);
                }
            }
        }

        counter += 1;
        // Do a move every ? seconds.
        if (counter % 10 == 0) {
            counter = 0;
            float numberOfFrames = 8;
            if (data.textureCoordinateData[0] < (numberOfFrames-1)/numberOfFrames) {
                data.textureCoordinateData[0] += 1/numberOfFrames;
                data.textureCoordinateData[2] += 1/numberOfFrames;
                data.textureCoordinateData[6] += 1/numberOfFrames;
                data.textureCoordinateData[4] += 1/numberOfFrames;
                data.textureCoordinateData[8] += 1/numberOfFrames;
                data.textureCoordinateData[10] += 1/numberOfFrames;
            }
            else {
                data.textureCoordinateData[0] = 0.0f;
                data.textureCoordinateData[2] = 0.0f;
                data.textureCoordinateData[6] = 0.0f;
                data.textureCoordinateData[4] = 1/numberOfFrames;
                data.textureCoordinateData[8] = 1/numberOfFrames;
                data.textureCoordinateData[10] = 1/numberOfFrames;
            }
        }

        data.coordinates.put(data.coordinateData).position(0);
        data.textureCoordinates.put(data.textureCoordinateData).position(0);

    }

}
