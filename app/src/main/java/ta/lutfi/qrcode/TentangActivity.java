package ta.lutfi.qrcode;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TentangActivity extends AppCompatActivity {

    private ImageView imgView;
    private TextView txtNamaHalaman, txtIsiHalaman;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        String nama = getIntent().getStringExtra("key_nama");

        getSupportActionBar().setTitle(nama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgView = findViewById(R.id.imageView);
        txtNamaHalaman = findViewById(R.id.nama_hal);
        txtIsiHalaman = findViewById(R.id.isi_hal);


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
