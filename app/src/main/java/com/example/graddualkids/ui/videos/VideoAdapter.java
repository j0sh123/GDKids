package com.example.graddualkids.ui.videos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graddualkids.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YoutubeVideos> youtubeVideosList;

    public VideoAdapter(List<YoutubeVideos> youtubeVideos) {
        this.youtubeVideosList = youtubeVideos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout._include_classes_view_teacher,parent,false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.videoWeb.loadData(youtubeVideosList.get(position).getVideoUrl(),"text/html", "utf-8");
    }

    public  int getItemCount() {return youtubeVideosList.size();}

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;

        public VideoViewHolder(View itemView){
            super(itemView);
            // todos lo que vas a mostrar
            videoWeb = (WebView) itemView.findViewById(R.id.video_webView);
            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient(){

            });
        }
    }
}
