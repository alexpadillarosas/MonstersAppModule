package com.example.monstersappmodule8.monster.reviews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.monstersappmodule8.database.MonstersRepository;
import java.util.List;

public class ShowReviewsViewModel extends AndroidViewModel {

    private final MonstersRepository monstersRepository;
    private List<ShowReview> reviews;

    public ShowReviewsViewModel(@NonNull Application application) {
        super(application);
        monstersRepository = new MonstersRepository(application);
//        reviews = new ArrayList<>();
    }
    // TODO: Implement the ViewModel

    public List<ShowReview> findReviewsByMonsterId(Integer monsterId){
        return monstersRepository.findReviewsByMonsterId(monsterId);
    }

}