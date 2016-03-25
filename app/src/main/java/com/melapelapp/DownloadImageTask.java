package com.melapelapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by gesban on 10/7/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    String url;

    public DownloadImageTask(String url,ImageView bmImage) {
        this.bmImage = bmImage;
        this.url=url;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {

        Bitmap newbitMap;
        newbitMap = resize(result);
        bmImage.setImageBitmap(newbitMap);
        //original bmImage.setImageBitmap(result);


        //original bmImage.setImageBitmap(result);
    }
    //experiment
    private Bitmap resize(Bitmap result)
    {
        int bmWidth=result.getWidth();
        int bmHeight=result.getHeight();
        int ivWidth=bmImage.getWidth();
        int ivHeight=bmImage.getHeight();

        int new_width=ivWidth;

        int new_height = (int) Math.floor((double) bmHeight *( (double) new_width / (double) bmWidth));
        Bitmap newbitMap = Bitmap.createScaledBitmap(result,new_width,new_height, true);

        return newbitMap;
    }

}
