package hansel.if4a.datapegawai.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelPegawai> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelPegawai> getData() {
        return data;
    }
}
