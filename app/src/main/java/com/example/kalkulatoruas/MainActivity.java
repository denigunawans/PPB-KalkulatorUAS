package com.example.kalkulatoruas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    EditText edtAngka1, edtAngka2;
    TextView txHasil, txRiwayat;
    RadioGroup rg;
    RadioButton rb;
    int selectedBt, temp=1, id = 1;
    SharedPreferences preferences;
    private ArrayList<Riwayat> listRiwayat;
    private RecyclerView recRiwayat;

    RiwayatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        showArray();
        if (listRiwayat.size() == 0){
            id = 1;
        }else{
            id = Integer.parseInt(listRiwayat.get(listRiwayat.size()-1).getId())+1;
        }
    }

    private void initComponent(){
        edtAngka1 = findViewById(R.id.edtAngka1);
        edtAngka2 = findViewById(R.id.edtAngka2);
        txHasil = findViewById(R.id.txHasil);
        txRiwayat = findViewById(R.id.txRiwayat);
        rg = findViewById(R.id.tombol);
        preferences = this.getSharedPreferences(getString(R.string.shared_key), Context.MODE_PRIVATE);
        recRiwayat = findViewById(R.id.rvListRiwayat);

        listRiwayat = new ArrayList<>();
        temp = preferences.getAll().size()+1;

        adapter = new RiwayatAdapter(listRiwayat, this, preferences);
    }

    public void showArray(){
        Map<String, ?> entries = preferences.getAll();
        for (Map.Entry<String, ?> entry: entries.entrySet()) {
            getArray(entry.getKey(), entry.getValue().toString());
        }
    }

    public void cek(){
        selectedBt = rg.getCheckedRadioButtonId();
        rb = findViewById(selectedBt);
        Toast.makeText(this, rb.getText(), Toast.LENGTH_SHORT).show();
    }

    public void saveToShared(String id, String hasil){
        try {
            preferences.edit().putString(id, hasil).apply();
            String value = preferences.getString(id,"");
            getArray(id, value);
            temp++;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getArray(String no, String rwyt){
        try {
            recRiwayat.setAdapter(new RiwayatAdapter(listRiwayat, this, preferences));
            recRiwayat.setLayoutManager(new LinearLayoutManager(this));
            listRiwayat.add(new Riwayat(no, String.valueOf(rwyt)));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("gagal tambah array");
        }
    }

    public void hasil(View v){
        try {
            try{
                String idRwyt = String.valueOf(id);
                double a1 = Double.parseDouble(edtAngka1.getText().toString());
                double a2 = Double.parseDouble(edtAngka2.getText().toString());
                double hasil;
                cek();
                if (rb.getText().equals("Tambah")){
                    hasil = a1 + a2;
                    txHasil.setText("hasil : "+hasil);
                    String riwayat = Double.toString(a1)+" + "+Double.toString(a2)+" = "+Double.toString(hasil);
                    saveToShared(idRwyt, riwayat);
                }else if (rb.getText().equals("Kurang")){
                    hasil = a1 - a2;
                    txHasil.setText("hasil : "+hasil);
                    String riwayat = Double.toString(a1)+" - "+Double.toString(a2)+" = "+Double.toString(hasil);
                    saveToShared(idRwyt, riwayat);
                }else if (rb.getText().equals("Kali")){
                    hasil = a1 * a2;
                    txHasil.setText("hasil : "+hasil);
                    String riwayat = Double.toString(a1)+" x "+Double.toString(a2)+" = "+Double.toString(hasil);
                    saveToShared(idRwyt, riwayat);
                }else if (rb.getText().equals("Bagi")){
                    hasil = a1 / a2;
                    txHasil.setText("hasil : "+hasil);
                    String riwayat = Double.toString(a1)+" / "+Double.toString(a2)+" = "+Double.toString(hasil);
                    saveToShared(idRwyt, riwayat);
                }
                id++;
            }catch (Exception e){
                e.printStackTrace();
                txHasil.setText("gagal");
                Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "faill", Toast.LENGTH_SHORT).show();
        }
    }

}



































