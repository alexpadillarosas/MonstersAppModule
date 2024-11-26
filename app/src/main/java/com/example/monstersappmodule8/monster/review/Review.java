package com.example.monstersappmodule8.monster.review;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.monstersappmodule8.login.User;
import com.example.monstersappmodule8.monster.Monster;

import java.util.Date;

@Entity(tableName = "REVIEW",
        foreignKeys = {
            @ForeignKey(entity = Monster.class,
                    parentColumns = "ID",
                    childColumns = "MONSTER_ID",
                    onDelete = CASCADE
            ),
            @ForeignKey(entity = User.class,
                    parentColumns = "ID",
                    childColumns = "USER_ID",
                    onDelete = CASCADE
            )
        },
        indices = { @Index(value = "MONSTER_ID"),       //This index is in place as we are going to query this table by monsterId, to retrieve all reviews made by users for a particular Monster, creating the index will speed up the look up
                    @Index(value = "USER_ID"),          //In this case when deleting a User from the User table, the db will query the review table by userId, to check if the delete can be performed safely.
                    @Index(value = {"MONSTER_ID", "USER_ID"}, unique = true)    //This is to not allow users to rate more than once a particular monster
        }

)
public class Review {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Integer id;

    @ColumnInfo(name = "STARS")
    private Integer stars;

    @ColumnInfo(name = "TITLE")
    private String title;

    @ColumnInfo(name = "COMMENT")
    private String comment;

    @ColumnInfo(name = "DATE")
    private Date date;

    @ColumnInfo(name = "MONSTER_ID")
    private Integer monsterId;

    @ColumnInfo(name = "USER_ID")
    private Integer userId;

    @Ignore
    public Review() {

    }

    public Review(Integer stars, String title, String comment, Date date, Integer monsterId, Integer userId) {
        this.stars = stars;
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.monsterId = monsterId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", stars=" + stars +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", monsterId=" + monsterId +
                ", userId=" + userId +
                '}';
    }
}
