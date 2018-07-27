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

package com.nwp.rogueliketower.gles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;

import com.nwp.rogueliketower.R;
import com.nwp.rogueliketower.assets.Textures;
import com.nwp.rogueliketower.core.Constants;
import com.nwp.rogueliketower.core.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameRenderer implements Renderer {
    private Context activityContext;
    private Game game;

    // Used to pass in tile coordinate data.
    private int coordinateHandle;
    // Used to pass in tile base color data.
    private int colorHandle;
    // Handle to the tile texture data.
    private int [] textureHandle;
    // Used to pass in tile texture data.
    private int textureUniformHandle;
    // Used to pass in tile texture coordinate data.
    private int textureCoordinateHandle;
    // The handle to the shader program.
    private int programHandle;

    public GameRenderer(Context activity, Game game) {
        this.activityContext = activity;
        this.game = game;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background clear color to black.
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Cull back faces.
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        // Fragments with smaller z are displayed in front.
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        // Enable texture mapping.
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        // Make the rendering program.
        final String vertexShader = loadTextFileFromRawResource(activityContext, R.raw.vertex_shader);
        final String fragmentShader = loadTextFileFromRawResource(activityContext, R.raw.fragment_shader);
        final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
        final String[] attributes = {};
        programHandle = makeProgram(vertexShaderHandle, fragmentShaderHandle, attributes);
        GLES20.glUseProgram(programHandle);

        // Set program handles.
        coordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        colorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
        textureCoordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate");
        textureUniformHandle = GLES20.glGetUniformLocation(programHandle, "u_Texture");

        // Pre-load all Textures.
        // TODO: Check total number of Textures to load against GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS.
        textureHandle = loadTextureFromRawResource(activityContext, Textures.names);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(textureUniformHandle, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to the same size as the screen.
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Check memory leak.
        // Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        // Debug.getMemoryInfo(memoryInfo);
        // String memMessage = String.format(
        //     "Memory: Pss=%.2f MB, Private=%.2f MB, Shared=%.2f MB",
        //     memoryInfo.getTotalPss() / 1024.0,
        //     memoryInfo.getTotalPrivateDirty() / 1024.0,
        //     memoryInfo.getTotalSharedDirty() / 1024.0);
        // System.out.println(memMessage);

        // Move the game forward by one step / frame.
        game.step();

        // Reset the screen.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Pass in the position information
        game.data.coordinates.position(0);
        GLES20.glVertexAttribPointer(coordinateHandle, Constants.coordinateDataSize,
            GLES20.GL_FLOAT, false, 0, game.data.coordinates);
        GLES20.glEnableVertexAttribArray(coordinateHandle);
        // Pass in the color information
        game.data.colors.position(0);
        GLES20.glVertexAttribPointer(colorHandle, Constants.colorDataSize,
            GLES20.GL_FLOAT, false, 0, game.data.colors);
        GLES20.glEnableVertexAttribArray(colorHandle);
        // Pass in the texture coordinate information
        game.data.textureCoordinates.position(0);
        GLES20.glVertexAttribPointer(textureCoordinateHandle, Constants.textureCoordinateDataSize,
            GLES20.GL_FLOAT, false, 0, game.data.textureCoordinates);
        GLES20.glEnableVertexAttribArray(textureCoordinateHandle);

        // Draw all tiles.
        for (int i = 0; i < game.data.nTiles; i++) {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, i*Constants.tileDataSize, Constants.tileDataSize);
        }
    }

    private int compileShader(final int shaderType, final String shaderSource) {
        int shaderHandle = GLES20.glCreateShader(shaderType);

        if (shaderHandle != 0) {
            // Pass in the shader source.
            GLES20.glShaderSource(shaderHandle, shaderSource);
            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);
            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0) {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

    private int makeProgram(final int vertexShaderHandle, final int fragmentShaderHandle,
                            final String[] attributes) {
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0) {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes.
            if (attributes != null) {
                final int size = attributes.length;
                for (int i = 0; i < size; i++) {
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)
            {
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0) {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }

    public static int [] loadTextureFromRawResource(final Context context, final String [] resourceNames) {
        final int[] textureHandle = new int[resourceNames.length];
        GLES20.glGenTextures(resourceNames.length, textureHandle, 0);


        for (int i = 0; i < resourceNames.length; i++){
            if (textureHandle[i] == 0) {
                throw new RuntimeException("Error generating texture name.");
            }

            // Read the texture.
            int resourceId = context.getResources().getIdentifier(resourceNames[i],
                "drawable", context.getPackageName());

            final BitmapFactory.Options options = new BitmapFactory.Options();
            // No pre-scaling.
            options.inScaled = false;

            // Read in the resource.
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL.
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[i]);

            // Set filtering.
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        return textureHandle;
    }

    public static String loadTextFileFromRawResource(final Context context, final int resourceID) {
        final InputStream inputStream = context.getResources().openRawResource(resourceID);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        final StringBuilder body = new StringBuilder();

        try {
            while ((line = bufferedReader.readLine()) != null) {
                body.append(line);
                body.append('\n');
            }
        } catch (IOException e) {
            return null;
        }

        return body.toString();
    }
}
