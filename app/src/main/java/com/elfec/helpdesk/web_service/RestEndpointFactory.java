package com.elfec.helpdesk.web_service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.elfec.helpdesk.model.security.HelpdeskApiCredential;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Factory para obtener el Endpoint de los webservices
 * Utilizando la configuración de RestAdapter necesaria
 */
public class RestEndpointFactory {

    /**
     * La URL de los web services de Helpdesk, si fuera necesario conectar a otro webservice
     * se puede pasar otra URL
     */
    public static final String BASE_URL = "http://192.168.50.56/helpdesk/api/";

    /**
     * Crea un endpoint Rest  con la url especificada
     *
     * @param url     especificada
     * @param service servicio especificado
     * @return Endpoint
     */
    public static <T> T create(@NonNull String url, @NonNull Class<T> service) {
        return create(url, service, null);
    }

    /**
     * Crea un endpoint Rest  con la url por defecto {@link RestEndpointFactory#BASE_URL}
     *
     * @return Endpoint
     */
    public static <T> T create(@NonNull Class<T> service) {
        return create(BASE_URL, service);
    }

    /**
     * Crea un endpoint Rest  con la url por defecto {@link RestEndpointFactory#BASE_URL}
     * y con los headers de autenticación con token necesarios
     *
     * @return Endpoint
     */
    public static <T> T create(@NonNull Class<T> service, HelpdeskApiCredential credentials) {
        return create(BASE_URL, service, credentials);
    }

    /**
     * Crea un endpoint Rest con la url especificada
     * y con los headers de autenticación con token necesarios
     *
     * @param url especificada
     * @return Instancia de endpoint
     */
    public static <T> T create(@NonNull String url, @NonNull Class<T> endpoint, @Nullable
    HelpdeskApiCredential credentials) {
        OkHttpClient httpClient = buildClient(credentials);
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()))
                .client(httpClient)
                .build().create(endpoint);
    }

    /**
     * Crea el cliente de http para retrofit
     *
     * @param credentials credentials of API
     * @return {@link OkHttpClient} http client
     */
    @NonNull
    private static OkHttpClient buildClient(@Nullable final HelpdeskApiCredential credentials) {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                if (credentials != null && credentials.getDeviceId() != null && credentials
                        .getToken() != null) {
                    request = request.newBuilder()
                            .header("X-Api-DeviceId", credentials.getDeviceId())
                            .header("X-Api-Token", credentials.getToken())
                            .method(request.method(), request.body())
                            .build();
                }

                return chain.proceed(request);
            }
        });
        return httpClient;
    }
}
