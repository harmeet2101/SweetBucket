package com.sb.sweetbucket.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.fragments.AboutUsFragment;
import com.sb.sweetbucket.fragments.AddressFragment;
import com.sb.sweetbucket.fragments.BrandsFragment;
import com.sb.sweetbucket.fragments.ContactUsFragment;
import com.sb.sweetbucket.fragments.HomeFragment;
import com.sb.sweetbucket.fragments.OrderFragment;
import com.sb.sweetbucket.fragments.PaymentFragment;
import com.sb.sweetbucket.fragments.PopularProductsFragment;
import com.sb.sweetbucket.fragments.ShopFragment;
import com.sb.sweetbucket.fragments.SweetCategoryFragment;
import com.sb.sweetbucket.fragments.SweetsFragment;
import com.sb.sweetbucket.fragments.TestFragment;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.CartDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 15-08-2019.
 */

public class DashboardActivity extends AppCompatActivity implements SweetsFragment.ISweetFragmentListener,View.OnClickListener{

    private final String TAG = DashboardActivity.class.getSimpleName();
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_SWEETS = "sweets";
    private static final String TAG_SHOPS = "shops";
    private static final String TAG_BRANDS = "brands";
    private static final String TAG_POPULAR = "popular";
    private static final String TAG_CONTACT_US = "contact_us";
    private static final String TAG_ABOUT_US = "about_us";
    private static final String TAG_ORDERS ="my orders";
    private static final String TAG_PAYMENT ="my payment";
    private static final String TAG_ADDRESS ="my address";
    public static String CURRENT_TAG = TAG_HOME;
    private static final String TAG_ALL_PRODUCTS = "all_products";
    private TextView toolbarTitleTextview,cartCountTextview;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    private ImageView cartImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitleTextview = (TextView)findViewById(R.id.custom_toolbar_title);
        cartCountTextview = (TextView)findViewById(R.id.cartCountTextview);
        cartImageView = (ImageView)findViewById(R.id.cartImgView);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
// load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // initializing navigation menu
        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        cartImageView.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // update the count...
        getCartDetails();

    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
        toolbarTitleTextview.setText(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                BrandsFragment brandsFragment = new BrandsFragment();
                return brandsFragment;
             //   HomeFragment homeFragment = new HomeFragment();
             //   return homeFragment;
            case 1:
                // sweets
                SweetsFragment sweetsFragment = new SweetsFragment();
                return sweetsFragment;
            case 2:
                // shop fragment
                ShopFragment shopFragment = new ShopFragment();
                return shopFragment;
            case 3:
                // brands fragment
                /*BrandsFragment brandsFragment = new BrandsFragment();
                return brandsFragment;*/
               TestFragment homeFragment1 = new TestFragment();
                return homeFragment1;

            case 4:
                // Popular fragment
                PopularProductsFragment popularProductsFragment = new PopularProductsFragment();
                return popularProductsFragment;

            case 5:
                OrderFragment orderFragment = new OrderFragment();
                return orderFragment;

            case 6:
                PaymentFragment paymentFragment = new PaymentFragment();
                return paymentFragment;
            case 7:

                AddressFragment addressFragment = new AddressFragment();
                return addressFragment;
            case 8:
                // contactUsFragment fragment
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                return contactUsFragment;

            case 9:
                // aboutUsFragment fragment
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                return aboutUsFragment;

            case 10:
                // all products fragment
                HomeFragment allProducts = new HomeFragment();
                return allProducts;
            default:
                return new TestFragment();
        }
    }
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        if(navItemIndex!=10)
        selectNavMenu();
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_sweets:
                        navItemIndex = 1;
                       CURRENT_TAG = TAG_SWEETS;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_shops:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SHOPS;

                        drawer.closeDrawers();
                        break;
                    case R.id.nav_brands:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_BRANDS;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_popular:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_POPULAR;
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_order:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ORDERS;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_payment:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_PAYMENT;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_address:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_ADDRESS;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_contact_us:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_CONTACT_US;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_about_us:
                        navItemIndex = 9;
                        // launch new intent instead of loading fragment
                       // startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        CURRENT_TAG = TAG_ABOUT_US;
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_logout:
                        drawer.closeDrawers();
                       showLogoutAlertDialog();
                        return false;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        /*if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }*/
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }if(getFragmentManager().getBackStackEntryCount()==0){

            showExitAlertDialog();
        }
       else super.onBackPressed();
    }

    private void showLogoutAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.app_close_dialog_message).setTitle(R.string.app_name);

        //Setting message manually and performing action on button click
                builder.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferncesController controller = SharedPreferncesController.getSharedPrefController(getApplicationContext());
                        controller.setIsUserLoggedIn(false);
                        controller.saveAPIToken(null);
                        dialog.cancel();
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showExitAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.app_exit_dialog_message).setTitle(R.string.app_name);

        //Setting message manually and performing action on button click
        builder.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    //TODO add sweetCategory frag/activity here 25-8-19
    @Override
    public void onSweetsItemSelected(String category) {

        Bundle bundle = new Bundle();
        bundle.putString("category",category);
        Intent intent = new Intent(getApplicationContext(),SweetsCategoryActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
/*        Fragment fragment = SweetCategoryFragment.getInstance(category);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();*/
    }

    public void switchToShopFragment(){
        navItemIndex = 2;
        CURRENT_TAG = TAG_SHOPS;
        loadHomeFragment();
    }

    public void switchToHomeFragment(){
        navItemIndex = 10;
        CURRENT_TAG = TAG_ALL_PRODUCTS;
        loadHomeFragment();
    }


    private void getCartDetails(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()) {
            Call<CartDetailsResponse> responseCall = apiInterface.getCartDetails("Bearer " + apiToken);
            responseCall.enqueue(new Callback<CartDetailsResponse>() {

                                     @Override
                                     public void onResponse(Call<CartDetailsResponse> call, Response<CartDetailsResponse> response) {
                                         //Toast.makeText(getApplicationContext(),response.code()+": response code",Toast.LENGTH_SHORT).show();
                                         if (response.body()!=null){
                                             Log.e(TAG, response.body().toString());
                                             HomeDataStore homeDataStore = HomeDataStore.getInstance();
                                             homeDataStore.setCartDetailsResponse(response.body());
                                             cartCountTextview.setText(response.body().getCartList().size()+"");
                                         }
                                         //  Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                                     }

                                     @Override
                                     public void onFailure(Call<CartDetailsResponse> call, Throwable t) {
                                         Toast.makeText(getApplicationContext(), "Some Error occured", Toast.LENGTH_SHORT).show();

                                     }

                                 }
            );
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){

            case R.id.cartImgView:


                if (HomeDataStore.getInstance().getCartDetailsResponse()!=null &&
                        HomeDataStore.getInstance().getCartDetailsResponse().getCartList().size()>0
                        ){
                    Intent intent = new Intent(getApplicationContext(),CartDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cartDetails",HomeDataStore.getInstance().getCartDetailsResponse());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(),"Cart is Empty",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
