package ta.lutfi.qrcode;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MuseumActivity extends AppCompatActivity {

    private ImageView imgView;
    private TextView txtNamaHalaman, txtIsiHalaman;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);

        String nama = getIntent().getStringExtra("key_nama");

        getSupportActionBar().setTitle(nama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgView = findViewById(R.id.imageView);
        txtNamaHalaman = findViewById(R.id.nama_hal);
        txtIsiHalaman = findViewById(R.id.isi_hal);

        Glide.with(MuseumActivity.this).load(R.drawable.istana_siak).into(imgView);
        txtNamaHalaman.setText("Istana Siak Sri Indrapura");
        txtIsiHalaman.setText("\" Istana Matahari Timur \" atau disebut juga Asserayah Hasyimiah atau ini dibangun oleh Sultan Syarif Hasyim Abdul Jalil Syaifuddin pada tahun 1889 oleh arsitek berkebangsaan Jerman. Arsitektur bangunan merupakan gabungan antara arsitektur Melayu, Arab, Eropa.\n" +
                "\n" +
                "Banguna Istana Siak bersejarah tersebut selesai pada tahun 1893. Pada dinding istana dihiasi dengan keramik khusus didatangkan buatan Prancis. Beberapa koleksi benda antik Istana, kini disimpan Museum Nasional Jakarta, Istananya sendiri menyimpan duplikat dari koleksi tersebut. \n" +
                "\n" +
                "Diantara koleksi benda antik Istana Siak adalah: Keramik dari Cina, Eropa, Kursi-kursi kristal dibuat tahun 1896, Patung perunggu Ratu Wihemina merupakan hadiah Kerajaan Belanda, patung pualam Sultan Syarim Hasim I bermata berlian dibuat pada tahun 1889, perkakas seperti sendok, piring, gelas-cangkir berlambangkan Kerajaan Siak masih terdapat dalam Istana. \n" +
                "\n" +
                "Dipuncak bangunan terdapat enam patung burung elang sebagai lambang keberanian Istana. Sekitar istana masih dapat dilihat delapan meriam menyebar ke berbagai sisi-sisi halaman istana, disebelah kiri belakang Istana terdapat bangunan kecil sebagai penjara sementara. \n" +
                "\n" +
                "\n" +
                "Alamat : Istana Siak Sri Indrapura di Jl. Sultan Syarif Kasim, kabupaten Siak, Riau.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
