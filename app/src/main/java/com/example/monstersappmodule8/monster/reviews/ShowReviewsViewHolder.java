package com.example.monstersappmodule8.monster.reviews;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monstersappmodule8.R;
import com.example.monstersappmodule8.databinding.ReviewRecyclerViewItemBinding;

import java.text.Format;
import java.text.SimpleDateFormat;


public class ShowReviewsViewHolder extends RecyclerView.ViewHolder {

    private ReviewRecyclerViewItemBinding binding;
    private static Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ShowReviewsViewHolder(@NonNull ReviewRecyclerViewItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void update(ShowReview review){
        this.binding.userNameTextView.setText(review.getUserFirstname() + " " + review.getUserLastname());
        String date = formatter.format(review.getDate());
        this.binding.reviewedOnTextView.setText(date);
        this.binding.monsterReviewTitle.setText(review.getTitle());
        this.binding.monsterRateReviewRatingBar.setRating(review.getStars());
        this.binding.commentReviewTextMultiLine.setText(review.getComment());

        if(review.getMonsterImage().isEmpty()){
            this.binding.monsterReviewImageFilterView.setImageResource(R.drawable.monster_9);
        }else{
            int resID = binding.getRoot().getResources().getIdentifier(review.getMonsterImage(), "drawable", binding.getRoot().getContext().getPackageName());
            this.binding.monsterReviewImageFilterView.setImageResource(resID);
        }
    }
}
