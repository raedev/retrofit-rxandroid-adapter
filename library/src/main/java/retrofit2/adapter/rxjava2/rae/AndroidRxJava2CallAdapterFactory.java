package retrofit2.adapter.rxjava2.rae;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public final class AndroidRxJava2CallAdapterFactory extends CallAdapter.Factory {

    public static AndroidRxJava2CallAdapterFactory create() {
        return new AndroidRxJava2CallAdapterFactory();
    }

    private final RxJava2CallAdapterFactory mSourceFactory;

    private AndroidRxJava2CallAdapterFactory() {
        mSourceFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        CallAdapter<?, ?> adapter = mSourceFactory.get(returnType, annotations, retrofit);
        return new AntSdkCallAdapter<>(adapter);
    }


    private class AntSdkCallAdapter<R> implements CallAdapter<R, Object> {

        @Nullable
        private CallAdapter<R, ?> mSourceAdapter;

        private AntSdkCallAdapter(CallAdapter<R, ?> adapter) {
            mSourceAdapter = adapter;
        }

        @Override
        public Type responseType() {
            return mSourceAdapter.responseType();
        }

        @Override
        public Object adapt(@NonNull Call<R> call) {
            Observable<?> observable = (Observable<?>) mSourceAdapter.adapt(call);
            return AndroidObservable.create(observable);
        }
    }
}
