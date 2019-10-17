package com.sb.sweetbucket.rest;

import com.sb.sweetbucket.rest.request.AddCartRequest;
import com.sb.sweetbucket.rest.request.AddressSaveRequest;
import com.sb.sweetbucket.rest.request.CheckPinRequest;
import com.sb.sweetbucket.rest.request.DelAddressRequest;
import com.sb.sweetbucket.rest.request.HomeRequest;
import com.sb.sweetbucket.rest.request.LoginRequest;
import com.sb.sweetbucket.rest.request.OrderDetailRequest;
import com.sb.sweetbucket.rest.request.PlaceOrderRequest;
import com.sb.sweetbucket.rest.request.ProductReviewRequest;
import com.sb.sweetbucket.rest.request.RegisterRequest;
import com.sb.sweetbucket.rest.response.AddCartResponse;
import com.sb.sweetbucket.rest.response.Address;
import com.sb.sweetbucket.rest.response.CartDetailsResponse;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.ConfirmOrderResponse;
import com.sb.sweetbucket.rest.response.DelAddressResponse;
import com.sb.sweetbucket.rest.response.HomeResponse;
import com.sb.sweetbucket.rest.response.LoginResponse;
import com.sb.sweetbucket.rest.response.OrderDetailResponse;
import com.sb.sweetbucket.rest.response.OrdersResponse;
import com.sb.sweetbucket.rest.response.PaymentModeResponse;
import com.sb.sweetbucket.rest.response.PinCodeResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.ProductReviewResponse;
import com.sb.sweetbucket.rest.response.RegisterResponse;
import com.sb.sweetbucket.rest.response.SaveAddressResponse;
import com.sb.sweetbucket.rest.response.Shop;
import com.sb.sweetbucket.rest.response.ShopsResponse;
import com.sb.sweetbucket.rest.response.TransactionResponse;
import com.sb.sweetbucket.rest.response.VendorResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by harmeet on 19-08-2019.
 */

public interface RestAPIInterface {

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/apiLogin")
    Call<LoginResponse> doLogin(@Body LoginRequest loginRequest);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/apiRegister")
    Call<RegisterResponse> doRegister(@Body RegisterRequest registerRequest);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/sugestion")
    Call<HomeResponse> getSuggestion(@Body HomeRequest homeRequest);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/category")
    Call<List<Category>> getCategory();

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/shops")
    Call<List<ShopsResponse>> getShops();


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/shops/{vendor_id}")
    Call<VendorResponse> getProductsByShopID(@Path(value = "vendor_id",encoded = true) String vendorID);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/sweets/{category}")
    Call<List<Product>> getSweetsByCategory(@Path("category") String category);

    @GET("api/trandingProducts")
    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    Call<List<Product>> getTredingProducts();

    @GET("api/trandingShops")
    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    Call<List<ShopsResponse>> getTredingShops();

    @GET("api/similarproducts/{cat-id}")
    Call<List<Product>> getSimilarProductsByCategory(@Path("cat-id") String category);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/checkMyPin")
    Call<PinCodeResponse> checkPin(@Body CheckPinRequest checkPinRequest);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/product/{id}")
    Call<Product> getProductByID(@Path(value = "id",encoded = true) String productID);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/productReview")
    Call<ProductReviewResponse> postProductReviews(@Body ProductReviewRequest request,
                                                   @Header("Authorization") String authorization);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/addToCart")
    Call<AddCartResponse> addToCart(@Body AddCartRequest request,
                                    @Header("Authorization") String authorization);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/getCart")
    Call<CartDetailsResponse> getCartDetails(@Header("Authorization") String authorization);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/getAddress")
    Call<List<Address>> getAddressList(@Header("Authorization") String authorization);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/setAddress")
    Call<SaveAddressResponse> postSaveAddress(@Body AddressSaveRequest addressSaveRequest,
                                              @Header("Authorization") String authorization);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/delAddress")
    Call<DelAddressResponse> delAddress(@Body DelAddressRequest delAddressRequest,
                                        @Header("Authorization") String authorization);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/orderDetails")
    Call<OrderDetailResponse> getOrderDetails(@Body OrderDetailRequest orderDetailRequest,
                                              @Header("Authorization") String authorization);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/orderList")
    Call<OrdersResponse> getOrders(@Header("Authorization") String authorization);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/paymentList")
    Call<TransactionResponse> getTransactionDetails(@Header("Authorization") String authorization);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/placeOrder")
    Call<ConfirmOrderResponse> postOrder(@Body PlaceOrderRequest placeOrderRequest,@Header("Authorization") String authorization);

    @GET("api/paymentMode")
    Call<List<PaymentModeResponse>> getPaymentModes(@Header("Authorization") String authorization);
}