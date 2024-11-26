package com.example.monstersappmodule8.monster.show;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monstersappmodule8.R;
import com.example.monstersappmodule8.databinding.MonsterRecyclerViewItemBinding;
import com.example.monstersappmodule8.monster.Monster;
import com.example.monstersappmodule8.monster.OnMonsterClickListener;

public class MonsterViewHolder extends RecyclerView.ViewHolder {

    private MonsterRecyclerViewItemBinding binding;

    public MonsterViewHolder(@NonNull MonsterRecyclerViewItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void update(Monster monster){
        this.binding.nameTextView.setText(monster.getName());
        this.binding.descriptionTextView.setText(monster.getDescription());
        //Here round to 1 decimal, else we might get results as 2.3333333
        String roundedAverageStars = String.format("%.1f", monster.getAverageStars());
        this.binding.averageStarsTextView.setText(roundedAverageStars);
        this.binding.totalVotesTextView.setText(monster.getTotalVotes().toString());
        this.binding.scarinessDiscreteSlider.setValue(monster.getScariness());
        this.binding.averageStarsRatingBar.setRating(monster.getAverageStars());
        this.binding.scarinessTextView.setText(monster.getScariness().toString());

        if(monster.getImage().isEmpty()){
            this.binding.photoImageView.setImageResource(R.drawable.monster_16);
        }else{
            int resID = binding.getRoot().getResources().getIdentifier(monster.getImage(), "drawable", binding.getRoot().getContext().getPackageName());
            this.binding.photoImageView.setImageResource(resID);
        }

    }

    //This method will be used to bind a monster to an independent listener, so we will know which monster was clicked
    public void bind(Monster monster, OnMonsterClickListener onMonsterClickListener){

        //Listen for long clicks on the CardView
        binding.monsterMaterialCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                binding.monsterMaterialCardView.setChecked(!binding.monsterMaterialCardView.isChecked());
                Log.i("XYZ", "Monster: " + monster.getName() + " checked: " + binding.monsterMaterialCardView.isChecked());
                onMonsterClickListener.onClick(monster, view);
                return true;
            }
        });
        //Listen for clicks on the rate button
        binding.rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onMonsterClickListener.onClick(monster, view);
                //Create a bundle
                Bundle bundle = new Bundle();
                bundle.putSerializable("REVIEW_MONSTER", monster);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_showMonstersFragment_to_reviewMonsterFragment, bundle);
            }
        });

        binding.viewReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("SHOW_REVIEWS", monster);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_showMonstersFragment_to_showReviewsFragment, bundle);
            }
        });
        //Add here more listeners if needed.
    }

}
