package br.gov.df.caesb.manobra;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Consulta extends Activity {

    private ListView lista;
    private SQLiteDatabase bancoDados;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> itens;
    private String condicional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta);

        recuReq();

    }
    private void recuReq() {
        try{

            Intent intentCampo = getIntent();
            Bundle parametros = intentCampo.getExtras();
            String campo = parametros.getString("chave campo");
            String sp = parametros.getString("chave spinner");
            bancoDados = openOrCreateDatabase("novo", MODE_PRIVATE, null);


            if(!campo.isEmpty())
                condicional = " endereco LIKE '%"+ campo + "%'";

            if ( ! sp.equals("SELECIONAR RA") )
            {
                if(condicional != null)
                    condicional = condicional + " AND ra = '"  + sp.toUpperCase() + "' ";
                else
                    condicional = " ra = '"  + sp.toUpperCase() + "' ";
            }

            if(campo.isEmpty() && sp.equals("SELECIONAR RA"))
                condicional = " 1=1 ";

            final Cursor cursor = bancoDados.rawQuery("SELECT * FROM manobra WHERE" +  condicional  + "ORDER BY id ASC", null);

            final int inColId = cursor.getColumnIndex("id");
            final int inColCod = cursor.getColumnIndex("codigo");
            int inColRA = cursor.getColumnIndex("ra");
            int inColLocalizada = cursor.getColumnIndex("localizadoEm");
            final int inColMaterial = cursor.getColumnIndex("material");
            final int inColAfetada = cursor.getColumnIndex("areaAfetada");
            final int inColEndereco = cursor.getColumnIndex("endereco");
            int inColTampa = cursor.getColumnIndex("tampa");
            int inColProfundidade = cursor.getColumnIndex("profundidade");
            int inColDiametro = cursor.getColumnIndex("diametro");
            final int inColDimensao = cursor.getColumnIndex("dimensao");
            int inColnDescarga = cursor.getColumnIndex("descarga");

            lista = (ListView) findViewById(R.id.listViewId);
            itens = new ArrayList<String>();
            adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text2,itens);
            lista.setAdapter(adaptador);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(Consulta.this, Identificacao.class);
                    intent.putExtra("endereco", itens.get(inColEndereco)) ;

                    if(position % 2 == 0){
                        lista.setBackgroundColor(Color.BLUE);
                    }
                    else{
                        lista.setBackgroundColor(Color.WHITE);
                    }

                    Toast.makeText(getBaseContext(), "item Clicado", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

            cursor.moveToFirst();

            while (cursor != null){
                itens.add(cursor.getString(inColCod) + "                                                                                    1 " + cursor.getString(inColEndereco));
                cursor.moveToNext();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

