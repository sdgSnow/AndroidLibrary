package com.sdg.androidlibrary.update;

import com.dimeno.network.callback.RequestCallback;
import com.dimeno.network.task.PostFormTask;

public class CheckVersionTask extends PostFormTask {

    private String api;

    public <EntityType> CheckVersionTask(RequestCallback<EntityType> callback) {
        super(callback);
    }

    @Override
    public String getApi() {
        return api;
    }

    public CheckVersionTask setApi(String api) {
        this.api = api;
        return this;
    }
}
