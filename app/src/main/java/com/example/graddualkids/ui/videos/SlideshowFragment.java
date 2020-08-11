package com.example.graddualkids.ui.videos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graddualkids.R;
import com.example.graddualkids.audioRecord.AudioAdapter;
import com.example.graddualkids.audioRecord.AudioRecorder;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    ////// VIDEO YOUTUBE /////////////
    private RecyclerView recyclerView;
    Vector<YoutubeVideos> youtubeVideos = new Vector<>();
    private LinearLayout linearLayoutURL;
    private RelativeLayout relativeLayoutVideos;
    private EditText edtUrl, edtTasks;
    private Button btnSearch, btnAdd,btnDeleteLastOne;
    /////////////////////////

    //////  IMAGES   //////////////
    private ImageView imageView, imageView1, imageView2,imageView3;
    private ImageView [] imageViews;
    private TextView [] txtViews;
    private Button [] btnButtons;
    private TextView txtAddImg0, txtAddImg1, txtAddImg2,txtAddImg3;
    private Button btnDeleteImg0, btnDeleteImg1, btnDeleteImg2,btnDeleteImg3;
    Uri uri;
    ///////////////////////////////////

    ////// GRABACION //////////
    private LinearLayout linearLayoutMic;
    private LinearLayout linearLayoutRecorderFragment;
    private LinearLayout linearLayoutRecyclerView;
    private ImageButton imbAddRecord;
    private TextView txtCancel;
        //del include
    private ImageButton imbRecordMic;
    private Chronometer timer;
        ////////
    private boolean isRecording = false;
    private int PERMISSION_CODE = 21;   // creado, no tan necesario
    private MediaRecorder mediaRecorder;
    private String recordFile;
    private String fileNameRecord;
    //////////////////////////

    //////// MOSTRAR AUDIOS ///////
    private AudioAdapter audioAdapter;
    private RecyclerView audioList;
    private File[] audioFiles;
    private ImageButton imbPlayBnt;
    private SeekBar seekBar;

    AudioRecorder audioRecorder2;
    ////////////////////////

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_t_videos, container, false);
       // final TextView textView = root.findViewById(R.id.text_slideshow);

        youtubeVideoUrl(root);
        imagesWithCrop(root);
        recorderAudio(root);


        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
       //         textView.setText(s);
            }
        });
        return root;
    }

    /********** GRACACION Y AUDIOS**/
    private void recorderAudio(View root) {

        audioRecorder2 = new AudioRecorder(getContext(),getActivity()); //usando clase AudioRecorder

        audioList = root.findViewById(R.id.recycleview_audios);
        txtCancel = root.findViewById(R.id.txt_cancelar_record);
        imbAddRecord = root.findViewById(R.id.imb_add_record);

        linearLayoutMic = root.findViewById(R.id.linearLayout_recorder_fragment_mic);
        linearLayoutRecorderFragment = root.findViewById(R.id.linearLayout_recorder_fragment);
        linearLayoutRecyclerView = root.findViewById(R.id.linearLayout_recorder_fragment_recyclerview);

        imbAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutRecorderFragment.setVisibility(View.VISIBLE);
                linearLayoutMic.setVisibility(View.VISIBLE);
                linearLayoutMic.requestFocus();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAudios();
            }
        });

        /**varibales del include*/
        timer = root.findViewById(R.id.record_timer);
        imbRecordMic = root.findViewById(R.id.imb_record_button);

        imbRecordMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording){

                    audioRecorder2.stopRecording();
                    imbRecordMic.setBackgroundResource(R.drawable.ic_mic);
                    showAudios();
                    isRecording = false;
                }else
                    if (audioRecorder2.checkPermissions()) {

                        audioRecorder2.startRecording(timer,mediaRecorder);
                        imbRecordMic.setBackgroundResource(R.drawable.ic_stop);
                        isRecording = true;
                    }
                }
        });
    }

    private void showAudios() {
        linearLayoutMic.setVisibility(View.GONE);

        String path = getActivity().getExternalFilesDir("/").getAbsolutePath();
        if (audioRecorder2.showAudios(path, audioList))
            linearLayoutRecorderFragment.setVisibility(View.VISIBLE);
        else linearLayoutRecorderFragment.setVisibility(View.GONE);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (isRecording){
            audioRecorder2.stopRecording();
        }
    }

    /****************************/

    /****       IMAGENES  ******/
    private void imagesWithCrop(View root) {
        /** cambiar por recyclerView **/
        imageViews = new ImageView[] {
                imageView1 = root.findViewById(R.id.imgview_images1),
                imageView2 = root.findViewById(R.id.imgview_images2),
                imageView3 = root.findViewById(R.id.imgview_images3)
        };
        txtViews = new TextView[] {
                txtAddImg1 = root.findViewById(R.id.txt_add_images1),
                txtAddImg2 = root.findViewById(R.id.txt_add_images2),
                txtAddImg3 = root.findViewById(R.id.txt_add_images3)
        };
        btnButtons = new Button[] {
                btnDeleteImg1 = root.findViewById(R.id.btn_delete_images1),
                btnDeleteImg2 = root.findViewById(R.id.btn_delete_images2),
                btnDeleteImg3 = root.findViewById(R.id.btn_delete_images3)
        };


        View.OnClickListener onClickListenerAddImg = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.txt_add_images1: imageView= imageViews[0]; btnDeleteImg0=btnButtons[0]; txtAddImg0=txtViews[0];break;
                    case R.id.txt_add_images2: imageView= imageViews[1]; btnDeleteImg0=btnButtons[1];txtAddImg0=txtViews[1];break;
                    case R.id.txt_add_images3: imageView= imageViews[2]; btnDeleteImg0=btnButtons[2];txtAddImg0=txtViews[2];break;
                }
                CropImage.startPickImageActivity(getContext(),SlideshowFragment.this);
