package hansel.if4a.datapegawai.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hansel.if4a.datapegawai.API.APIRequestData;
import hansel.if4a.datapegawai.API.RetroServer;
import hansel.if4a.datapegawai.Activity.DetailActivity;
import hansel.if4a.datapegawai.Activity.MainActivity;
import hansel.if4a.datapegawai.Activity.UbahActivity;
import hansel.if4a.datapegawai.Model.ModelPegawai;
import hansel.if4a.datapegawai.Model.ModelResponse;
import hansel.if4a.datapegawai.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPegawai extends RecyclerView.Adapter<AdapterPegawai.VHPegawai> {
    private Context ctx;
    private List<ModelPegawai> listPegawai;

    public AdapterPegawai(Context ctx, List<ModelPegawai> listPegawai) {
        this.ctx = ctx;
        this.listPegawai = listPegawai;
    }

    @NonNull
    @Override
    public VHPegawai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pegawai, parent, false);
        return new VHPegawai(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHPegawai holder, int position) {
        ModelPegawai MP = listPegawai.get(position);
        holder.tvID.setText(MP.getId());
        holder.tvNama.setText(MP.getNama());
        holder.tvUmur.setText(MP.getUmur());
        holder.tvAsal.setText(MP.getAsal());
        holder.tvPendidikan.setText(MP.getPendidikan());
        holder.tvJenisKelamin.setText(MP.getJeniskelamin());
        Picasso.get().load(MP.getGambarpegawai()).into(holder.ivGambar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(ctx, DetailActivity.class);
                pindah.putExtra("xId", MP.getId());
                pindah.putExtra("xNama", MP.getNama());
                pindah.putExtra("xUmur", MP.getUmur());
                pindah.putExtra("xAsal", MP.getAsal());
                pindah.putExtra("xPendidikan", MP.getPendidikan());
                pindah.putExtra("xJenisKelamin", MP.getJeniskelamin());
                pindah.putExtra("xGambar", MP.getGambarpegawai());
                ctx.startActivity(pindah);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                pesan.setTitle("Perhatian");
                pesan.setMessage("Operasi apa yang akan lakukan?");
                pesan.setCancelable(true);

                pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.hapusPegawai(MP.getId());
                        dialog.dismiss();
                    }
                });

                pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent pindah = new Intent(ctx, UbahActivity.class);
                        pindah.putExtra("xId", MP.getId());
                        pindah.putExtra("xNama", MP.getNama());
                        pindah.putExtra("xUmur", MP.getUmur());
                        pindah.putExtra("xAsal", MP.getAsal());
                        pindah.putExtra("xPendidikan", MP.getPendidikan());
                        pindah.putExtra("xJenisKelamin", MP.getJeniskelamin());
                        pindah.putExtra("xGambar", MP.getGambarpegawai());
                        ctx.startActivity(pindah);
                    }
                });

                pesan.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPegawai.size();
    }

    public class VHPegawai extends RecyclerView.ViewHolder {
        TextView tvID, tvNama, tvUmur, tvAsal, tvPendidikan, tvJenisKelamin;
        ImageView ivGambar;
        public VHPegawai(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvUmur = itemView.findViewById(R.id.tv_umur);
            tvAsal = itemView.findViewById(R.id.tv_asal);
            tvPendidikan = itemView.findViewById(R.id.tv_pendidikan);
            tvJenisKelamin = itemView.findViewById(R.id.tv_jeniskelamin);
            ivGambar = itemView.findViewById(R.id.iv_gambar);
        }

        private void hapusPegawai(String idPegawai) {
            APIRequestData ard = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = ard.ardDelete(idPegawai);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode: " + kode + ", Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrievePegawai();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
