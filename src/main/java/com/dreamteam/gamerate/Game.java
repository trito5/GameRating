package com.dreamteam.gamerate;

public class Game {
    private int id;
    private String name;
    private String description;
    private int votes;
    private int totalScore;
    private double rating;
    private String imgUrl;

    public Game(int id, String name, String description, double rating,String imgUrl, int totalScore, int votes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.imgUrl=imgUrl;
        this.totalScore = totalScore;
        this.votes=votes;

    }

    public Game(){

    }
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", votes=" + votes +
                ", totalScore=" + totalScore +
                ", rating=" + rating +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
