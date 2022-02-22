package com.app.gopalayurvediccenter.Utils;

import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class MyLifecycleObserver implements DefaultLifecycleObserver {
    private static final String TAG = "MyLifecycleObserver";
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<String> mGetContent;

    public void onCreate(@NonNull LifecycleOwner owner) {
        // ...

        mGetContent = mRegistry.register("myKey", owner, new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        Log.e(TAG, "onActivityResult: ");
                    }
                });
    }

    public void selectImage() {
        // Open the activity to select an image
        mGetContent.launch("image/*");
    }


    public MyLifecycleObserver(@NonNull ActivityResultRegistry registry) {
        mRegistry = registry;
    }
}
