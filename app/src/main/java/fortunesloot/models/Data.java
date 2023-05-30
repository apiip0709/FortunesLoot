package fortunesloot.models;

public class Data {
    private String jenis;
    private int jumlah;
    private String tanggal;
    
    public Data(String jenis, int jumlah, String tanggal) {
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getJenis() {
        return jenis;
    }
    
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    
    public int getJumlah() {
        return jumlah;
    }

}
