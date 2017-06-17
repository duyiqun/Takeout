package com.qun.takeout.model.net.api;

import com.qun.takeout.model.net.bean.ResponseInfo;
import com.qun.takeout.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Qun on 2017/6/17.
 */

public interface ResponseInfoAPI {

    @GET(Constant.LOGIN)
    Call<ResponseInfo> login(@Query("username") String username, @Query("password") String password);
}
