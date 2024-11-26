package com.example.monstersappmodule8.monster.review;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.monstersappmodule8.database.MonstersRepository;
import com.example.monstersappmodule8.monster.Monster;

public class ReviewMonsterViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private final MonstersRepository monstersRepository;

    public ReviewMonsterViewModel(@NonNull Application application) {
        super(application);
        monstersRepository = new MonstersRepository(application);

    }

    public Long insert(Review review){
       return monstersRepository.insert(review);
    }

    public Review find(Integer monsterId, Integer userId){
        return monstersRepository.findReviewByMonsterAndUserId(monsterId, userId);
    }



    public void updateMonsterStatistics(Monster monster){
        ReviewStatistics reviewStatistics = monstersRepository.findReviewStatisticsByMonsterId(monster.getId());
        Log.i("XYZ", reviewStatistics.toString());
        monster.setAverageStars(reviewStatistics.getAverageStars());
        monster.setTotalVotes(reviewStatistics.getTotalVotes());
        monstersRepository.update(monster);
    }

}