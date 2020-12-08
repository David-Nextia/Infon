package com.nextia.data;
/**
 * class to create the retrofit service that will manage the server calls
 */


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.nextia.domain.Repository;
import com.nextia.domain.models.Seguridad;
import com.nextia.domain.models.StatusServicio;
import com.nextia.domain.models.user.Credito;
import com.nextia.domain.models.user.UserResponse;

import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String URL_BASE="https://serviciosweb.infonavit.org.mx:8892";
    private static final String URL_BASE_PROD="https://serviciosweb.infonavit.org.mx:9051";
    private static final String URL_BASE_LOGIN="https://serviciosweb.infonavit.org.mx:8893";
    private static final String URL_BASE_LOGIN_PROD="https://serviciosweb.infonavit.org.mx:8893";
    static Gson gson = new GsonBuilder().registerTypeAdapter(UserResponse.class, new UserResponseDeserealizer()).create();

    //To create the repository with the apiService
    public static Repository getApiService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient().addInterceptor(logging).build())
                .build();
        return retrofit.create(Repository.class);
    }

    //to get login service
    public static Repository getApiLoginService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE_LOGIN)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient().addInterceptor(logging).build())
                .build();
        return retrofit.create(Repository.class);
    }
    //apiprod
    public static Repository getApiProdService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient().addInterceptor(logging).build())
                .build();
        return retrofit.create(Repository.class);
    }

    //Class to deserealize UserResponse and avoid errors on different responses
    public static  class UserResponseDeserealizer implements JsonDeserializer<UserResponse> {

        @Override
        public UserResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement Jnss = json.getAsJsonObject().get("nss");
            String nss=gson.fromJson(Jnss,String.class);
            JsonElement Jnombre = json.getAsJsonObject().get("nombre");
            String nombre=gson.fromJson(Jnombre,String.class);
            JsonElement JapPaterno = json.getAsJsonObject().get("apPaterno");
            String apPaterno=gson.fromJson(JapPaterno,String.class);
            JsonElement JapMaterno = json.getAsJsonObject().get("apMaterno");
            String apMaterno=gson.fromJson(JapMaterno,String.class);
            JsonElement Jrfc = json.getAsJsonObject().get("rfc");
            String rfc=gson.fromJson(Jrfc,String.class);
            JsonElement Jscurp = json.getAsJsonObject().get("scurp");
            String scurp=gson.fromJson(Jscurp,String.class);
            JsonElement JtelefonoCelular= json.getAsJsonObject().get("telefonoCelular");
            String telefonoCelular=gson.fromJson(JtelefonoCelular,String.class);
            JsonElement JemailPersonal = json.getAsJsonObject().get("emailPersonal");
            String emailPersonal=gson.fromJson(JemailPersonal,String.class);
            JsonElement JidPerfilglobal = json.getAsJsonObject().get("idPerfilglobal");
            String idPerfilglobal=gson.fromJson(JidPerfilglobal,String.class);

            JsonElement Jbiometrico = json.getAsJsonObject().get("biometrico");
            Boolean biometrico=gson.fromJson(Jbiometrico,Boolean.class);
            JsonElement Jnotificacion = json.getAsJsonObject().get("notificacion");
            Boolean notificacion=gson.fromJson(Jnotificacion,Boolean.class);
            JsonElement Jseguridad = json.getAsJsonObject().get("Seguridad");
            Seguridad seguridad=gson.fromJson(Jseguridad,Seguridad.class);
            JsonElement JstatusServicio = json.getAsJsonObject().get("StatusServicio");
            StatusServicio statusServicio =gson.fromJson(JstatusServicio,StatusServicio.class);
            JsonElement Jcredito = json.getAsJsonObject().get("Credito");
            if(Jcredito.isJsonArray()){
                ArrayList<Credito> credits= gson.fromJson(Jcredito,new TypeToken<ArrayList<Credito>>(){}.getType());
                return new UserResponse(nss,nombre,apPaterno,apMaterno,rfc,scurp,telefonoCelular,emailPersonal,idPerfilglobal,biometrico,notificacion,credits,statusServicio,seguridad);

            }
            else if(Jcredito.isJsonObject()){
                ArrayList<Credito> credits=new ArrayList<>();
                credits.add(gson.fromJson(Jcredito,Credito.class));
                return new UserResponse(nss,nombre,apPaterno,apMaterno,rfc,scurp,telefonoCelular,emailPersonal,idPerfilglobal,biometrico,notificacion,credits,statusServicio,seguridad);
            }
            else{
                throw new JsonParseException("Error en la base de datos");
            }

        }
    }

    //Client builder to read the sent and received body.
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException { }
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException { }
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};}}};
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
