package com.example.anik.agent.httpService;

/**
 * Created by Anik on 03-Aug-15, 003.
 */
public interface IHttpResponseListener {
    public void onRemoteCallComplete(int statusCode, String json);
}
