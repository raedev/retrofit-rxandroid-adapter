/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2.adapter.rxjava2;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

@RunWith(AndroidJUnit4.class)
public final class AndroidObservableTest {

    interface Service {
        @GET("app/config")
        Observable<Void> token();
    }

    private Service service;

    @Before
    public void setUp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ant.raedev.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxAndroidCallAdapterFactory.create())
                .build();
        service = retrofit.create(Service.class);
    }

    @Test
    public void testToken() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        service.token()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onComplete() {
                        countDownLatch.countDown();
                    }
                });
        countDownLatch.await();
        Log.i("rae", "test finish!");
    }
}
