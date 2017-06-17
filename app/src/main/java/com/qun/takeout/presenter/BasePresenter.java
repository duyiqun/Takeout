package com.qun.takeout.presenter;

import com.qun.takeout.MyApplication;
import com.qun.takeout.model.dao.DBHelper;
import com.qun.takeout.model.net.api.ResponseInfoAPI;
import com.qun.takeout.model.net.bean.ResponseInfo;
import com.qun.takeout.utils.Constant;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 联网工作的管理和数据库管理
 * Created by Qun on 2017/6/17.
 */

public abstract class BasePresenter {

    //联网
    protected Retrofit mRetrofit;
    private final ResponseInfoAPI mResponseInfoAPI;

    //数据库
    protected DBHelper mDBHelper;

    public BasePresenter() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.HOME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mResponseInfoAPI = mRetrofit.create(ResponseInfoAPI.class);

        //获取上下文
        //问题：如果上下文对应的是某个Activity或Fragment
        //此处设置的上下文需要有较长的生命周期
        mDBHelper = DBHelper.getInstance(MyApplication.getInstance());
    }

    protected class CallbackAdapter implements Callback<ResponseInfo> {

        private HashMap<String, String> errorInfo;

        public CallbackAdapter() {
            errorInfo = new HashMap<>();
            errorInfo.put("5", "");
        }

        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            ResponseInfo body = response.body();
            body.getCode();
            body.getData();

            if ("0".equals(body.getCode())) {
                //服务器处理成功，可以解析data数据了
                parseDestInfo(body.getData());
            } else {
                String error = errorInfo.get(body.getCode());
                onFailure(call, new RuntimeException(error));
            }
        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            //我们该如何区分异常，其他类型异常(如：网络有问题)和服务器处理请求失败的异常(如：登录时，输入的用户名密码有误)
            //我们需要创建一个自己的异常类(MyException)，当服务器处理失败时，通过该异常类包装显示数据
            if(t instanceof RuntimeException){
                showError(((RuntimeException)t).getMessage());
            }
            showError("服务器忙，请稍后重试");
        }
    }

    /**
     * 服务器处理失败时需要将错误信息提示给用户(如：用户名密码有误)
     * @param message
     */
    protected abstract void showError(String message);

    /**
     * 解析服务器回复数据
     *
     * @param data
     */
    protected abstract void parseDestInfo(String data);
}
