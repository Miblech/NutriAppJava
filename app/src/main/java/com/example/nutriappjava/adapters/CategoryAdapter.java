package com.example.nutriappjava.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutriappjava.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of categories within a RecyclerView.
 * Each category is represented by a simple text view within an item layout (item_category).
 * The adapter listens for click events on these items and notifies an OnCategoryClickListener when an item is clicked.
 * This design allows for flexible interaction with individual categories, enabling actions like navigating to a details page or filtering a list based on the selected category.
 */
@SuppressWarnings("deprecation")
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<String> categoryList = new ArrayList<>();
    private OnCategoryClickListener onCategoryClickListener;

    /**
     * Defines the behavior when a category item is clicked.
     */
    public interface OnCategoryClickListener {
        void onCategoryClick(String category);
    }

    /**
     * Sets the listener for category click events.
     *
     * @param onCategoryClickListener The listener to be notified when a category item is clicked.
     */
    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    /**
     * Updates the list of categories and refreshes the adapter.
     *
     * @param categoryList The new list of categories.
     */
    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view, onCategoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = categoryList.get(position);
        holder.categoryName.setText(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    /**
     * Holds the view references for each item in the RecyclerView.
     */
    static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView categoryName;
        OnCategoryClickListener onCategoryClickListener;

        public CategoryViewHolder(@NonNull View itemView, OnCategoryClickListener onCategoryClickListener) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            this.onCategoryClickListener = onCategoryClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onCategoryClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onCategoryClickListener.onCategoryClick(categoryName.getText().toString());
                }
            }
        }
    }
}
