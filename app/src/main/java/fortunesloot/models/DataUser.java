package fortunesloot.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataUser extends AbsData {

    public DataUser(){}
    
    public DataUser(String jenis, int jumlah, String tanggal) {
        super(jenis, jumlah, tanggal);
    }

    @Override
    public String tanggalWaktuNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    // method untuk sinkron keseluruhan total untuk tampilan pada dompet
    public int hitungTotalPenghasilan(List<DataUser> dataPenghasilan) {
        int totalPenghasilan = 0;
        for (DataUser data : dataPenghasilan) {
            totalPenghasilan += data.getJumlah();
        }
        return totalPenghasilan;
    }
    
    public int hitungTotalPengeluaran(List<DataUser> dataPengeluaran) {
        int totalPengeluaran = 0;
        for (DataUser data : dataPengeluaran) {
            totalPengeluaran += data.getJumlah();
        }
        return totalPengeluaran;
    }
}

