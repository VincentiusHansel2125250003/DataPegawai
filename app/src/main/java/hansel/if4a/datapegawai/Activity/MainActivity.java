package hansel.if4a.datapegawai.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import hansel.if4a.datapegawai.API.APIRequestData;
import hansel.if4a.datapegawai.API.RetroServer;
import hansel.if4a.datapegawai.Adapter.AdapterPegawai;
import hansel.if4a.datapegawai.Model.ModelPegawai;
import hansel.if4a.datapegawai.Model.ModelResponse;
import hansel.if4a.datapegawai.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvPegawai;
    private FloatingActionButton fabTambah;
    private ProgressBar pbPegawai;
    private RecyclerView.Adapter adPegawai;
    private RecyclerView.LayoutManager lmPegawai;
    private List<ModelPegawai> listPegawai = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPegawai = findViewById(R.id.rv_pegawai);
        fabTambah = findViewById(R.id.fab_tambah);
        pbPegawai = findViewById(R.id.pb_pegawai);

        lmPegawai = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPegawai.setLayoutManager(lmPegawai);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrievePegawai();
    }

    public void retrievePegawai(){
        pbPegawai.setVisibility(View.VISIBLE);
        APIRequestData ard = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ard.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listPegawai = response.body().getData();

                adPegawai = new AdapterPegawai(MainActivity.this, listPegawai);
                rvPegawai.setAdapter(adPegawai);
                adPegawai.notifyDataSetChanged();

                pbPegawai.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbPegawai.setVisibility(View.GONE);
            }
        });
    }
}