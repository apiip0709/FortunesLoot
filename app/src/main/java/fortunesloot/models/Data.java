package fortunesloot.models;

public abstract class Data {
    private String jenis;
    private int jumlah;
    private String tanggal;
    
    public abstract String tanggalWaktuNow();

    public Data() {}
    
    public Data(String jenis, int jumlah, String tanggal) {
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
