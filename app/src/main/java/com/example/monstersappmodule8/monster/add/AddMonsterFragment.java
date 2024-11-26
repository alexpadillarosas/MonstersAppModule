package com.example.monstersappmodule8.monster.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.monstersappmodule8.databinding.AddMonsterFragmentBinding;
import com.example.monstersappmodule8.login.LoginViewModel;
import com.example.monstersappmodule8.monster.Monster;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.Random;

public class AddMonsterFragment extends Fragment {

    private AddMonsterFragmentBinding binding;
    private Integer scariness = 0;

    public static AddMonsterFragment newInstance() {
        return new AddMonsterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AddMonsterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // safe to code using the UI

        //Get the shared view model to get the logged in User, use requireActivity as provider
        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        //Get the view model assigned to this fragment: AddMonsterViewModel
        AddMonsterViewModel addMonsterViewModel = new ViewModelProvider(this).get(AddMonsterViewModel.class);

        //getting data from the view model (if exists) and set it in the UI
        Monster monster = addMonsterViewModel.getMonster();


        binding.addMonsterNameTIL.getEditText().setText(monster.getName());
        binding.addMonsterDescriptionTIL.getEditText().setText(monster.getDescription());

        binding.scarinessDiscreteSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                scariness = (int) value;
            }
        });


        binding.addMonsterAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.addMonsterNameTIL.getEditText().getText().toString();
                if(name.isBlank()){
                    Snackbar.make(view, "Name is required", Snackbar.LENGTH_LONG).show();
                    return;
                }

                String description = binding.addMonsterDescriptionTIL.getEditText().getText().toString();
                if(description.isBlank()){
                    Snackbar.make(view, "Description is required", Snackbar.LENGTH_LONG).show();
                    return;
                }

                monster.setName(name);
                monster.setDescription(description);
                monster.setScariness(scariness);
                monster.setTotalVotes(0);
                monster.setAverageStars(0.0f);
                monster.setLastUpdate(new Date());

                Random ran = new Random();
                int value = ran.nextInt(30) + 1;
                String imageName = "monster_" + value;
                monster.setImage(imageName);


                addMonsterViewModel.insert(monster);

                NavController navController = Navigation.findNavController(view);
                navController.navigateUp();


            }
        });

        binding.addMonsterCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigateUp(); // This method pop the backStack, it can be used, but not if you want to pass data to the previous fragment, unless you save it in the View Model.
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}