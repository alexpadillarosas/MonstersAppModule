package com.example.monstersappmodule8.monster.reviews;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.util.Date;

public class ShowReview {

    @ColumnInfo(name = "ID")
    private Integer id;
    @ColumnInfo(name = "TITLE")
    private String title;
    @ColumnInfo(name = "COMMENT")
    private String comment;
    @ColumnInfo(name = "STARS")
    private Integer stars;
    @ColumnInfo(name = "DATE")
    private Date date;
    @ColumnInfo(name = "MONSTER_ID")
    private Integer monsterId;
    @ColumnInfo(name = "MONSTER_NAME")
    private String monsterName;
    @ColumnInfo(name = "MONSTER_IMAGE")
    private String monsterImage;
    @ColumnInfo(name = "USER_FIRST_NAME")
    private String userFirstname;
    @ColumnInfo(name = "USER_LAST_NAME")
    private String userLastname;

    @Ignore
    public ShowReview() {
    }

    public ShowReview(Integer id, String title, String comment, Integer stars, Date date, Integer monsterId, String monsterName, String monsterImage, String userFirstname, String userLastname) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.stars = stars;
        this.date = date;
        this.monsterId = monsterId;
        this.monsterName = monsterName;
        this.monsterImage = monsterImage;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(Integer monsterId) {
        this.monsterId = monsterId;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public String getMonsterImage() {
        return monsterImage;
    }

    public void setMonsterImage(String monsterImage) {
        this.monsterImage = monsterImage;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    @Override
    public String toString() {
        return "ShowReview{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", stars=" + stars +
                ", date=" + date +
                ", monsterId=" + monsterId +
                ", monsterName='" + monsterName + '\'' +
                ", monsterImage='" + monsterImage + '\'' +
                ", userFirstname='" + userFirstname + '\'' +
                ", userLastname='" + userLastname + '\'' +
                '}';
    }
}
