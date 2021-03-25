package co.com.personacliente2021.service.persona;

import java.util.List;

import co.com.personacliente2021.model.Persona;
import co.com.personacliente2021.service.persona.respuesta.RespuestaPersona;
import co.com.personacliente2021.util.CustomResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

interface PersonaClient {
       @GET("v1/persona")
       Call<List<Persona>> getPersonas();

       @POST("v1/persona")
       Call<CustomResponse<RespuestaPersona>> insertar(@Header("Content-Type") String contentTypeApplicationJson, @Body Persona persona);
 }