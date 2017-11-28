package rohitkadam.audioplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button buttonStart,buttonPause,buttonStop,buttonSaveCurrent,buttonPlayLast;
    MediaPlayer mediaPlayer;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionCheck();
        buttonStart =findViewById(R.id.buttonStart);
        buttonPause=findViewById(R.id.buttonPause);
        buttonStop=findViewById(R.id.buttonStop);
        buttonSaveCurrent=findViewById(R.id.buttonCurrentPosition);
        buttonPlayLast=findViewById(R.id.buttonPlayFromLast);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
        buttonSaveCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCurrent();
            }
        });
        buttonPlayLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playLast();
            }
        });
    }
    public void prepare(){

        //mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.audio);
        File rootDir = Environment.getExternalStorageDirectory();
        String audioFilePath=rootDir.getAbsolutePath()+File.separator+"song.mp3";
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void start(){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }else{
            prepare();
            mediaPlayer.start();

        }
    }
    public  void pause(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
    }
    public void stop(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }
    public void saveCurrent(){
        position = mediaPlayer.getCurrentPosition();
        stop();
    }
    public  void playLast(){
       prepare();
       mediaPlayer.seekTo(position);
       mediaPlayer.start();
    }
    public  void permissionCheck(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            String[] permission={Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(MainActivity.this,permission,1);
            return;
        }
    }
}
