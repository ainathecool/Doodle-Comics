package com.project.doodlecomics_20k1688_20i0665;

import java.io.Serializable;

public class Comics implements Serializable {

    String comicTitle;

    public String getComicCanvas() {
        return comicCanvas;
    }

    public void setComicCanvas(String comicCanvas) {
        this.comicCanvas = comicCanvas;
    }

    String comicCanvas;

    public Comics() {
    }

    public Comics(String comicTitle, String comicDescription, String comicGenre, String comicCover, String comicArtist, String comicCollabArtist, String comicLikes, String comicRemixes, String comicURL) {
        this.comicTitle = comicTitle;
        this.comicDescription = comicDescription;
        this.comicGenre = comicGenre;
        this.comicCover = comicCover;
        this.comicArtist = comicArtist;
        this.comicCollabArtist = comicCollabArtist;
        this.comicLikes = comicLikes;
        this.comicRemixes = comicRemixes;
        this.comicURL = comicURL;
    }

    public Comics(String title, String genre, String description, String comicCoverUri, String canvasImagePath, String artistEmail, String likes, String remixes) {
        comicTitle = title;
        comicGenre = genre;
        comicDescription = description;
        comicURL = comicCoverUri;
        comicArtist = artistEmail;
        comicLikes = likes;
        comicRemixes = remixes;
        comicCanvas = canvasImagePath;


    }

    public String getComicTitle() {
        return comicTitle;
    }

    public void setComicTitle(String comicTitle) {
        this.comicTitle = comicTitle;
    }

    public String getComicDescription() {
        return comicDescription;
    }

    public void setComicDescription(String comicDescription) {
        this.comicDescription = comicDescription;
    }

    public String getComicGenre() {
        return comicGenre;
    }

    public void setComicGenre(String comicGenre) {
        this.comicGenre = comicGenre;
    }

    public String getComicCover() {
        return comicCover;
    }

    public void setComicCover(String comicCover) {
        this.comicCover = comicCover;
    }

    public String getComicArtist() {
        return comicArtist;
    }

    public void setComicArtist(String comicArtist) {
        this.comicArtist = comicArtist;
    }

    public String getComicCollabArtist() {
        return comicCollabArtist;
    }

    public void setComicCollabArtist(String comicCollabArtist) {
        this.comicCollabArtist = comicCollabArtist;
    }

    public String getComicLikes() {
        return comicLikes;
    }

    public void setComicLikes(String comicLikes) {
        this.comicLikes = comicLikes;
    }

    public String getComicRemixes() {
        return comicRemixes;
    }

    public void setComicRemixes(String comicRemixes) {
        this.comicRemixes = comicRemixes;
    }

    public String getComicURL() {
        return comicURL;
    }

    public void setComicURL(String comicURL) {
        this.comicURL = comicURL;
    }

    String comicDescription;
    String comicGenre;
    String comicCover;
    String comicArtist;
    String comicCollabArtist;
    String comicLikes;
    String comicRemixes;
    String comicURL;

}
