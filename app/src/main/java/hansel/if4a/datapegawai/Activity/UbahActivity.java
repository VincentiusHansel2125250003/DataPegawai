package hansel.if4a.datapegawai.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hansel.if4a.datapegawai.API.APIRequestData;
import hansel.if4a.datapegawai.API.RetroServer;
import hansel.if4a.datapegawai.Model.ModelResponse;
import hansel.if4a.datapegawai.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private String yId, yNama, yUmur, yAsal, yPendidikan, yJenisKelamin, yGambar;
    private EditText etNama, etUmur, etAsal, etPendidikan, etJenisKelamin, etGambar;
    private Button btnUbah;
    private String nama, umur, asal, pendidikan, jenisKelamin, gambarpegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xId");
        yNama = ambil.getStringExtra("xNama");
        yUmur = ambil.getStringExtra("xUmur");
        yAsal = ambil.getStringExtra("xAsal");
        yPendidikan = ambil.getStringExtra("xPendidikan");
        yJenisKelamin = ambil.getStringExtra("xJenisKelamin");
        yGambar = ambil.getStringExtra("xGambar");
        
        etNama = findViewById(R.id.et_nama);
        etUmur = findViewById(R.id.et_umur);
        etAsal = findViewById(R.id.et_asal);
        etPendidikan = findViewById(R.id.et_pendidikan);
        etJenisKelamin =  findViewById(R.id.et_jenisKelamin);
        etGambar = findViewById(R.id.et_gambar);
        btnUbah = findViewById(R.id.btn_ubah);
        
        etNama.setText(yNama);
        etUmur.setText(yUmur);
        etAsal.setText(yAsal);
        etPendidikan.setText(yPendidikan);
        etJenisKelamin.setText(yJenisKelamin);
        etGambar.setText(yGambar);
        
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                umur = etUmur.getText().toString();
                asal = etAsal.getText().toString();
                pendidikan = etPendidikan.getText().toString();
                jenisKelamin = etJenisKelamin.getText().toString();
                gambarpegawai = etGambar.getText().toString();

                if (nama.trim().isEmpty())
                {
                    etNama.setError("Nama Tidak Boleh Kosong");
                } else if (umur.trim().isEmpty()) {
                    etUmur.setError("Umur Tidak Boleh Kosong");
                } else if (asal.trim().isEmpty()) {
                    etAsal.setError("Asal Tidak Boleh Kosong");
                } else if (pendidikan.trim().isEmpty()) {
                    etPendidikan.setError("Pendidikan Tidak Boleh Kosong");
                } else if (jenisKelamin.trim().isEmpty()) {
                    etJenisKelamin.setError("Jenis Kelamin Tidak Boleh Kosong");
                }
                else {
                    ubahPegawai();
                }
            }
        });
    }
    
    private void ubahPegawai(){
        APIRequestData ard = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ard.ardUpdate(yId, nama, umur, asal, pendidikan, jenisKelamin, gambarpegawai);
        
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode: " + kode + ", Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}