package com.example.monstersappmodule8.monster.show;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.monstersappmodule8.R;
import com.example.monstersappmodule8.databinding.ShowMonstersFragmentBinding;
import com.example.monstersappmodule8.login.LoginViewModel;
import com.example.monstersappmodule8.login.User;
import com.example.monstersappmodule8.monster.Monster;
import com.example.monstersappmodule8.monster.OnMonsterClickListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ShowMonstersFragment extends Fragment implements OnMonsterClickListener {

    private ShowMonstersFragmentBinding binding;
    private MonsterRecyclerViewAdapter adapter;
    private LiveData<List<Monster>> allMonsters;
    private List<Monster> selectedMonsters;


    public static ShowMonstersFragment newInstance() {
        return new ShowMonstersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShowMonstersFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Here we are going to share the LoginViewModel, therefore we need to use requireActivity instead of this
        //Usually is 1 ViewModel per Fragment
        //But here we do it this way as we want to share the logged in user
        //Alternatives:
        // 1: store the logged in user using DataStore:  https://developer.android.com/topic/libraries/architecture/datastore#java The usage of Shared preferences is discouraged.
        // 2: Hilt
        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        User loggedInUser = loginViewModel.getLoggedInUser();

        //Get an observer and set it, we need requireActivity() else every time we come back to this screen the view Model gets recreated.
        ShowMonstersViewModel showMonstersViewModel = new ViewModelProvider(requireActivity()).get(ShowMonstersViewModel.class);

        allMonsters = showMonstersViewModel.getAllMonsters();
        selectedMonsters = showMonstersViewModel.getSelectedMonsters();

        binding.addMonsterFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_showMonstersFragment_to_addMonsterFragment);
            }
        });

        /**
         * Here notice we can implement different layout managers
         * binding.monstersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
         * binding.monstersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
         * binding.monstersRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
         */
        binding.monstersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        binding.monstersRecyclerView.setHasFixedSize(true);

        //Create the adapter
        adapter = new MonsterRecyclerViewAdapter(this);

        //Set it to the recyclerView
        binding.monstersRecyclerView.setAdapter(adapter);
        /**
         * SnapHelper : More useful in horizontal scrolling, it sets the item in the center of the screen
         */
        //SnapHelper snapHelper = new LinearSnapHelper();
        //snapHelper.attachToRecyclerView(binding.monstersRecyclerView);

        //create an observer to check for changes in every monster
        final Observer<List<Monster>> allMonstersObserver = new Observer<List<Monster>>() {
            @Override
            public void onChanged(List<Monster> monsters) {
                //when a monster changes, then make the recycler view to update the change by using the ListAdapter we previously implemented
                adapter.submitList(monsters);
            }
        };

        //make LiveData observe for changes
        allMonsters.observe(getViewLifecycleOwner(), allMonstersObserver);

        //for search
        binding.monsterSearchView.clearFocus();
        binding.monsterSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                filterList(text);
                return false;
            }
        });

    }
    //Filter method implemented to search in the recycler view
    private void filterList(String text) {
        List<Monster> filteredList = new ArrayList<Monster>();
        for (Monster monster : allMonsters.getValue()){
            if(monster.getName().toUpperCase().contains(text.toUpperCase())){
                filteredList.add(monster);
            }
        }

        if(filteredList.isEmpty()){
            Snackbar.make(getView(), "No matches found", Snackbar.LENGTH_LONG).show();
        }
        adapter.submitList(filteredList);
    }

    //Method to be called every time the user clicks the recycler view item
    @Override
    public void onClick(Monster monster, View view) {
        //Here we code what we are going to do every time a monster is clicked.

        Log.i("XYZ", view.getResources().getResourceName(view.getId()));
        //if the click was on the MaterialCard
        if (view instanceof MaterialCardView){
            MaterialCardView card = (MaterialCardView) view;
            if(card.isChecked()){
                selectedMonsters.add(monster);
            }else{
                selectedMonsters.remove(monster);
            }
        }


    }
}