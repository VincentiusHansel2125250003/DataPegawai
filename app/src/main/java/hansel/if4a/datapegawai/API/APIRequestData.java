package hansel.if4a.datapegawai.API;

import hansel.if4a.datapegawai.Model.ModelPegawai;
import hansel.if4a.datapegawai.Model.ModelResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrievepegawai.php")
    Call<ModelResponse> ardRetrieve();

    @FormUrlEncoded
    @POST("createpegawai.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("umur") String umur,
            @Field("asal") String asal,
            @Field("pendidikan") String pendidikan,
            @Field("jeniskelamin") String jenisKelamin
    );

    @FormUrlEncoded
    @POST("updatepegawai.php")
    Call<ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("umur") String umur,
            @Field("asal") String asal,
            @Field("pendidikan") String pendidikan,
            @Field("jeniskelamin") String jenisKelamin
    );

    @FormUrlEncoded
    @POST("deletepegawai.php")
    Call<ModelResponse> ardDelete(
            @Field("id") String id
    );
}
