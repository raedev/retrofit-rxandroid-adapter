package retrofit2.adapter.rxjava2;

import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * Created by rae on 2019/1/13.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AndroidCallAdapter <R> implements CallAdapter<R, Object> {

    // Source Adapter
    private CallAdapter<R, ?> mCallAdapter;

    AndroidCallAdapter(CallAdapter<R, ?> callAdapter) {
        mCallAdapter = callAdapter;
    }

    @Override
    public Type responseType() {
        return mCallAdapter.responseType();
    }

    @Override
    public Object adapt(@NonNull Call<R> call) {
        Observable observable = (Observable) mCallAdapter.adapt(call);
        if (observable == null) return null;
        return AndroidObservable.create(observable);
    }
}
