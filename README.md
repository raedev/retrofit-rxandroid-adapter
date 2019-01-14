# RxAndroid Adapter

An `Adapter` for adapting ` RxJava 2.x` types.

![version](https://img.shields.io/badge/version-1.0.0-brightgreen.svg)

# Usege

Add `RxAndroidCallAdapterFactory` as a `Call` adapter when building your `Retrofit` instance:
```java
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://example.com/")
    .addCallAdapterFactory(RxAndroidCallAdapterFactory.create())
    .build();
```

Your service methods can now use any of the above types as their return type.
```java
interface MyService {
  @GET("/user")
  AndroidObservable<User> getUser();
}
```

Note: method `.subscribeOn(AndroidSchedulers.mainThread())` is call in AndroidObservable.

> Call In Activity:

```java
public class MyActivity extends Activity {

	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
		MyService service = retrofit.create(MyService.class);
		service.getUser().with(this).subscribe(new YourObserver());
	}
}
```

> Call In Fragment

```java
public class MyFragment extends Fragment {

	@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		MyService service = retrofit.create(MyService.class);
		service.getUser().with(this).subscribe(new YourObserver());
	}
}
```


Download
--------

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

```groovy
dependencies {
	implementation 'com.github.raedev:retrofit-rxandroid-adapter:latest.version'
}
```