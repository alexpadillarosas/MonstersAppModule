package com.example.monstersappmodule8.monster.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.monstersappmodule8.databinding.ReviewMonsterFragmentBinding;
import com.example.monstersappmodule8.login.LoginViewModel;
import com.example.monstersappmodule8.login.User;
import com.example.monstersappmodule8.monster.Monster;
import com.google.android.material.snackbar.Snackbar;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewMonsterFragment extends Fragment {

    private ReviewMonsterViewModel mViewModel;
    private ReviewMonsterFragmentBinding binding;
    private Monster monster;
    private Integer rate = 0;

    public static ReviewMonsterFragment newInstance() {
        return new ReviewMonsterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ReviewMonsterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        User loggedInUser = loginViewModel.getLoggedInUser();

        binding.userNameTextView.setText(loginViewModel.fullName(loggedInUser));

        mViewModel = new ViewModelProvider(this).get(ReviewMonsterViewModel.class);



        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("REVIEW_MONSTER")){
            monster = bundle.getSerializable("REVIEW_MONSTER", Monster.class);
            binding.monsterNameTextView.setText(monster.getName());

            //Check if the user has already reviewed the monster
            Review review = mViewModel.find(monster.getId(), loggedInUser.getId());
            if(review != null){
                //The user has already reviewed the monster, therefore show the data
                binding.reviewEditTextTextMultiLine.setText(review.getComment());
                binding.monsterRateReviewRatingBar.setRating(review.getStars());
                String date = formatter.format(review.getDate());
                binding.reviewedOnTextView.setText(date);
                //and disable everything
                binding.reviewEditTextTextMultiLine.setEnabled(false);
                binding.monsterRateReviewRatingBar.setEnabled(false);
                binding.submitReviewButton.setEnabled(false);
                binding.reviewedOnLabelTextView.setVisibility(View.VISIBLE);
                binding.reviewedOnTextView.setVisibility(View.VISIBLE);
            }else{
                //The monster has not been reviewed by the user
                binding.reviewEditTextTextMultiLine.setEnabled(true);
                binding.monsterRateReviewRatingBar.setEnabled(true);
                binding.submitReviewButton.setEnabled(true);
                binding.reviewedOnLabelTextView.setVisibility(View.GONE);
                binding.reviewedOnTextView.setVisibility(View.GONE);
            }

            binding.monsterRateReviewRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    rate = (int) ratingBar.getRating();
                }
            });
        }

        binding.cancelReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigateUp();
            }
        });

        binding.submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do validations
                if(rate == 0){
                    Snackbar.make(view, "Rate is needed", Snackbar.LENGTH_LONG).show();
                    return;
                }
                String title = binding.titleReviewTIL.getEditText().getText().toString();
                if(title.isEmpty()){
                    Snackbar.make(view, "Title is needed", Snackbar.LENGTH_LONG).show();
                    return;
                }

                String userReview = binding.reviewEditTextTextMultiLine.getText().toString();

                Review review = new Review(rate, title, userReview, new Date(), monster.getId(), loggedInUser.getId());
                //Add the review
                mViewModel.insert(review);
                //update the monster total votes and
                mViewModel.updateMonsterStatistics(monster);

                NavController navController = Navigation.findNavController(view);
                navController.navigateUp();
            }
        });
    }
}