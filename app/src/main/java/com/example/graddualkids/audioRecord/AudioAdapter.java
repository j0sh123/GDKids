package com.example.graddualkids.audioRecord;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graddualkids.R;

import java.io.File;
import java.io.IOException;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {

    private File[] recordFiles;
    private onItemListClick onItemListClick;

    private boolean isPlaying = false;

    private MediaPlayer mediaPlayer = null;
    private Runnable updateSeekBar;

    private Handler seekBarHandler;
    private SeekBar seekBar;

    private ImageButton imbPlayBnt;

    public AudioAdapter (File[] recordFiles, onItemListClick onItemListClick){
        this.recordFiles = recordFiles;
        this.onItemListClick = onItemListClick;
    }
    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._audio_single_record, parent,false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, final int position) {
        /** aqui se puede poner el click con el inicio de profesores, del boton */
        holder.record_title.setText(recordFiles[position].getName());
//        holder.record_date.setText(recordFiles[position].lastModified() + "");

    }

    @Override
    public int getItemCount() {
        return recordFiles.length;
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder {


        private TextView record_title;
        private TextView record_date;
        public ImageButton imbDelete;
        private ImageButton imbPlayBntLocal;
        private SeekBar seekBarLocal;
        private Handler seekBarHandlerLocal;

        public AudioViewHolder(@NonNull final View itemView) {
            super(itemView);

            imbPlayBntLocal = itemView.findViewById(R.id.imb_record_single_button);
            record_title = itemView.findViewById(R.id.txt_record_title);
            record_date = itemView.findViewById(R.id.txt_record_date);
            imbDelete = itemView.findViewById(R.id.imb_delete_record);
            seekBarLocal = itemView.findViewById(R.id.seekbar_player_single);


            imbPlayBntLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 File   file = recordFiles[getAdapterPosition()];

                    if (isPlaying){
                        stopAudio();
                        //playAudio(file);
                    }else {
                        /**para que funciones el seekBar en cada item*/
                        seekBar = seekBarLocal;
                        seekBarHandler = seekBarHandlerLocal;
                        imbPlayBnt = imbPlayBntLocal;
                        /**********/
                        playAudio(file);
                    }
                }
            });

            imbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordFiles[getAdapterPosition()].delete();
                    onItemListClick.onClickListenerReload();
                  //  Toast.makeText(v.getContext(),"eliminado" + recordFiles[getAdapterPosition()].getName(),Toast.LENGTH_SHORT).show();
                }
            });

            onItemListClick.onClickVisivility(imbDelete);

            /****   para que la barra se pueda adelantar o retorceder */
            seekBarLocal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (recordFiles!=null) {
                        //pauseAudio();
                    }
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (recordFiles != null){
                        mediaPlayer.seekTo(seekBar.getProgress());
                        //resumeAudio();
                    }
                }
            });
        }
    }

    public interface onItemListClick{
        void onClickListenerReload();
        void onClickVisivility(ImageButton imageButton);
    }

    /*****************************************/
    private void stopAudio() {
        isPlaying = false;
        mediaPlayer.pause(); //stop()
        seekBarHandler.removeCallbacks(updateSeekBar);
        imbPlayBnt.setBackgroundResource(R.drawable.ic_play_btn);
    }

    private void playAudio(File fileToPlay) {
        isPlaying = true;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileToPlay.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*****  reproducir ****/
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
            }
        });

        //seekbar
        seekBar.setMax(mediaPlayer.getDuration());
        seekBarHandler = new Handler();
        updateRannable();
        seekBarHandler.postDelayed(updateSeekBar,0);
        //////

        imbPlayBnt.setBackgroundResource(R.drawable.ic_stop);
    }

    private Integer audioDuration(File file){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer.getDuration();
    }

    private void updateRannable() {
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                seekBarHandler.postDelayed(this,0); // velocidad del seekBar
            }
        };
    }

}
