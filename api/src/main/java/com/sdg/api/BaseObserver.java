package com.sdg.api;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sdg.api.loading.ApiLoading;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 规则接口格式{
 *     "flag":
 *     "msg":
 *     "ResultObj":{}
 * }
 */
public abstract class BaseObserver<T> implements Observer<Res<T>> {
    /**
     * hideLoading 是否隱藏等待框
     * 默認為true
     */
    private boolean hideLoading=true;

    private Context context;

    public BaseObserver(Context context,boolean hideLoading){
        this.context=context;
        this.hideLoading=hideLoading;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        ApiLoading.getInstance().show(context,false);
        onPrepare(d);
    }

    @Override
    public void onNext(@NonNull Res<T> tRes) {
        ApiLoading.getInstance().cancel();
        onSuccess(tRes);
        if(tRes != null) {
            if(tRes.code == 200 && tRes.ResultObj != null) {
                onSuccess(tRes);
            }else {
                onFaild(tRes.Msg);
            }
        }else {
            onFaild("数据返回异常");
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        if(e instanceof HttpException){
            int code = ((HttpException) e).code();
            onFaild(e.getMessage());
            if(code == 404){
                Toast.makeText(context,"抱歉~你要查看的页面不存在",Toast.LENGTH_SHORT).show();
            }else if (code == 500){
                Toast.makeText(context,"连接异常，请稍后重试或联系管理员处理",Toast.LENGTH_SHORT).show();
            }else if (code == 502){
                Toast.makeText(context,"网络连接不可用，请稍后重试",Toast.LENGTH_SHORT).show();
            }else if (code == 504){
                Toast.makeText(context,"网络连接不可用，请稍后重试",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        if(hideLoading) {
            //TODO 停止接口的加载时动画
            ApiLoading.getInstance().cancel();
        }
    }

    @Override
    public void onComplete() {
        if(hideLoading){
            //TODO 停止接口的加载时动画
            ApiLoading.getInstance().cancel();
        }
    }

    protected abstract void onPrepare(Disposable d);

    protected abstract void onSuccess(Res<T> res);

    protected abstract void onFaild(String error);

}
