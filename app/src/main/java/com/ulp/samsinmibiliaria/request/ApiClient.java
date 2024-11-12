package com.ulp.samsinmibiliaria.request;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulp.samsinmibiliaria.modelo.Contrato;
import com.ulp.samsinmibiliaria.modelo.Inmueble;
import com.ulp.samsinmibiliaria.modelo.LoginModel;
import com.ulp.samsinmibiliaria.modelo.Pago;
import com.ulp.samsinmibiliaria.modelo.Propietario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    public static final String URLBASE = "http://10.230.157.199:5000/api/";
    private static ApiInmobiliaria apiInmobiliaria;

    public static ApiInmobiliaria getInmobiliaria() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInmobiliaria = retrofit.create(ApiInmobiliaria.class);
        return apiInmobiliaria;
    }


    public interface ApiInmobiliaria {
        @POST("login")
        Call<LoginModel> login(@Body LoginModel loginModel);

        @GET("inmueble")
        Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);

        @GET("propietario")
        Call<Propietario> getPerfil(@Header("Authorization") String token);

        @PUT("propietario/actualizar")
        Call<Propietario> editarPerfil(@Header("Authorization") String token, @Body Propietario propietario);

        @PATCH("inmueble/habilitar/{id}")
        Call<Inmueble> habilitarInmueble(@Header("Authorization") String token, @Path("id") int id);

        @Multipart
        @POST("inmueble/guardar")
        Call<Inmueble> CargarInmueble(@Header("Authorization") String token,
                                      @Part("direccion") RequestBody direccion,
                                      @Part("uso") RequestBody uso,
                                      @Part("tipoInmuebleid") RequestBody tipoInmuebleid,
                                      @Part("cant_ambientes") RequestBody cant_ambientes,
                                      @Part("coordenadas") RequestBody coordenadas,
                                      @Part("precio") RequestBody precio,
                                      @Part("descripcion") RequestBody descripcion,
                                      @Part MultipartBody.Part imagen);

        @GET("contrato/alquilados")
        Call<List<Contrato>> inmueblesAlquilados(@Header("Authorization") String token);

        @GET("pago/{id}")
        Call<List<Pago>> pagosPorContrato(@Header("Authorization") String token, @Path("id") int id);
        @POST("recovery")
        @FormUrlEncoded
        Call<String> enviarMail(@Field("email") String email);
        @PATCH("propietario/actualizar/pass")
        @FormUrlEncoded
        Call<String> cambiarPass(@Header("Authorization")String token, @Field("pass") String pass);
    }
}