//                btnDeleteImg0.setVisibility(View.VISIBLE);
//                txtAddImg0.setVisibility(View.GONE);
            }
        };

        txtViews[0].setOnClickListener(onClickListenerAddImg);
        txtViews[1].setOnClickListener(onClickListenerAddImg);
        txtViews[2].setOnClickListener(onClickListenerAddImg);

        View.OnClickListener onClickListenerDeleteImg = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_delete_images1:
                        imageView= imageViews[0];
                        btnDeleteImg0=btnButtons[0];
                        txtAddImg0=txtViews[0]; break;
                    case R.id.btn_delete_images2:
                        imageView= imageViews[1];
                        btnDeleteImg0=btnButtons[1];
                        txtAddImg0=txtViews[1]; break;
                    case R.id.btn_delete_images3:
                        imageView= imageViews[2];
                        btnDeleteImg0=btnButtons[2];
                        txtAddImg0=txtViews[2]; break;
                }

                imageView.setImageBitmap(null);
                txtAddImg0.setVisibility(View.VISIBLE);
                btnDeleteImg0.setVisibility(View.GONE);
            }
        };

        btnButtons[0].setOnClickListener(onClickListenerDeleteImg);
        btnButtons[1].setOnClickListener(onClickListenerDeleteImg);
        btnButtons[2].setOnClickListener(onClickListenerDeleteImg);
    }
    /*********************/

    private void youtubeVideoUrl(View root) {

        recyclerView = root.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        linearLayoutURL = root.findViewById(R.id.linearLayout_url);
        relativeLayoutVideos = root.findViewById(R.id.relativeLayout_video);

        edtUrl = root.findViewById(R.id.edt_url);
        edtTasks = root.findViewById(R.id.edt_tasks);
        btnSearch = root.findViewById(R.id.btn_search);
        btnAdd = root.findViewById(R.id.btn_add);
        btnDeleteLastOne = root.findViewById(R.id.btn_delete_lastOne);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutURL.setVisibility(View.GONE);
                relativeLayoutVideos.setVisibility(View.VISIBLE);

                String url=edtUrl.getText().toString();
                url = url.substring(17);    // quitar https://www.youtube.com/embed/


                youtubeVideos.add(0,new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+url+"\" frameborder=\"0\" allow=\"accelerometer; picture-in-picture\" allowfullscreen></iframe>"));
                VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
                //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3)); //hace que los videos aparescan horizontal
                recyclerView.setAdapter(videoAdapter);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutURL.setVisibility(View.VISIBLE);
                relativeLayoutVideos.setVisibility(View.GONE);
                edtUrl.setText("");

            }
        });

        btnDeleteLastOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeVideos.remove(0);
                recyclerView.removeViewAt(0);
            }
        });
    }


    ////////    IMAGENES   ///////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imageUri = CropImage.getPickImageResultUri(getContext(), data);
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), imageUri)){
                uri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else {
                startCrop(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) { // -1 = RESULT_OK
                imageView.setImageURI(result.getUri());

                // para que no se borre el +
                btnDeleteImg0.setVisibility(View.VISIBLE);
                txtAddImg0.setVisibility(View.GONE);
            }
        }
    }


    private void startCrop(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getContext(),SlideshowFragment.this);
    }

    //////////////////////
}
