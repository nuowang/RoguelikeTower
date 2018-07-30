package com.nwp.rogueliketower.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resources {
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

    public static Bitmap[] loadTexturesFromRawResource(final Context context, final String [] resourceNames) {
        Bitmap[] bitmaps = new Bitmap[resourceNames.length];

        for (int i = 0; i < resourceNames.length; i++){
            // Read the texture.
            int resourceId = context.getResources().getIdentifier(resourceNames[i],
                    "drawable", context.getPackageName());

            final BitmapFactory.Options options = new BitmapFactory.Options();
            // No pre-scaling.
            options.inScaled = false;

            // Read in the resource.
            bitmaps[i] = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        }

        return bitmaps;
    }

}
