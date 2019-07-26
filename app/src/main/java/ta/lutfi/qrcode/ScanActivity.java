package ta.lutfi.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        getSupportActionBar().setTitle("Scan QRCode");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view // Inisialisasi pemrograman tampilan pemindai
        setContentView(mScannerView);                // Set the scanner view as the content view // Tetapkan tampilan pemindai sebagai tampilan konten
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.// Daftarkan diri kita sebagai penangan untuk hasil pemindaian.


        mScannerView.startCamera();          // Start camera on resume // Mulai kamera pada resume
        mScannerView.setAutoFocus(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause // Hentikan kamera saat jeda
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        //Toast.makeText(this, "Hasil: "+rawResult.getText()+", jenis: "+rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        // Lakukan sesuatu dengan hasilnya di sini
        //Toast.makeText(this, "Hasil:" + rawResult.getText () + ", jenis:" + rawResult.getBarcodeFormat (). ToString (), Toast.LENGTH_SHORT) .show ();
        Intent intent = new Intent(ScanActivity.this, DetailActivity.class);
        intent.putExtra("key_id_koleksi", rawResult.getText());
        startActivity(intent);
        finish();
        // If you would like to resume scanning, call this method below: // Jika Anda ingin melanjutkan pemindaian, panggil metode ini di bawah:
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent = new Intent(ScanActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
