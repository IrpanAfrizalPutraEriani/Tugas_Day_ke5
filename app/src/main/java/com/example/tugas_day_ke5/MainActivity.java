package com.example.tugas_day_ke5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText Nama, Kode, Jumlah;
    Button proses;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nama = findViewById(R.id.teksKonsumen);
        Kode = findViewById(R.id.tekskodebarang);
        Jumlah = findViewById(R.id.teksJumlahbarangdibeli);
        proses = findViewById(R.id.TombolProses);
        radioGroup = findViewById(R.id.radioGroup);

        proses.setOnClickListener(v -> {
            String namaPembeli = Nama.getText().toString().trim();
            String kodeBarang = Kode.getText().toString().trim();
            int jumlahBarang = Integer.parseInt(Jumlah.getText().toString().trim());

            if (kodeBarang.isEmpty()) {
                Toast.makeText(MainActivity.this, getString(R.string.harap_masukkan_kode_barang), Toast.LENGTH_SHORT).show();
                return;
            }

            double hargaBarang = getHargaBarang(kodeBarang);
            if (hargaBarang == -1) {
                Toast.makeText(MainActivity.this, getString(R.string.kode_barang_tidak_valid), Toast.LENGTH_SHORT).show();
                return;
            }

            String MerekBarang = getMerekBarang(kodeBarang);
            if (MerekBarang == null) {
                Toast.makeText(MainActivity.this, getString(R.string.kode_barang_tidak_valid), Toast.LENGTH_SHORT).show();
                return;
            }
            double totalHarga = hargaBarang * jumlahBarang;
            double diskon = hitungDiskon(totalHarga);
            double totalBayar = totalHarga - diskon - hitungPotonganHarga(totalHarga);
            double potonganHarga = hitungPotonganHarga(totalHarga);
            tampilkanBon(namaPembeli, kodeBarang, MerekBarang, hargaBarang, jumlahBarang, totalHarga, potonganHarga, diskon, totalBayar);
        });
    }

    private double getHargaBarang(String kodeBarang) {
        switch (kodeBarang.toUpperCase()) {
            case "LV3":
                return 6666666;
            case "AA5":
                return 9999999;
            case "MP3":
                return 28999999;
            default:
                return -1;
        }
    }

    private String getMerekBarang(String kodeBarang) {
        switch (kodeBarang.toUpperCase()) {
            case "LV3":
                return "Lenovo V14 Gen 3";
            case "AA5":
                return "Acer Aspire 5";
            case "MP3":
                return "MACBOOK PRO M3";
            default:
                return null;
        }
    }

    private double hitungPotonganHarga(double totalHarga) {
        double potonganHarga = 0;
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.rbPLATINUM) {
            potonganHarga = totalHarga * 0.1;
        } else if (selectedRadioButtonId == R.id.rbPREMIUM) {
            potonganHarga = totalHarga * 0.05;
        } else if (selectedRadioButtonId == R.id.rbVIP) {
            potonganHarga = totalHarga * 0.02;
        }
        return potonganHarga;
    }

    private double hitungDiskon(double totalHarga) {
        if (totalHarga > 10000000) {
            return 100000;
        }
        return 0;
    }

    private void tampilkanBon(String namaPembeli, String kodeBarang, String merekBarang, double hargaBarang, int jumlahBarang, double totalHarga, double potonganHarga, double diskon, double totalBayar) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String hargabarang = getString(R.string.rp) + " " + decimalFormat.format(hargaBarang);
        String hargaJumlah =  decimalFormat.format(jumlahBarang);
        String hargatotal = getString(R.string.rp) + " " + decimalFormat.format(totalHarga);
        String hargabayar = getString(R.string.rp) + " " + decimalFormat.format(totalBayar);
        String hargaPotongan = getString(R.string.rp) + " " + decimalFormat.format(potonganHarga);
        String Diskon = getString(R.string.rp) + " " + decimalFormat.format(diskon);

        String bonMessage = getString(R.string.transaksi_hari_ini) + "\n\n"
                + getString(R.string.nama_pembeli) + namaPembeli + "\n\n"
                + getString(R.string.kode_barang) + kodeBarang + "\n\n"
                + getString(R.string.merek_barang) + merekBarang + "\n\n"
                + getString(R.string.harga_barang_satuan) + hargabarang + "\n\n"
                + getString(R.string.jumlah_barang_dibeli) + hargaJumlah + "\n\n"
                + getString(R.string.total_harga) + hargatotal + "\n\n"
                + getString(R.string.diskon_membership) + hargaPotongan + "\n\n"
                + getString(R.string.diskon_harga) + Diskon + "\n\n"
                + getString(R.string.total_pembayaran) + hargabayar;

        Intent intent = new Intent(MainActivity.this, Teks_activity.class);

        intent.putExtra("bonMessage", bonMessage);
        // Mulai activity BonBelanjaActivity
        startActivity(intent);
    }
}
