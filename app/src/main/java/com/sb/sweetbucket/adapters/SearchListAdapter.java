package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sb.sweetbucket.R;

import java.util.ArrayList;

/**
 * Created by harmeet on 31-08-2019.
 */

public class SearchListAdapter extends BaseAdapter implements Filterable {


    private SearchFilter searchFilter;
    private ArrayList<String> searchList;
    private ArrayList<String> filterdSearchList;
    private Context mContext;
    public SearchListAdapter(Context mContext,ArrayList<String> searchList) {
        this.mContext = mContext;
        this.searchList = searchList;
        this.filterdSearchList = searchList;
        getFilter();
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filterdSearchList.size();
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public Object getItem(int i) {
        return filterdSearchList.get(i);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Create list row view
     * @param position index
     * @param view current list item view
     * @param parent parent
     * @return view
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        final ViewHolder holder;
        final String nameString = (String) getItem(position);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.search_list_row_layout, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tvSearchItem);
            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(nameString);
        return view;
    }

    /**
     * Get custom filter
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (searchFilter == null) {
            searchFilter = new SearchFilter();
        }

        return searchFilter;
    }

    /**
     * Keep reference to children view to avoid unnecessary calls
     */
    static class ViewHolder {
        TextView name;
    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    private class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<String> tempList = new ArrayList<String>();

                // search content in friend list
                for (String nameString : searchList) {
                    if (nameString.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(nameString);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = searchList.size();
                filterResults.values = searchList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterdSearchList = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }

    public  void  updateDataSource(ArrayList<String> searchList){
        this.searchList = searchList;
        notifyDataSetChanged();
    }
}
