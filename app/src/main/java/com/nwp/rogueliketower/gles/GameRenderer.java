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
import android.opengl.Matrix;

import com.nwp.rogueliketower.R;
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
    private RendererData data;

    // How many bytes per float.
    private int bytesPerFloat;
    // Size of the position data in elements.
    private int positionDataSize;
    // Size of the color data in elements.
    private int colorDataSize;
    // Size of the texture coordinate data in elements.
    private int textureCoordinateDataSize;

    // Store the projection matrix. This is used to project the scene onto a 2D viewport.
    private float[] projectionMatrix = new float[16];

    // This will be used to pass in the transformation matrix.
    private int projectMatrixHandle;
    // This will be used to pass in model position information.
    private int positionHandle;
    // This will be used to pass in model color information.
    private int colorHandle;
    // This will be used to pass in model texture coordinate information.
    private int textureCoordinateHandle;
    // This is a handle to our texture data.
    private int mTextureDataHandle;
    // This will be used to pass in the texture.
    private int textureUniformHandle;
    // This is a handle to our per-vertex cube shading program.
    private int mProgramHandle;

    public GameRenderer(Context activity, Game game) {
        this.activityContext = activity;
        this.game = game;
        this.data = game.getRendererData();
        this.bytesPerFloat = data.bytesPerFloat;
        this.positionDataSize = data.positionDataSize;
        this.colorDataSize = data.colorDataSize;
        this.textureCoordinateDataSize = data.textureCoordinateDataSize;
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
        mProgramHandle = makeProgram(vertexShaderHandle, fragmentShaderHandle, attributes);
        GLES20.glUseProgram(mProgramHandle);

        // Set program handles.
        projectMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_ProjectionMatrix");
        positionHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Position");
        colorHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Color");
        textureCoordinateHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_TexCoordinate");
        textureUniformHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_Texture");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to the same size as the screen.
        GLES20.glViewport(0, 0, width, height);

        // Scale the shapes to keep their original aspect ratio given the aspect ratio of the device screen.
        // The height of the shapes will stay the same while the width varies.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        // z-axis clipping parameters.
        final float near = -10.0f;
        final float far = 10.0f;

        // Use orthogonal view and scale the image to the right aspect ratio.
        Matrix.orthoM(projectionMatrix, 0, left, right, bottom, top, near, far);
        Matrix.setIdentityM(projectionMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Move the game forward by one frame / step.
        int nTiles = game.step();
        // Reset the screen.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        for(int i=0; i<nTiles; i++) {
            drawTile(i*6);
        }
    }

    /**
     * Draw one tile.
     */
    private void drawTile(int first) {
        // Load the texture.
        mTextureDataHandle = loadTextureFromRawResource(activityContext, R.drawable.chris);
        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(textureUniformHandle, 0);

        // Pass in the position information
        data.positions.position(0);
        GLES20.glVertexAttribPointer(positionHandle, positionDataSize, GLES20.GL_FLOAT, false,
                0, data.positions);
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Pass in the color information
        data.colors.position(0);
        GLES20.glVertexAttribPointer(colorHandle, colorDataSize, GLES20.GL_FLOAT, false,
                0, data.colors);
        GLES20.glEnableVertexAttribArray(colorHandle);

        // Pass in the texture coordinate information
        data.textureCoordinates.position(0);
        GLES20.glVertexAttribPointer(textureCoordinateHandle, textureCoordinateDataSize, GLES20.GL_FLOAT, false,
                0, data.textureCoordinates);
        GLES20.glEnableVertexAttribArray(textureCoordinateHandle);

        // Pass in the projection / scaling matrix.
        GLES20.glUniformMatrix4fv(projectMatrixHandle, 1, false, projectionMatrix, 0);

        // Draw the cube.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, first, 6);
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

    public static int loadTextureFromRawResource(final Context context, final int resourceId) {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] == 0) {
            throw new RuntimeException("Error generating texture name.");
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        // No pre-scaling.
        options.inScaled = false;

        // Read in the resource.
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        // Bind to the texture in OpenGL.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

        // Set filtering.
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // Recycle the bitmap, since its data has been loaded into OpenGL.
        bitmap.recycle();

        return textureHandle[0];
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
