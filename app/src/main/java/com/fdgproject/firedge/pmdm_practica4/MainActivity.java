package com.fdgproject.firedge.pmdm_practica4;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class MainActivity extends Activity {

    private VideoView vv;
    private int position = 0;
    private MediaController media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Para dar fullscreen
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        vv = (VideoView) findViewById(R.id.vv_video);
        Intent intent = getIntent();
        Uri data = intent.getData();
        //Aqui creamos el objeto de Media si no existe
        if(media == null){
            media = new MediaController(this);
        }
        if(intent.getType().equals("video/mp4")){
            try {
                //Le damos los controles a nuestro video
                vv.setMediaController(media);
                //Le damos la ruta del video
                vv.setVideoURI(data);
            } catch (Exception e){
                Log.v("Error Video", e.toString());
            }
            vv.requestFocus();
            vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    vv.seekTo(position);
                    if(position == 0){
                        vv.start();
                    } else {
                        vv.pause();
                    }
                }
            });
        }
    }

    //Métodos para recuperar por donde iba el video al girar la pantalla
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", vv.getCurrentPosition());
        vv.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("pos");
        vv.seekTo(position);
    }

}
