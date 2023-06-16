package hansel.if4a.datapegawai.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hansel.if4a.datapegawai.R;

public class DetailActivity extends AppCompatActivity {
    TextView tvID, tvNama, tvUmur, tvAsal, tvPendidikan, tvJenisKelamin;
    private String yId, yNama, yUmur, yAsal, yPendidikan, yJenisKelamin, yGambar;
    ImageView ivGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xId");
        yNama = ambil.getStringExtra("xNama");
        yUmur = ambil.getStringExtra("xUmur");
        yAsal = ambil.getStringExtra("xAsal");
        yPendidikan = ambil.getStringExtra("xPendidikan");
        yJenisKelamin = ambil.getStringExtra("xJenisKelamin");
        yGambar = ambil.getStringExtra("xGambar");

        tvID = findViewById(R.id.tv_id);
        tvNama = findViewById(R.id.tv_nama);
        tvUmur = findViewById(R.id.tv_umur);
        tvAsal = findViewById(R.id.tv_asal);
        tvPendidikan = findViewById(R.id.tv_pendidikan);
        tvJenisKelamin = findViewById(R.id.tv_jeniskelamin);
        ivGambar = findViewById(R.id.iv_gambar);

        tvNama.setText(yNama);
        tvUmur.setText(yUmur);
        tvAsal.setText(yAsal);
        tvPendidikan.setText(yPendidikan);
        tvJenisKelamin.setText(yJenisKelamin);
        Picasso.get().load(yGambar).into(ivGambar);

    }
}