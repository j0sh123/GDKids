package com.example.graddualkids.audioRecord;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AudioRecorder  implements AudioAdapter.onItemListClick {

    private Context context;
    private FragmentActivity getActivity; // para poder usar getActivity

    private int PERMISSION_CODE = 21;
    private Chronometer timer;
    private MediaRecorder mediaRecorder;
    private AudioAdapter audioAdapter;
    private String path;
    private RecyclerView recyclerView;

    public boolean VISIBILITY_BTN_DELETE = true;

    public AudioRecorder(Context context, FragmentActivity getActivity){
        this.context = context;
        this.getActivity = getActivity;
    }

    public void startRecording(Chronometer timer, MediaRecorder mediaRecorder) {
        timer.setBase(SystemClock.elapsedRealtime()); // para que regrese a cero
        timer.start();
        
        this.timer = timer;

        String recordPath =  getActivity.getExternalFilesDir("/").getAbsolutePath();

        // para que la grabaciones tengan nombres distintos, con fechas y segundos
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.ROOT);
        Date now = new Date();
        String recordFile = "/Recorded_"+formatter.format(now)+".3gp";


        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();

        this.mediaRecorder = mediaRecorder;
    }

    public void stopRecording() {
        timer.setBase(SystemClock.elapsedRealtime()); // para que regrese a cero
        timer.stop();
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public boolean showAudios(String path, RecyclerView recyclerView) {
        File directory = new File(path);
        File[] audioFiles = directory.listFiles();

            audioAdapter = new AudioAdapter(audioFiles, this);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(audioAdapter);

            this.path = path;
            this.recyclerView = recyclerView;

        if (audioFiles.length != 0) {
            return true;
        }
        else {
            Log.e("AudioRecorder","no hay archivos");
            return false;
        }
    }

    public boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            ActivityCompat.requestPermissions(getActivity, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_CODE);
            return false;
        }
    }


    @Override
    public void onClickListenerReload() {
        showAudios(path,recyclerView);
    }

    @Override
    public void onClickVisivility(ImageButton imageButton) {
        if (VISIBILITY_BTN_DELETE)
            imageButton.setVisibility(View.VISIBLE);
        else imageButton.setVisibility(View.GONE);
    }
}
