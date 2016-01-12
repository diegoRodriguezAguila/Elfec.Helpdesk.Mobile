package com.elfec.helpdesk.web_service;

import com.elfec.helpdesk.model.ApiError;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Creates ApiError from responses
 */
public class ApiErrorFactory {

    private static final ApiError DEFAULT_ERROR =
            new ApiError("Error no definido", -1);

    /**
     * Construye un error a partir de un Response de retrofit
     * @param response response de retrofit
     * @return {@link ApiError} apiError
     */
    public static ApiError build(Response<?> response){
        return build(response.errorBody());
    }

    /**
     * Construye un error a partir de un errorBody de un Response
     * @param errorBody error body
     * @return {@link ApiError} apiError
     */
    public static ApiError build(ResponseBody errorBody){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        ApiError error = null;
        try {
            error = gson.fromJson(errorBody.string(),
                    ApiError.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error==null?DEFAULT_ERROR: error;
    }
}
