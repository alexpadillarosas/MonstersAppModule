package com.example.monstersappmodule8.monster.reviews;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.monstersappmodule8.databinding.ShowReviewsFragmentBinding;
import com.example.monstersappmodule8.monster.Monster;

import java.util.List;

public class ShowReviewsFragment extends Fragment {

    private ShowReviewsViewModel mViewModel;
    private ShowReviewsFragmentBinding binding;
    private  Monster monster;
    private List<ShowReview> reviewsByMonsterId;

    public static ShowReviewsFragment newInstance() {
        return new ShowReviewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShowReviewsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShowReviewsViewModel.class);

        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("SHOW_REVIEWS")){
            monster = bundle.getSerializable("SHOW_REVIEWS", Monster.class);

            //Set the Header
            binding.monsterNameReviewHeaderTextView.setText(monster.getName());
            binding.averageStarsReviewsRatingBar.setRating(monster.getAverageStars());
            binding.averageStarsReviewsTextView.setText(monster.getAverageStars().toString());
            binding.totalVoltesReviewsTextView.setText(monster.getTotalVotes().toString());

            //For the reviews
            reviewsByMonsterId = mViewModel.findReviewsByMonsterId(monster.getId());
            Log.i("XYZ", "" + reviewsByMonsterId.size());

            ShowReviewsRecyclerViewAdapter adapter = new ShowReviewsRecyclerViewAdapter();
            adapter.submitList(reviewsByMonsterId);

            binding.reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            binding.reviewsRecyclerView.setHasFixedSize(true);
            binding.reviewsRecyclerView.setAdapter(adapter);


        }



    }



}