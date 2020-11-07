package com.example.nb_interview.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.nb_interview.database.RoomDatabase;

public class RemoveItemWorker extends Worker {
    private final String TAG = "RemoveItemWorker";
    private RoomDatabase roomDatabase;

    public RemoveItemWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        roomDatabase = RoomDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "doWork: " );

        //Remove last item in the local database
        roomDatabase.productDao().deleteLastItem();

        return Result.success();
    }

}
