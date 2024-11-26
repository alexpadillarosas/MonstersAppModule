package com.example.monstersappmodule8.monster.show;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.monstersappmodule8.databinding.MonsterRecyclerViewItemBinding;
import com.example.monstersappmodule8.monster.Monster;
import com.example.monstersappmodule8.monster.OnMonsterClickListener;

/**
 * ListAdapter handles addition and removal without the need to redraw the entire view, and even animate those changes.
 *
 * Another benefit of using ListAdapter is that it comes with animations for when an item is added or removed.
 * This provides a nice visual cue for the user to see the changes in the list.
 * Animations are possible without ListAdapter but they must be implemented by the developer and won’t have the same performance because the view will likely need to be redrawn along with being animated.
 */

public class MonsterRecyclerViewAdapter extends ListAdapter<Monster, MonsterViewHolder> {

    private MonsterRecyclerViewItemBinding binding;
    private OnMonsterClickListener onMonsterClickListener;

    protected MonsterRecyclerViewAdapter(OnMonsterClickListener onMonsterClickListener) {
        super(DIFF_CALLBACK);
        this.onMonsterClickListener = onMonsterClickListener;
    }

    /**
     * This helper method check the difference between 2 Monster
     *
     * DiffUtil is the secret ingredient that makes it possible for ListAdapter to change the items in the list efficiently.
     * DiffUtil compares the new list with the old list to figure out what was added, moved, and removed and outputs a list of update operations that converts the first list into the second efficiently.
     *
     * In order to identify new data, DiffUtil requires you to override areItemsTheSame() and areContentsTheSame().
     * areItemsTheSame() checks if two items are actually the same item. areContentsTheSame() checks if two items have the same data.
     */
    private static final DiffUtil.ItemCallback<Monster> DIFF_CALLBACK = new DiffUtil.ItemCallback<Monster>() {
        @Override
        public boolean areItemsTheSame(@NonNull Monster oldItem, @NonNull Monster newItem) {
            return oldItem.getId().equals(newItem.getId());
            // If the old monster and the new monster have the same ID then they represent
            // the same monster in our database
        }

        @Override
        public boolean areContentsTheSame(@NonNull Monster oldItem, @NonNull Monster newItem) {
            return  oldItem.getTotalVotes().equals(newItem.getTotalVotes()) &&
                    Float.compare(oldItem.getAverageStars(), newItem.getAverageStars()) == 0 &&
                    oldItem.getScariness().intValue() == newItem.getScariness().intValue() &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getImage().equals(newItem.getImage());
            //check if the content of the 2 monsters are the same
        }
    };

    /**
     * Creates a view holder whenever the RecyclerView needs a new one, it creates a view holder(data in one element of the recyclerView).
     * This is the moment when the row layout is inflated (grab the RecyclerItemViewBinding and turning it into GUI component).
     * Creates a new view Holder(MonsterViewHolder in this case) by passing the recently inflated view binding
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MonsterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Inflate the recyclerViewItem
        binding = MonsterRecyclerViewItemBinding.inflate(inflater, parent, false);
        //create a new ViewHolder passing the ViewBinding to it
        MonsterViewHolder monsterViewHolder = new MonsterViewHolder(binding);
        //return the inflated view Holder
        return monsterViewHolder;
    }

    /**
     * Takes a ViewHolder object and sets the proper list data (from the list) on the view
     * @param holder    an object of MonsterViewHolder class, representing each item (CardView content)
     *                  in the recyclerView
     * @param position  the position of the monster in the monsters list
     */
    @Override
    public void onBindViewHolder(@NonNull MonsterViewHolder holder, int position) {
        //get data from the list based on position, the getItem method it's implemented in the ListAdapter class we have extended
        Monster monster = getItem(position);
        //call the method to set the values in the MonsterViewHolder
        holder.update(monster);
        //We bind the monster with the listener
        holder.bind(monster, onMonsterClickListener);
//        holder.bind(monster);
    }
}
