package ta.lutfi.qrcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import ta.lutfi.qrcode.Util.Request;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgView;
    private TextView txtNamaKoleksi, txtMasaSultan, txtDeskripsi;
    private Button btnPlay, btnScan;
    private ProgressDialog pDialog;

    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("Keterangan Koleksi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNamaKoleksi = findViewById(R.id.nama_koleksi);
        imgView = findViewById(R.id.imageView);
        txtMasaSultan = findViewById(R.id.masa_sultan);
        txtDeskripsi = findViewById(R.id.deskripsi);
        btnPlay = findViewById(R.id.btn_play);
        btnScan = findViewById(R.id.btn_scan);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            mPlayer.stop();
            Intent intent = new Intent(DetailActivity.this, ScanActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class getData extends AsyncTask<Void,Void,String> {

        private int scs = 0;
        private String id_koleksi,
                m_id_koleksi,
                m_masa_sultan,
                m_nama_koleksi,
                m_deskripsi,
                m_file_gambar,
                m_file_audio,
                m_message;

        public getData(String id_koleksi){
            this.id_koleksi = id_koleksi;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_koleksi", id_koleksi);

                try {
                    //convert this HashMap to encodedUrl to send to php file // konversi HashMap ini ke encodedUrl untuk dikirim ke file php
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file // buat permintaan Http dan kirim data ke file php
                    String response = Request.post("http://103.111.86.246/app/koleksi-siak/api/detail.php", dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable // Menyimpan setiap item json dalam variabel
                            m_id_koleksi = c.getString("id_koleksi");
                            m_nama_koleksi = c.getString("nama_koleksi");
                            m_masa_sultan = c.getString("masa_sultan");
                            m_deskripsi = c.getString("deskripsi");
                            m_file_gambar = c.getString("file_gambar");
                            m_file_audio = c.getString("file_audio");

                        }
                    } else {
                        // no data found // tidak ada data ditemukan
                        m_message = ob.getString("message");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(scs == 1){
                txtNamaKoleksi.setText(m_nama_koleksi);
                Glide.with(DetailActivity.this).load(m_file_gambar).into(imgView);
                txtMasaSultan.setText(m_masa_sultan);
                txtDeskripsi.setText(m_deskripsi);

                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // putar audio
                        putarAudio(m_file_audio);
                    }
                });

                btnScan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // tutup activity
                        mPlayer.stop();
                        Intent intent = new Intent(DetailActivity.this, ScanActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            pDialog.dismiss();
        }

    }

    private String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        String id = getIntent().getStringExtra("key_id_koleksi");
        new getData(id).execute();
    }

    private void putarAudio(String audioUrl){

        // Initialize a new media player instance // Inisialisasi instance pemutar media baru
        mPlayer = new MediaPlayer();

        // Set the media player audio stream type // Tetapkan jenis aliran audio pemutar media
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //Try to play music/audio from url // Cobalah memainkan musik / audio dari url
        try{
                    /*
                        void setDataSource (String path)
                            Sets the data source (file-path or http/rtsp URL) to use.

                        Parameters
                            path String : the path of the file, or the http/rtsp URL of the stream you want to play

                        Throws
                            IllegalStateException : if it is called in an invalid state

                                When path refers to a local file, the file may actually be opened by a
                                process other than the calling application. This implies that the
                                pathname should be an absolute path (as any other process runs with
                                unspecified current working directory), and that the pathname should
                                reference a world-readable file. As an alternative, the application
                                could first open the file for reading, and then use the file
                                descriptor form setDataSource(FileDescriptor).

                            IOException
                            IllegalArgumentException
                            SecurityException
                    */
            // Set the audio data source // Tetapkan sumber data audio
            mPlayer.setDataSource(audioUrl);

                    /*
                        void prepare ()
                            Prepares the player for playback, synchronously. After setting the
                            datasource and the display surface, you need to either call prepare()
                            or prepareAsync(). For files, it is OK to call prepare(), which blocks
                            until MediaPlayer is ready for playback.

                        Throws
                            IllegalStateException : if it is called in an invalid state
                            IOException
                    */
            // Prepare the media player // Siapkan pemutar media
            mPlayer.prepare();

            // Start playing audio from http url // Mulai putar audio dari url http
            mPlayer.start();
            btnPlay.setEnabled(false);

        }catch (IOException e){
            // Catch the exception // Tangkap pengecualian
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btnPlay.setEnabled(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        mPlayer.stop();
        finish();
    }
}
