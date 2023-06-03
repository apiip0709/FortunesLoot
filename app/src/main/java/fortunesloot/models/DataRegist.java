package fortunesloot.models;

public class DataRegist {
    private String namaLengkap;
    private int umur;
    private String username;
    private String password;

    public DataRegist(String namaLengkap, int umur, String username, String password) {
        this.namaLengkap = namaLengkap;
        this.umur = umur;
        this.username = username;
        this.password = password;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
