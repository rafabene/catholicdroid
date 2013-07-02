package com.rafabene.android.lib.tasks;

import java.util.List;

import android.os.AsyncTask;

import com.rafabene.android.lib.domain.TaskCaller;
import com.rafabene.android.lib.domain.TwitterResult;

public class TwitterQueryAsyncTask extends AsyncTask<String, Void, List<TwitterResult>> {

    private TaskCaller<List<TwitterResult>> caller;

    public TwitterQueryAsyncTask(TaskCaller<List<TwitterResult>> caller) {
        this.caller = caller;
    }

    /**
     * @param params [0] = Term to query [1] = Consumer key [2] = Consumer secret
     */
    @Override
    protected List<TwitterResult> doInBackground(String... params) {
       return null;
    }

   




    @Override
    protected void onPostExecute(List<TwitterResult> result) {
        caller.success(result);
    }

}
