package com.example.monstersappmodule8.monster.reviews;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.monstersappmodule8.databinding.ReviewRecyclerViewItemBinding;

public class ShowReviewsRecyclerViewAdapter extends ListAdapter<ShowReview, ShowReviewsViewHolder> {

    private ReviewRecyclerViewItemBinding binding;


    protected ShowReviewsRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ShowReview> DIFF_CALLBACK = new DiffUtil.ItemCallback<ShowReview>() {
        @Override
        public boolean areItemsTheSame(@NonNull ShowReview oldItem, @NonNull ShowReview newItem) {
            return oldItem.getId().intValue() == newItem.getId().intValue();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShowReview oldItem, @NonNull ShowReview newItem) {
            return  oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getComment().equals(newItem.getComment()) &&
                    oldItem.getStars().intValue() == newItem.getStars().intValue() &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getMonsterId().intValue() == newItem.getMonsterId().intValue() &&
                    oldItem.getMonsterName().equals(newItem.getMonsterName()) &&
                    oldItem.getMonsterImage().equals(newItem.getMonsterImage()) &&
                    oldItem.getUserFirstname().equals(newItem.getUserFirstname()) &&
                    oldItem.getUserLastname().equals(newItem.getUserLastname()) ;
        }
    };



    @NonNull
    @Override
    public ShowReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ReviewRecyclerViewItemBinding.inflate(inflater, parent, false);
        ShowReviewsViewHolder reviewsViewHolder = new ShowReviewsViewHolder(binding);
        return reviewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowReviewsViewHolder holder, int position) {
        ShowReview showReview = getItem(position);
        holder.update(showReview);

    }
}
