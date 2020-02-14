package com.example.audiovideoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener , MediaPlayer.OnCompletionListener{

    private VideoView videoPlay;
    private Button playBtn;
    private MediaController mediaController;
    private Button btnPlayMusic,btnPauseMusic;
    private MediaPlayer mediaPlayer;
    private SeekBar volumeSeekBar;
    private AudioManager audioManager;
    private SeekBar moveSeekBar;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoPlay = findViewById(R.id.videoPlay);
        playBtn = findViewById(R.id.playBtn);
        mediaPlayer = MediaPlayer.create(this,R.raw.heavyrain);

        btnPauseMusic = findViewById(R.id.btnPauseMusic);
        btnPlayMusic  =  findViewById(R.id.btnPlayMusic);

        volumeSeekBar = findViewById(R.id.seekBarVolume);
        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        int maxVolOfDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolOfDevice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar.setMax(maxVolOfDevice);
        volumeSeekBar.setProgress(currentVolOfDevice);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
             //   Toast.makeText(MainActivity.this,Integer.toString(progress), Toast.LENGTH_SHORT).show();
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaController = new MediaController(MainActivity.this);

        playBtn.setOnClickListener(MainActivity.this);

        btnPlayMusic.setOnClickListener(MainActivity.this);
        btnPauseMusic.setOnClickListener(MainActivity.this);

        moveSeekBar = findViewById(R.id.seekBarMove);
        moveSeekBar.setOnSeekBarChangeListener(MainActivity.this);
        moveSeekBar.setMax(mediaPlayer.getDuration());

        mediaPlayer.setOnCompletionListener(this);

    }

    @Override
    public void onClick(View buttonView) {

        switch(buttonView.getId()){

        case R.id.playBtn:
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() +
                "/" + R.raw.nature);

        videoPlay.setVideoURI(videoUri);
        videoPlay.setMediaController(mediaController);
        mediaController.setAnchorView(videoPlay);
        videoPlay.start();
        break;



        case R.id.btnPauseMusic:
        mediaPlayer.pause();
        timer.cancel();


        break;


        case R.id.btnPlayMusic:
        mediaPlayer.start();
            timer = new Timer();
        //    if(mediaPlayer.isPlaying()!= true){timer.cancel();}
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,1000);



        break;

        }
}

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
       // Toast.makeText(this,Integer.toString(progress), Toast.LENGTH_SHORT).show();
    if(fromUser){
       if(mediaPlayer.isPlaying()==false){mediaPlayer.start();}
        mediaPlayer.seekTo(progress);

    }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
mediaPlayer.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
mediaPlayer.start();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
    //if(mediaPlayer.isPlaying()==true) {
       timer.cancel(); Toast.makeText(this, "Timer Cncelled", Toast.LENGTH_SHORT).show();
        //   Toast.makeText(this, "Timer Cncelled", Toast.LENGTH_SHORT).show();}
    }
}
