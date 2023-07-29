package com.example.syrus_media_mobile.Models;

import java.io.Serializable;

public class Video implements Serializable {
    int id;
    String title;
    String description;
    String file_path;
    String thumbnail_path;
    String genre;
    int user_id;
    public Video() {};
    public Video(String video_id) {
        super();
        this.id = Integer.parseInt(video_id);
    };
    public Video(int id, String title, String description, String file_path, String thumbnail_path, String genre,
                 int user_id) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.file_path = file_path;
        this.thumbnail_path = thumbnail_path;
        this.genre = genre;
        this.user_id = user_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
