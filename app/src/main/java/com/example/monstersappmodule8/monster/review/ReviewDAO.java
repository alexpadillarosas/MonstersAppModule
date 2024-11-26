package com.example.monstersappmodule8.monster.review;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.monstersappmodule8.monster.reviews.ShowReview;

import java.util.List;

@Dao
public interface ReviewDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    Long insert(Review review);

    //Find the total number of stars and average for a specified monster, notice that we use column aliases
    //that matches the name of the properties in the ReviewAverage class
    @Query("SELECT COUNT(ID) as totalVotes, AVG(STARS) as averageStars FROM REVIEW WHERE MONSTER_ID = :monsterId")
    ReviewStatistics findReviewStatisticsByMonsterId(Integer monsterId);

    //We need a query to check if the user has already reviewed a Monster
    @Query("SELECT * FROM REVIEW WHERE MONSTER_ID = :monsterId AND USER_ID = :userId")
    Review findByMonsterAndUserId(Integer monsterId, Integer userId);

    //Query used when retrieving all comments for a particular monster
    @Query("SELECT R.ID as ID, R.TITLE, R.COMMENT, R.STARS, R.DATE, M.ID as MONSTER_ID, M.NAME as MONSTER_NAME, M.IMAGE as MONSTER_IMAGE, U.FIRST_NAME as USER_FIRST_NAME, U.LAST_NAME as USER_LAST_NAME" +
            " FROM REVIEW R" +
            " INNER JOIN MONSTER M" +
            " ON M.ID = R.MONSTER_ID" +
            " INNER JOIN USER U" +
            " ON U.ID = R.USER_ID" +
            " WHERE MONSTER_ID = :monsterId")
    List<ShowReview>  findReviewsByMonsterId(Integer monsterId);

}
