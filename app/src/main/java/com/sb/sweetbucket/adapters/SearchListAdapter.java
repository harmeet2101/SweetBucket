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
import com.sb.sweetbucket.model.Search;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harmeet on 31-08-2019.
 */

public class SearchListAdapter extends BaseAdapter implements Filterable {


    private SearchFilter searchFilter;
    private ArrayList<Search> mSearchList;
    private ArrayList<Search> mSearchFilteredList;
    private Context mContext;
    public SearchListAdapter(Context mContext,ArrayList<Search> mSearchList) {
        this.mContext = mContext;
        this.mSearchList = mSearchList;
        this.mSearchFilteredList = mSearchList;
        getFilter();
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {

        return mSearchFilteredList.size();
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public Object getItem(int i) {
        return mSearchFilteredList.get(i);
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
        final Search mSearch = (Search)getItem(position);
        final String nameString = mSearch.getName();

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
                ArrayList<Search> tempList = new ArrayList<>();

                for (Search search: mSearchList) {
                    if (search.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(search);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mSearchList.size();
                filterResults.values = mSearchList;
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
            mSearchFilteredList = (ArrayList<Search>) results.values;
            notifyDataSetChanged();
        }
    }

    public  void  updateDataSource(ArrayList<Search> searchList){
        this.mSearchList = searchList;
        this.mSearchFilteredList = mSearchList;
        notifyDataSetChanged();
    }
}
