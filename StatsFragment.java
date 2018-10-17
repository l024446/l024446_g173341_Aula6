package br.unicamp.ft.l024446_g173341_aula6;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    //links para conectar com a interface grafica
    private TextView statsValorErros;
    private TextView statsValorAcertos;
    private TextView statsMediaParaMais;
    private TextView statsMediaParaMenos;
    private TextView statsMaisErrou1;
    private TextView statsNomeMaisErrou1;
    private TextView statsMaisErrou2;
    private TextView statsNomeMaisErrou2;
    private TextView statsMaisErrou3;
    private TextView statsNomeMaisErrou3;
    private Button statsBtReset;
    private View view;
    private final String jogoReiniciado = "Jogo iniciado   ";
    private final String valorReiniciado = " 0 ";

    //gerenciador do banco de dados
    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    //Strings para o banco de dados
    String selectCursor = "Select * from tabela";

    //Construtor
    public StatsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_stats, container, false);
        }
        statsValorErros = (TextView) view.findViewById(R.id.statsValorErros);
        statsValorAcertos = (TextView) view.findViewById(R.id.statsValorAcertos);
        statsMediaParaMais = (TextView) view.findViewById(R.id.statsMediaParaMais);
        statsMediaParaMenos = (TextView) view.findViewById(R.id.statsMediaParaMenos);
        statsMaisErrou1 = (TextView) view.findViewById(R.id.statsMaisErrou1);
        statsNomeMaisErrou1 = (TextView) view.findViewById(R.id.statsNomeMaisErrou1);
        statsMaisErrou2 = (TextView) view.findViewById(R.id.statsMaisErrou2);
        statsNomeMaisErrou2 = (TextView) view.findViewById(R.id.statsNomeMaisErrou2);
        statsMaisErrou3 = (TextView) view.findViewById(R.id.statsMaisErrou3);
        statsNomeMaisErrou3 = (TextView) view.findViewById(R.id.statsNomeMaisErrou3);
        statsBtReset = (Button) view.findViewById(R.id.statsBtReset);
        dbHelper = new DatabaseHelper(getContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();


        //O botao reset apaga todas as entradas do banco de dados e inicializa uma nova série de stats
        statsBtReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //apaga os dados da tabela
                sqLiteDatabase.execSQL("delete from tabela");

                //reinicia as textviews
                iniciaJogo();

            }
        });



        atualizaView();
        return view;
    }

    //função utilizada para atualizar a view de estatisticas
    public void atualizaView (){

        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery(selectCursor, null);

        iniciaJogo();

        //verifica se o banco de dados já possui informações para atualizar o stats
        if(cursor.moveToFirst()){
            setPorcentagens();
            setmedias();
            setMaisErrou();


        }

        cursor.close();

    }


    //Função usada para resetar os valores da view
    public void iniciaJogo(){
        statsMaisErrou1.setText(valorReiniciado);
        statsMaisErrou2.setText(valorReiniciado);
        statsMaisErrou3.setText(valorReiniciado);
        statsMediaParaMais.setText(valorReiniciado);
        statsMediaParaMenos.setText(valorReiniciado);
        statsNomeMaisErrou1.setText(jogoReiniciado);
        statsNomeMaisErrou2.setText(jogoReiniciado);
        statsNomeMaisErrou3.setText(jogoReiniciado);
        statsValorAcertos.setText(valorReiniciado);
        statsValorErros.setText(valorReiniciado);
    }

    //função usada para setar as porcentagens de erro e acerto
    public void setPorcentagens(){
        Cursor cursor = sqLiteDatabase.rawQuery(selectCursor, null);
        float  valorErros = 0, valorAcertos = 0, total = 0;
        int indexErros = cursor.getColumnIndex("erros");
        int indexAcertos = cursor.getColumnIndex("acertos");

            if(cursor.moveToFirst()){
                do{
                    valorErros = (valorErros + cursor.getFloat(indexErros));
                    valorAcertos = (valorAcertos + cursor.getFloat(indexAcertos));
                }while(cursor.moveToNext());

                total = valorAcertos+valorErros;

                valorAcertos = (valorAcertos*100)/total;
                valorErros = (valorErros*100)/total;
            }

            String porcentagemErro= String.format("%.2f", valorErros);
            String porcentagemAcerto= String.format("%.2f", valorAcertos);
            statsValorErros.setText(porcentagemErro);
            statsValorAcertos.setText(porcentagemAcerto);

        cursor.close();
    }

    //função para setar as médias de quem erra para mais e para menos
    public void setmedias(){
        float mediaParaMais = 0, mediaParaMenos = 0, bdMediaParaMais = 0, bdMediaParaMenos = 0;
        int indexIdadeMaior, indexIdadeMenor;
        int contador_medias = 0;

        Cursor cursor = sqLiteDatabase.rawQuery(selectCursor, null);
        indexIdadeMaior = cursor.getColumnIndex("mediaidademaior");
        indexIdadeMenor = cursor.getColumnIndex("mediaidademenor");

        if(cursor.moveToFirst()) {
            //calcula a media de erros para mais
            do {
                bdMediaParaMais = cursor.getFloat(indexIdadeMaior);
                mediaParaMais = (mediaParaMais + bdMediaParaMais);
                contador_medias++;
            } while (cursor.moveToNext());
            mediaParaMais = mediaParaMais / contador_medias;
        }
            contador_medias = 0;


        if(cursor.moveToFirst()) {
        //calcula a media de erros para menos
            do {
                bdMediaParaMenos = cursor.getFloat(indexIdadeMenor);
                mediaParaMenos = (mediaParaMenos + bdMediaParaMenos);
                contador_medias++;
            } while (cursor.moveToNext());
            mediaParaMenos = mediaParaMenos / contador_medias;
        }

            String txtMediaParaMais = String.format("%.2f", mediaParaMais);
            String txtMediaParaMenos = String.format("%.2f", mediaParaMenos);
            statsMediaParaMais.setText(txtMediaParaMais);
            statsMediaParaMenos.setText(txtMediaParaMenos);
        cursor.close();
    }

    public void setMaisErrou(){

        String nomeMaisErrou1 = "",
                nomeMaisErrou2 = "",
                nomeMaisErrou3 = "";
        int erro1 = -1, erro2 = 0, erro3 = 0;


        Cursor cursor = sqLiteDatabase.rawQuery(selectCursor, null);


        int indexNome = cursor.getColumnIndex("nome");
        int indexValor = cursor.getColumnIndex("erros");

        if(cursor.moveToFirst()){
            do{
                if(erro1 <= cursor.getInt(indexValor)) {

                    nomeMaisErrou3 = nomeMaisErrou2;
                    nomeMaisErrou2 = nomeMaisErrou1;
                    nomeMaisErrou1 = cursor.getString(indexNome);

                    erro3 = erro2;
                    erro2 = erro1;
                    erro1 = cursor.getInt(indexValor);

            //        Toast.makeText(getContext(), nomeMaisErrou1+" "+erro1+" "+nomeMaisErrou2+" "+erro2+" "+nomeMaisErrou3+" "+erro3, Toast.LENGTH_SHORT).show();
                }

            }while(cursor.moveToNext());
        }

        cursor.close();

        statsMaisErrou1.setText(String.valueOf(erro1));
        statsNomeMaisErrou1.setText(nomeMaisErrou1);
        statsMaisErrou2.setText(String.valueOf(erro2));
        statsNomeMaisErrou2.setText(nomeMaisErrou2);
        statsMaisErrou3.setText(String.valueOf(erro3));
        statsNomeMaisErrou3.setText(nomeMaisErrou3);


    }

    public void onStop() {
        super.onStop();

        sqLiteDatabase.close();
        dbHelper.close();
    }


}
