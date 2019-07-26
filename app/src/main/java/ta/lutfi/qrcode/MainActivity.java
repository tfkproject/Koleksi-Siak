package ta.lutfi.qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSejarah, btnScan, btnPanduan, btnTentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        //cek camera permission // cek izin kamera
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkCameraPermission();
        }

        btnSejarah = findViewById(R.id.btn_sejarah);
        btnSejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MuseumActivity.class);
                intent.putExtra("key_nama", "Tentang Museum");
                startActivity(intent);
            }
        });
        btnScan = findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnPanduan = findViewById(R.id.btn_panduan);
        btnPanduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PanduanActivity.class);
                intent.putExtra("key_nama", "Cara Penggunaan");
                startActivity(intent);
            }
        });
        btnTentang = findViewById(R.id.btn_tentang);
        btnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TentangActivity.class);
                intent.putExtra("key_nama", "Tentang Aplikasi");
                startActivity(intent);
            }
        });
    }


    public static final int MY_PERMISSIONS_REQUEST = 99;
    public boolean checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed // Bertanya kepada pengguna apakah diperlukan penjelasan
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // Tampilkan penjelasan kepada pengguna * secara tidak sinkron * - jangan blokir
                // utas ini menunggu respons pengguna! Setelah pengguna
                // lihat penjelasannya, coba lagi untuk meminta izin.

                //Prompt the user once explanation has been shown // Prompt pengguna setelah penjelasan ditampilkan
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST);


            } else {
                // No explanation needed, we can request the permission. // Tidak diperlukan penjelasan, kami dapat meminta izin.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty. // Jika permintaan dibatalkan, array hasil kosong.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the // izin diberikan. Lakukan
                    // contacts-related task you need to do. // tugas yang berhubungan dengan kontak yang perlu Anda lakukan.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {

                        //do something //lakukan sesuatu
                        //pilihGambar();
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.// Izin ditolak, Nonaktifkan fungsi yang tergantung pada izin ini.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
            // baris 'case' lainnya untuk memeriksa izin lain yang mungkin diminta aplikasi ini.
            // Anda dapat menambahkan di sini pernyataan kasus lainnya sesuai dengan kebutuhan Anda.
        }
    }
}
