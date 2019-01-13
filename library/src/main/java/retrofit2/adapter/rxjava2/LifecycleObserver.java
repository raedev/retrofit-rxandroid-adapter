package retrofit2.adapter.rxjava2;

import android.support.annotation.NonNull;

public interface LifecycleObserver {

    @NonNull
    LifecycleProvider getLifecycleProvider();
}
