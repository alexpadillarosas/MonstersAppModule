package com.example.monstersappmodule8.monster.show;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.monstersappmodule8.database.MonstersRepository;
import com.example.monstersappmodule8.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class ShowMonstersViewModel extends AndroidViewModel {

    private MonstersRepository monstersRepository;
    private LiveData<List<Monster>> allMonsters;
    private List<Monster> selectedMonsters;

    public ShowMonstersViewModel(@NonNull Application application) {
        super(application);
        monstersRepository = new MonstersRepository(application);
        allMonsters = monstersRepository.getMonsters();
        selectedMonsters = new ArrayList<>();
    }

    public LiveData<List<Monster>> getAllMonsters() {
        return allMonsters;
    }

    public List<Monster> getSelectedMonsters() {
        return selectedMonsters;
    }

    public void setSelectedMonsters(List<Monster> selectedMonsters) {
        this.selectedMonsters = selectedMonsters;
    }
}
