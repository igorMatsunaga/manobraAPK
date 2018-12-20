package br.gov.df.caesb.manobra;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Identificacao extends Activity {

    private SQLiteDatabase bancoDados;
    private Button btnVoltar;
    private ListView lista1, lista2;
    private GridView lista3;
    private ArrayAdapter<String> adaptador1, adaptador2, adaptador3;
    private ArrayList<String> itens1, itens2, itens3;
    private WebView mapas;

    private int[] colors = new int[] { 0xFFFFFFFF, 0x3000FFFF };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identificacao);

        Intent intent = getIntent();
        String parametro = (String) intent.getSerializableExtra("endereco");
        TextView registro = (TextView) findViewById(R.id.regEnt);
        registro.setText("Endereço/localização: " + ' ' + parametro );



        bancoDados = openOrCreateDatabase("novo", MODE_PRIVATE, null);

        Cursor cursor = bancoDados.rawQuery("SELECT * FROM manobra WHERE endereco = " + "'" + parametro + "'" +  "ORDER BY id ASC", null);

        int inColId = cursor.getColumnIndex("id");
        int inColCod = cursor.getColumnIndex("codigo");
        int inColRA = cursor.getColumnIndex("ra");
        int inColLocalizada = cursor.getColumnIndex("localizadoEm");
        int inColMaterial = cursor.getColumnIndex("material");
        int inColAfetada = cursor.getColumnIndex("areaAfetada");
        int inColEndereco = cursor.getColumnIndex("endereco");
        int inColTampa = cursor.getColumnIndex("tampa");
        int inColProfundidade = cursor.getColumnIndex("profundidade");
        int inColDiametro = cursor.getColumnIndex("diametro");
        int inColDimensao = cursor.getColumnIndex("dimensao");
        int inColnDescarga = cursor.getColumnIndex("descarga");

        lista1 = (ListView) findViewById(R.id.entID);
        itens1 = new ArrayList<String>();
        adaptador1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text2,itens1);
        lista1.setAdapter(adaptador1);
        cursor.moveToFirst();


        lista2 = (ListView) findViewById(R.id.entProt);
        itens2 = new ArrayList<String>();
        adaptador2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text2,itens2);
        lista2.setAdapter(adaptador2);
        cursor.moveToFirst();


        lista3 = (GridView) findViewById(R.id.entArea);
        itens3 = new ArrayList<String>();
        adaptador3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text2,itens3);
        lista3.setAdapter(adaptador3);
        cursor.moveToFirst();


        itens1.add("RA: " + cursor.getString(inColRA));
        itens1.add("CÓDIGO: " + cursor.getString(inColCod));
        itens1.add("DIÂMETRO: " + cursor.getString(inColDiametro));


        itens2.add("TAMPA: "+cursor.getString(inColTampa));
        itens2.add("LOCALIZAÇÃO: " + cursor.getString(inColLocalizada));
        itens2.add("MATERIAL: " + cursor.getString(inColMaterial));
        itens2.add("PROFUNDIDADE: " + cursor.getString(inColProfundidade));
        itens2.add("DIMENSÃO: " + cursor.getString(inColDimensao));
        itens2.add("DESCARGA: " + cursor.getString(inColnDescarga));

        itens3.add(cursor.getString(inColAfetada));

        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Identificacao.this, TelaInicial.class);
                startActivity(i);
            }
        });

        mapas = (WebView) findViewById(R.id.mapas);
        mapas.loadUrl("https://www.google.com/maps/@-15.8136464,-47.96637,15z");

        WebSettings webSettings = mapas.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}



