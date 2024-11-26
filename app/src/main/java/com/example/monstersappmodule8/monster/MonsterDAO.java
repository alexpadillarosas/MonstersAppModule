package com.example.monstersappmodule8.monster;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MonsterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Monster monster);

    @Update
    void update(Monster monster);

    @Delete
    void delete(Monster monster);

    //We need a query that returns all monster we will display to users, so they will be able to write a review
    @Query("SELECT * FROM MONSTER")
    LiveData<List<Monster>> findAll();

    @Query("SELECT * FROM MONSTER WHERE ID = :id")
    Monster findById(Integer id);


}
