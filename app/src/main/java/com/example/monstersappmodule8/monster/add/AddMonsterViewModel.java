package com.example.monstersappmodule8.monster.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.monstersappmodule8.database.MonstersRepository;
import com.example.monstersappmodule8.monster.Monster;

public class AddMonsterViewModel extends AndroidViewModel {

    private final MonstersRepository monstersRepository;

    private Monster monster;

    public AddMonsterViewModel(@NonNull Application application) {
        super(application);
        //instantiate the repository as we will need it to communicate the view model with the data storage
        monstersRepository = new MonstersRepository(application);
        //Create an instance of a monster as this view model will create a new one
        monster = new Monster();
        monster.setName("");
        monster.setDescription("");
        monster.setScariness(1);
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public void insert(Monster monster){
        monstersRepository.insert(monster);
    }


}
