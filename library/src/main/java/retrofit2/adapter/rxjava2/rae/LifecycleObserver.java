package retrofit2.adapter.rxjava2.rae;

import android.support.annotation.NonNull;

public interface LifecycleObserver {

    @NonNull
    LifecycleProvider getLifecycleProvider();
}
