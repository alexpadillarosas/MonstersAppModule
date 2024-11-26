package com.example.monstersappmodule8.monster.review;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.monstersappmodule8.database.MonstersRepository;


public class ReviewViewModel extends AndroidViewModel {

    //A reference to talk to the repository
    private final MonstersRepository monstersRepository;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        monstersRepository = new MonstersRepository(application);

    }
    
    public void insert(Review review){
        monstersRepository.insert(review);
    }

//    public ReviewStatistics findReviewStatisticsByMonsterId(Review review){
//        monstersRepository.findReviewStatisticsByMonsterId(review.getMonsterId())
//    }

}
