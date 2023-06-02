package fortunesloot.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUser extends Data {

    public DataUser(String jenis, int jumlah, String tanggal) {
        super(jenis, jumlah, tanggal);
    }

    @Override
    public String tanggalWaktuNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}

