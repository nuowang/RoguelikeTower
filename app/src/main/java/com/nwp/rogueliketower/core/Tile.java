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

public class Tile {
    public String type = "square";
    public float [] center = {0, 0, 0};
    public float radius = 0;
    public float width = 0;
    public float height = 0;
    public boolean visible = false;

    public float [] genGLESCoordinates() {
        float [] a = {0};
        return a;
    }

    public float [] genGLESTextureCoordinate() {
        float [] a = {0};
        return a;
    }

    public float [] genGLESColor() {
        float [] a = {0};
        return a;
    }

    public float [] genGLES() {
        float [] a = {0};
        return a;
    }

}
