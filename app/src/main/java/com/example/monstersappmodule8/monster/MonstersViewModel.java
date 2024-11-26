package com.example.monstersappmodule8.monster;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Date;

public class MonstersViewModel extends AndroidViewModel {

    //variable used to store a monster
    private Monster monster;

    public MonstersViewModel(@NonNull Application application) {
        super(application);
        monster = new Monster();
        monster.setLastUpdate(new Date(System.currentTimeMillis()));
    }
    // TODO: Implement the ViewModel


    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }


}