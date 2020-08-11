package com.example.graddualkids;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.graddualkids.audioRecord.AudioRecorder;
import com.example.graddualkids.model.StudentData;

public class ThomeworkReview extends AppCompatActivity {

    private CheckBox chkHomewrkFinished, chkHomewrkNot;
    private ImageView img1, img2, img3;
    private ImageButton imbSend;
    private LinearLayout linearLayoutNotification;
    private VideoView videoView;
    private EditText edtNotification;

    private AudioRecorder audioRecorder;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_thomework_review);

        // mostrar barra atras
        ImageView arrow = findViewById(R.id.img_arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // retroceder actividad anterior
            }
        });

        images();
        videos();
        audios();
        studentText();
        revision();


        TextView txtName  = findViewById(R.id.txt_student_name);
        String name = getIntent().getStringExtra("name");
        String cod = getIntent().getStringExtra("cod");
        txtName.setText(name);

           // studentData.studentDataArrayList.set(Integer.parseInt(position),studentData);
        // el checkbox de terminado tiene que estar seleccionado cuando este verde el
        //if (number == "1") chkHomewrkFinished.setChecked(true);


    }

    private void images() {
        img1 = findViewById(R.id.imgview_student_images1);
        img2 = findViewById(R.id.imgview_student_images2);
        img3 = findViewById(R.id.imgview_student_images3);

        img1.setImageResource(R.mipmap.img1);
        img2.setImageResource(R.mipmap.img2);
        img3.setImageResource(R.mipmap.img3);

    }

    private void videos() {
        videoView = findViewById(R.id.video_webView);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        String videoPath = "android.resource://"+getPackageName()+ "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        //videoView.start();
    }

    private void audios() {
        audioRecorder = new AudioRecorder(this, this);
        audioRecorder.VISIBILITY_BTN_DELETE = false;

        recyclerView = findViewById(R.id.recycleview_audios_review);
        linearLayout = findViewById(R.id.linearLayout_recyclerview);

        String path = this.getExternalFilesDir("/").getAbsolutePath();

        if (!audioRecorder.showAudios(path, recyclerView)){
            linearLayout.setVisibility(View.GONE);
        }

    }

    private void studentText() {

    }
    private void revision() {
        edtNotification = findViewById(R.id.edt_notification);

        chkHomewrkFinished = findViewById(R.id.chk_homewrk_finished);
        chkHomewrkNot = findViewById(R.id.chk_homewrk_not_finished);

        imbSend = findViewById(R.id.imb_send_notification);

        linearLayoutNotification = findViewById(R.id.linearLayout_notification);

        /****** CHECKED BOXES *****/
        chkHomewrkFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // para que no se selecciones los dos a la misma vez
                chkHomewrkNot.setChecked(false);
                linearLayoutNotification.setVisibility(View.GONE);

                if (chkHomewrkFinished.isChecked()) {
                    Toast.makeText(v.getContext(), "Buen Trabajo alumno", Toast.LENGTH_SHORT).show();
                    edtNotification.setText("");
                }
            }
        });
        /*****  notificacion  ****/
        chkHomewrkNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkHomewrkFinished.setChecked(false);
                if (chkHomewrkNot.isChecked()) {
                    linearLayoutNotification.setVisibility(View.VISIBLE);
                    //y con android:focusableInTouchMode="true"(xml) enfocan al layout que deseas
                    linearLayoutNotification.requestFocus();
                }
                else {linearLayoutNotification.setVisibility(View.GONE);}
            }
        });

        imbSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // enviando mensaje como notificacion a alumno
                Notifications notifications = new Notifications(v.getContext(),
                        "Mensaje de Profesora",edtNotification.getText().toString());
                // Toast.makeText(v.getContext(),"Mensaje Enviado" ,Toast.LENGTH_SHORT).show();

            }
        });
        /*********/
    }
}
