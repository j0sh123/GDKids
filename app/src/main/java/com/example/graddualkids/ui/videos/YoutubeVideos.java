package com.example.graddualkids.ui.videos;

public class YoutubeVideos {

    private String videoUrl;

    public YoutubeVideos(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
