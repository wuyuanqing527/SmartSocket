package com.wuyuanqing.smartsocket.util;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.Set;

/**
 * Created by wyq on 2015/10/9.
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery ;
    private Gson gson;

    private OkHttpClientManager(){
        mOkHttpClient=new OkHttpClient();
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        mDelivery=new Handler(Looper.getMainLooper());
        gson=new Gson();
    }

    public static OkHttpClientManager getInstance(){
        if(mInstance==null){
            synchronized (OkHttpClientManager.class){
                if(mInstance==null){
                    mInstance=new OkHttpClientManager();
                }

            }
        }
        return  mInstance;
    }

    /**
     *同步的GET请求
     * @param url
     * @return response
     */
    private Response _getSync(String url){
        Request request=new Request.Builder().url(url).build();
        Call call=mOkHttpClient.newCall(request);
        Response response= null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 同步的GET请求
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url){
        Response response=_getSync(url);
        return response.body().toString();
    }

    /**
     * 异步的GET请求
     * @param url
     * @param callback
     */
    private void _getAsyn(String url,ResultCallback callback){

        Request request=new Request.Builder().url(url).build();
        deliveryResult(callback,request);
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException
    {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }




    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException
    {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Param... params)
    {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * 异步的put请求
     * @param url
     * @param callback
     * @param params
     */
    private void _putAsyn(String url,final ResultCallback callback,Param...params){
        Request request=buildPutRequest(url, params);
        deliveryResult(callback,request);
    }

    private Request buildPostRequest(String url, Param[] params) {
        if(params == null){
            params=new Param[0];
        }
//        FormEncodingBuilder builder=new FormEncodingBuilder();
//        for(Param param : params){
//            builder.add(param.key,param.value);
//        }
//        RequestBody requestBody=builder.build();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody=RequestBody.create(JSON, params[0].value);
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private Request buildPutRequest(String url, Param[] params) {
        if(params == null){
            params=new Param[0];
        }
//        FormEncodingBuilder builder=new FormEncodingBuilder();
//        for(Param param : params){
//            builder.add(param.key,param.value);
//        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody=RequestBody.create(JSON,params[0].value);
        return new Request.Builder().url(url).put(requestBody).build();
    }

    private Param[] map2Params(Map<String, String> params) {
        if(params ==null){
            return  new Param[0];
        }

        int size=params.size();
        Param[] res=new Param[size];
        Set<Map.Entry<String,String>> entries=params.entrySet();
        int i=0;
        for(Map.Entry<String,String> entry : entries){
            res[i++]=new Param(entry.getKey(),entry.getValue());
        }
         return res;
    }

    private void deliveryResult(final ResultCallback callback,Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailedStringCallback(request,e,callback);
            }

            @Override
            public void onResponse(Response response) throws IOException {

                try{
                    String string=response.body().string();
                    if(callback.mType ==String.class){
                        sendSuccessResultCallback(string,callback);
                    }else{
                        Object o=gson.fromJson(string,callback.mType);
                        sendSuccessResultCallback(o,callback);
                    }
                }catch (IOException e){
                    sendFailedStringCallback(response.request(),e,callback);
                }catch (JsonParseException e){
                    sendFailedStringCallback(response.request(),e,callback);
                }

            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(request, e);
                }
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null){
                    callback.onResponse(object);
                }
            }
        });
    }

    public static abstract  class ResultCallback<T>{

        Type mType;

        public ResultCallback()
        {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass)
        {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    public static class Param{
        String key;
        String value;
        public Param(){

        }

        public Param(String key,String value){
            this.key=key;
            this.value=value;
        }

    }

    //*******************对外公布的方法*************
    public static Response getsync(String url){
        return  getInstance()._getSync(url);
    }

    public static String getAsString(String url){
        return getInstance()._getAsString(url);
    }


    public static void getAsyn(String url, ResultCallback callback)
    {
        getInstance()._getAsyn(url, callback);
    }

    public static Response post(String url, Param... params) throws IOException {
        return getInstance()._post(url, params);
    }

    public static String postAsString(String url, Param... params) throws IOException
    {
        return getInstance()._postAsString(url, params);
    }

    public static void postAsyn(String url, final ResultCallback callback, Param... params)
    {
        getInstance()._postAsyn(url, callback, params);
    }


    public static void postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        getInstance()._postAsyn(url, callback, params);
    }

    public static void putAsyn(String url, final ResultCallback callback, Param... params)
    {
        getInstance()._putAsyn(url, callback, params);
    }
}


