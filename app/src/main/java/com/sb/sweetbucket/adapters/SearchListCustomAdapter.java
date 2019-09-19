package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.ProductDetailsActivity;
import com.sb.sweetbucket.activities.ShopDetailActivity;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;
import com.sb.sweetbucket.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harmeet on 15-09-2019.
 */

public class SearchListCustomAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = SearchListCustomAdapter.class.getSimpleName();

    public static final int ITEM_TYPE__LOADING_LIST = 1;
    public static final int ITEM_TYPE__EMPTY_LIST = 2;
    public static final int ITEM_TYPE_CATEGORY_ITEM = 3;
    public static final int ITEM_TYPE_CATEGORY_ITEM_HEADING = 4;
    public static final int ITEM_TYPE_PRODUCT_ITEM = 5;
    public static final int ITEM_TYPE_PRODUCT_ITEM_HEADING = 6;
    public static final int ITEM_TYPE_SHOP_ITEM = 7;
    public static final int ITEM_TYPE_SHOP_ITEM_HEADING = 8;
    private List<Product> productResponseList;
    private List<Category> categoryList;
    private List<Shop> shopList;
    private int count =0;
    private Context mContext;


    private SearchListCustomAdapter.SearchFilter searchFilter;
    private ArrayList<String> searchList;
    private ArrayList<String> filterdSearchList;


    public SearchListCustomAdapter(List<Product> productResponseList, List<Category> categoryList,
                                   List<Shop> shopList, Context mContext) {
        this.productResponseList = productResponseList;
        this.categoryList = categoryList;
        this.shopList = shopList;
        this.mContext = mContext;
        this.searchList = (ArrayList<String>) getRawList();
        this.filterdSearchList =searchList;
       // this.searchList = (ArrayList<String>) getRawList();
       // this.filterdSearchList = searchList;
        getFilter();
    }

    @Override
    public int getCount() {

        return filterdSearchList.size();
    }

    @Override
    public Object getItem(int i) {
        return filterdSearchList.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        if (productResponseList == null && shopList==null && categoryList==null) {
            viewType = ITEM_TYPE__LOADING_LIST;
        } else if (productResponseList.isEmpty()&& shopList.isEmpty() && categoryList.isEmpty()) {
            viewType = ITEM_TYPE__EMPTY_LIST;
        }
        else if(position < categoryList.size()){
            if(categoryList.get(position).getName().equalsIgnoreCase("Heading")){
                return ITEM_TYPE_CATEGORY_ITEM_HEADING;
            }else
                return ITEM_TYPE_CATEGORY_ITEM;
        }
        else if(position - categoryList.size() < productResponseList.size()){
            if(productResponseList.get(position - categoryList.size())
                    .getName().equalsIgnoreCase("Heading")){
                return ITEM_TYPE_PRODUCT_ITEM_HEADING;
            }else
                return ITEM_TYPE_PRODUCT_ITEM;
        }
        else if(position - (categoryList.size()+productResponseList.size())< shopList.size()){
            if(shopList.get(position-(categoryList.size()+productResponseList.size())).getStoreName().equalsIgnoreCase("Heading")){
                return ITEM_TYPE_SHOP_ITEM_HEADING;
            }else
                return ITEM_TYPE_SHOP_ITEM;
        }

        return viewType;
    }

    @Override
    public int getViewTypeCount() {
        return filterdSearchList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        int type = getItemViewType(i);
        String str = (String) getItem(i);
        View v = view;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            switch (type){
               /* case ITEM_TYPE__LOADING_LIST:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading,
                            parent, false);
                    break;

                case ITEM_TYPE__EMPTY_LIST:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_list,
                            parent, false);
                    break;

                case ITEM_TYPE_CATEGORY_ITEM_HEADING:
                    v= inflater.from(mContext).inflate(R.layout.layout_search_item_heading,
                            parent, false);
                    break;*/
                case ITEM_TYPE_CATEGORY_ITEM: {
                    v = inflater.from(mContext).inflate(R.layout.layout_search_item,
                            parent, false);
                    CategoryViewHolder categoryViewHolder = new CategoryViewHolder(v);
                    categoryViewHolder.setData(str);
                    v.setTag(categoryViewHolder);
                }
                    break;
                /*case ITEM_TYPE_SHOP_ITEM_HEADING:
                    v= inflater.from(mContext).inflate(R.layout.layout_search_item_heading,
                            parent, false);
                    break;*/
                case ITEM_TYPE_SHOP_ITEM: {
                    v = inflater.from(mContext).inflate(R.layout.layout_search_item,
                            parent, false);
                    ShopViewHolder shopViewHolder = new ShopViewHolder(v, i);
                    shopViewHolder.setData(str);
                    v.setTag(shopViewHolder);
                }
                    break;
               /* case ITEM_TYPE_PRODUCT_ITEM_HEADING:
                    v= inflater.from(mContext).inflate(R.layout.layout_search_item_heading,
                            parent, false);
                    break;*/
                case ITEM_TYPE_PRODUCT_ITEM: {
                    v = inflater.from(mContext).inflate(R.layout.layout_search_item,
                            parent, false);
                    ProductViewHolder productViewHolder = new ProductViewHolder(v, i);
                    productViewHolder.setData(str);
                    v.setTag(productViewHolder);
                }
                    break;
            }
        }else {
            switch (type){
               /* case ITEM_TYPE_CATEGORY_ITEM_HEADING: {
                    TextView headingTextview = (TextView) v.findViewById(R.id.tvHeading);
                    headingTextview.setText("In Category");
                }
                break;
                case ITEM_TYPE_SHOP_ITEM_HEADING: {
                    TextView headingTextview = (TextView) v.findViewById(R.id.tvHeading);
                    headingTextview.setText("In Shops");
                }
                break;
                case ITEM_TYPE_PRODUCT_ITEM_HEADING: {
                    TextView headingTextview = (TextView) v.findViewById(R.id.tvHeading);
                    headingTextview.setText("In Products");
                }
                break;*/
                case ITEM_TYPE_CATEGORY_ITEM:
                {
                    CategoryViewHolder categoryViewHolder = (CategoryViewHolder)v.getTag();
                    categoryViewHolder.setData(str);
                }break;
                case ITEM_TYPE_PRODUCT_ITEM:
                {
                    ProductViewHolder productViewHolder = (ProductViewHolder) v.getTag();
                    productViewHolder.setData(str);
                }break;
                case ITEM_TYPE_SHOP_ITEM:
                {
                    ShopViewHolder shopViewHolder = (ShopViewHolder) v.getTag();
                    shopViewHolder.setData(str);
                }


                break;
            }
        }




        return v;
    }

    private class CategoryViewHolder implements View.OnClickListener{

        private TextView nameTextview;
        private ViewGroup viewGroup;
        private View view;
        public CategoryViewHolder(View view) {
            this.view = view;
            nameTextview = (TextView)view.findViewById(R.id.tvName);
            viewGroup = (ViewGroup)view.findViewById(R.id.mainView);
            viewGroup.setOnClickListener(this);
        }

        public void  setData(String data){
            this.nameTextview.setText(data);
        }
        @Override
        public void onClick(View view) {

            Toast.makeText(mContext,"catgory:",Toast.LENGTH_SHORT).show();
            CommonUtils.hideSoftKeyboard(mContext,view);
        }
    }


    private class ShopViewHolder implements View.OnClickListener{

        private TextView nameTextview;
        private ViewGroup viewGroup;
        private View v;
        private int pos;
        public ShopViewHolder(View v,int pos) {
            this.v = v;
            nameTextview = (TextView)v.findViewById(R.id.tvName);
            viewGroup = (ViewGroup)v.findViewById(R.id.mainView);
            viewGroup.setOnClickListener(this);
            this.pos = pos;
        }

        public void  setData(String data){
            this.nameTextview.setText(data);
        }
        @Override
        public void onClick(View view) {
            Toast.makeText(mContext,"shop:",Toast.LENGTH_SHORT).show();
            CommonUtils.hideSoftKeyboard(mContext,v);
            Bundle bundle  = new Bundle();
            bundle.putString("vendorName",shopList.get(pos-(categoryList.size()+productResponseList.size())).getStoreName());
            String id = CommonUtils.getBase64EncodeString(shopList.get(pos-(categoryList.size()+productResponseList.size())).getVendorId());
            bundle.putString("vendorID",id);
            Intent intent = new Intent(mContext,ShopDetailActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

    private class ProductViewHolder implements View.OnClickListener{

        private TextView nameTextview;
        private ViewGroup viewGroup;
        private View v;
        private int position;
        public ProductViewHolder(View v,int position) {
            this.v = v;
            nameTextview = (TextView)v.findViewById(R.id.tvName);
            viewGroup = (ViewGroup)v.findViewById(R.id.mainView);
            viewGroup.setOnClickListener(this);
            this.position = position;
        }

        public void  setData(String data){
            this.nameTextview.setText(data);
        }
        @Override
        public void onClick(View view) {
            Toast.makeText(mContext,"prod:",Toast.LENGTH_SHORT).show();
            CommonUtils.hideSoftKeyboard(mContext,v);
           /* Product product = productResponseList.get(position - categoryList.size());
            ProductDetails details = new ProductDetails(product.getId(),product.getCat1Id(),product.getProductCode(),product.getName(),
                    ""*//*categoryNameMap.get(Integer.parseInt(product.getCat1Id()))*//*,
                    *//*vendorNameMap.get(product.getVendorId())*//*""
                    ,product.getInfo(),product.getTags(),product.getImageUrl(),product.getBasePrice(),product.getDealPrice(),product.getSalePrice(),
                    product.getDiscount(),product.getUnit(),product.getStockQty()
            );
            Bundle pBundle = new Bundle();
            pBundle.putSerializable("productDetails",details);
            Intent pIntent = new Intent(mContext,ProductDetailsActivity.class);
            pIntent.putExtras(pBundle);
            mContext.startActivity(pIntent);*/
        }
    }
    @Override
    public Filter getFilter() {
        if (searchFilter == null) {
            searchFilter = new SearchFilter();
        }

        return searchFilter;
    }


    private class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<String> tempList = new ArrayList<String>();

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


    public  void  updateDataSource(List<Category> categoryList,List<Product> productResponseList,List<Shop> shopList){
        this.productResponseList = productResponseList;
        this.categoryList = categoryList;
        this.shopList = shopList;
        this.searchList = (ArrayList<String>) getRawList();
        this.filterdSearchList = searchList;
        notifyDataSetChanged();
    }


    private List<String> getRawList(){

        List<String> rawList = new ArrayList<>();
        for(Category category:categoryList){
            rawList.add(category.getName());
        }

        for(Product product:productResponseList){
            rawList.add(product.getName());
        }
        for(Shop shop:shopList){
            rawList.add(shop.getStoreName());
        }
        this.searchList  = (ArrayList<String>) rawList;

        return  searchList;
    }
}
