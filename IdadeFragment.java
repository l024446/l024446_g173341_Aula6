package br.unicamp.ft.l024446_g173341_aula6;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import br.unicamp.ft.l024446_g173341_aula6.interfaces.OnBiografiaRequest;

import static br.unicamp.ft.l024446_g173341_aula6.Alunos.alunos;



/**
 * A simple {@link Fragment} subclass.
 */
public class IdadeFragment extends Fragment {

    //links para conectar com a interface grafica
    public ImageView imageView1;
    public TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public Button mButton1;
    public Button mButton2;
    public Button mButton3;
    public Button mButton4;
    public Button mButton5;
    public Button mButton6;
    public Button mButton7;
    public Button mButton8;
    public Button mButton9;
    View view1;

    //variáveis
    public int idade;
    public int tentativas;

    //gerenciadores para o jogo
    Random gerador = new Random();
    final int size = Alunos.alunos.length;
    public int alunoJOGO = 0;
    private OnBiografiaRequest onBiografiaRequest;

    //gerenciador do banco de dados
    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    //Strings para o banco de dados
    String selectCursor = "Select * from tabela";

    //construtor
    public IdadeFragment() {
        // Required empty public constructor
    }


    //metodo onCreate inicializa o fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view1 == null) {
            view1 = inflater.inflate(R.layout.fragment_idade, container, false);
        }
        imageView1 = (ImageView) view1.findViewById(R.id.fotojogo);
        textView1 = (TextView)view1.findViewById(R.id.nomejogo);
        textView2 = (TextView)view1.findViewById(R.id.tentativas);
        textView3 = (TextView) view1.findViewById(R.id.resposta);
        mButton1 = (Button) view1.findViewById(R.id.dezenove);
        mButton2 = (Button) view1.findViewById(R.id.vinte);
        mButton3 = (Button) view1.findViewById(R.id.vinteum);
        mButton4 = (Button) view1.findViewById(R.id.vintedois);
        mButton5 = (Button) view1.findViewById(R.id.vintetres);
        mButton6 = (Button) view1.findViewById(R.id.vintequatro);
        mButton7 = (Button) view1.findViewById(R.id.vintecinco);
        mButton8 = (Button) view1.findViewById(R.id.vinteseis);
        mButton9 = (Button) view1.findViewById(R.id.vintesete);
        dbHelper = new DatabaseHelper(getContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();
        startGame();

        View.OnClickListener guessButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case (R.id.dezenove):
                        testejogo(19);
                        break;
                    case (R.id.vinte):
                        testejogo(20);
                        break;
                    case (R.id.vinteum):
                        testejogo(21);
                        break;
                    case (R.id.vintedois):
                        testejogo(22);
                        break;
                    case (R.id.vintetres):
                        testejogo(23);
                        break;
                    case (R.id.vintequatro):
                        testejogo(24);
                        break;
                    case (R.id.vintecinco):
                        testejogo(25);
                        break;
                    case (R.id.vinteseis):
                        testejogo(26);
                        break;
                    case (R.id.vintesete):
                        testejogo(27);
                        break;

                }

            }
        };

        mButton1.setOnClickListener(guessButtonListener);
        mButton2.setOnClickListener(guessButtonListener);
        mButton3.setOnClickListener(guessButtonListener);
        mButton4.setOnClickListener(guessButtonListener);
        mButton5.setOnClickListener(guessButtonListener);
        mButton6.setOnClickListener(guessButtonListener);
        mButton7.setOnClickListener(guessButtonListener);
        mButton8.setOnClickListener(guessButtonListener);
        mButton9.setOnClickListener(guessButtonListener);

        return view1;

    }

    //metodo usado para abrir o biografia request do aluno quando o jogador perde o jogo
    public void setOnBiografiaRequest(OnBiografiaRequest onBiografiaRequest) {
        this.onBiografiaRequest = onBiografiaRequest;
    }

    //inicia o jogo
    public void startGame() {

        alunoJOGO=gerador.nextInt(size);
        System.out.println(alunoJOGO);
        tentativas = 3;

        imageView1.setImageResource(alunos[alunoJOGO].getFoto());
        textView1.setText(alunos[alunoJOGO].getNome());
        idade = alunos[alunoJOGO].getIdade();
        textView2.setText(String.valueOf("tentativas restantes: "+tentativas));



    }

    //faz os testes e define se o jogador acertou ou errou
    public void testejogo(int idade){
        if (alunos[alunoJOGO].idade == idade) {
            correto(alunos[alunoJOGO].nome);
        }
        else {
            if(alunos[alunoJOGO].idade > idade) {
                incorreto(idade, alunos[alunoJOGO].idade, "maior",alunos[alunoJOGO].nome);
            }else
                if(alunos[alunoJOGO].idade < idade){
                    incorreto(idade, alunos[alunoJOGO].idade, "menor",alunos[alunoJOGO].nome);
            }

        }

    }

    //resposta quando o jogador acetou (chama a função que atualiza a tabela do banco de dados).
    public void correto(String nome){
        textView3.setText("correto!!");
        atualizarAcerto(nome);
        view1.postDelayed(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        textView3.setText("jogue novamente!! ");

                        startGame();
                    }
                }, 2000);
    }

    //resposta quando o jogador errou (chama a função que atualiza a tabela do banco de dados);
    public void incorreto(int idadeSelecionada, int idadeCorreta, String maiorMenor, String nome){
        tentativas--;
        if (tentativas > 0 ){
            textView3.setText("incorreto, a idade é "+maiorMenor+".");
            textView2.setText(String.valueOf("tentativas restantes: "+tentativas));
            atualizarErro(nome, idadeSelecionada, idadeCorreta);

        }
        else {
            textView2.setText(String.valueOf("tentativas restantes: "+tentativas));
            textView3.setText("você perdeu!! ");
            view1.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getContext(),"Conheça seu colega!! "+ alunos[alunoJOGO].nome,Toast.LENGTH_SHORT).show();
                            if (onBiografiaRequest != null){
                                onBiografiaRequest.onRequest(alunoJOGO);
                            }

                        }
                    }, 2000);
        }
    }

    //métodos para gerenciar a entrada de dados no banco de dados

    //atualiza  a tabela quando o jogador acerta
    public void atualizarAcerto(String nome){
        int indexId, indexNome, indexAcerto;
        String valida;
        int id, pos = 0;
        Cursor cursor4;

        boolean encontrou = false;//para verificar se o registro existe

        String whereClause = "nome = ?";
        String[] whereArgs   = new String[]{nome};
        ContentValues cv = new ContentValues();


        //inicializa o cursor e recebe os valores de index
        cursor4 = sqLiteDatabase.rawQuery(selectCursor, null);
        indexId = cursor4.getColumnIndex("_id");
        indexNome = cursor4.getColumnIndex("nome");
        indexAcerto = cursor4.getColumnIndex("acertos");

        //verifica se o nome já está no banco de dados
        if(cursor4.moveToFirst()){
            do{
                valida = cursor4.getString(indexNome);
                if(nome.equals(valida)){
                    encontrou = true;
                    pos = cursor4.getPosition();
                }
            }while (cursor4.moveToNext());
        }


        //se o nome está no banco de dados atualiza a coluna de vitórias, se o nome não está no banco de dados inclui uma nova entrada

        if(encontrou){
            cursor4.moveToPosition(pos);
            int valor = cursor4.getInt(indexAcerto);
            valor++;
            cv.put("acertos", valor);
            //Toast.makeText(getContext(),valor+" ", Toast.LENGTH_LONG).show();
            sqLiteDatabase.update("tabela", cv, whereClause, whereArgs);
        }else{
            //identifica a posição do ultimo id, se o banco estiver vazio coloca o id como zero
            if(cursor4.moveToFirst()){
                cursor4.moveToLast();
                id = cursor4.getInt(indexId);
                id++;
            }else{
                id = 0;
            }

            cv.put("_id", id);
            cv.put("nome", nome);
            cv.put("erros", 0);
            cv.put("acertos",1);
            cv.put("mediaidademaior", 0);
            cv.put("mediaidademenor", 0);

            //Toast.makeText(getContext(),id+" "+nome, Toast.LENGTH_LONG).show();
            sqLiteDatabase.insert("tabela", null, cv);
        }
        cursor4.close();
    }

    /*atualiza a tabela quando o jogador erra, verifica se o aluno ja
    /esta no bd, verifica se o erro foi para mais ou para menos e chama
    /a função correta para atualizar o bd
    */
    public void atualizarErro(String nome, int idadeSelecionada, int idadeReal){
        int indexNome, indexId;
        int pos = 0;
        int id = 0;

        boolean alunoEncontrado = false;
        String nomeAlunoBd;
        Cursor cursor;

        //cria um cursor e verifica se o nome existe
        cursor = sqLiteDatabase.rawQuery(selectCursor, null);

        //verificação para identificar se o aluno já está no BD
        if(cursor.moveToFirst()){
            do{
                indexNome = cursor.getColumnIndex("nome");
                nomeAlunoBd = cursor.getString(indexNome);
                if(nome.equals(nomeAlunoBd)){
                    alunoEncontrado = true;
                    pos = cursor.getPosition();
                }
                cursor.moveToNext();
            }while (cursor.moveToNext());
        }

        //calcula o valor do Id para incluir no bd se necessário
        indexId = cursor.getColumnIndex("_id");
        if(cursor.moveToFirst()){
            cursor.moveToLast();
            id = cursor.getInt(indexId);
            id++;
        }

        //O cursor não será mais necessário por isso foi fechado
        cursor.close();

        if(alunoEncontrado){
            if(idadeReal>idadeSelecionada){
                atualizaIdadeMenor(pos, idadeReal, idadeSelecionada);
            }else{
                atualizaIdadeMaior(pos, idadeReal, idadeSelecionada);
            }
        }else{

            if(idadeReal>idadeSelecionada){
                insereErroIdadeMenor(id, nome,  idadeReal, idadeSelecionada);
            }else{
                insereErroIdadeMaior(id, nome, idadeReal, idadeSelecionada);
            }
        }

    }

    //funcao para atualizar quando a idade jogada é maior do que a idade real
    private void atualizaIdadeMaior(int pos, int idadeReal, int idadeSelecionada){
        int indexMediaErro, indexErro, indexNome, totalErros;
        float media;
        float mediaBd;
        ContentValues cv = new ContentValues();
        String nome;

        String whereClause = "nome = ?";



        //cria um cursor e move ele para a posicao correta
        Cursor cursor1 = sqLiteDatabase.rawQuery(selectCursor, null);
        cursor1.moveToPosition(pos);

        //inicializa os valores de index
        indexMediaErro = cursor1.getColumnIndex("mediaidademaior");
        indexErro = cursor1.getColumnIndex("erros");
        indexNome = cursor1.getColumnIndex("nome");

        //calcula diferenca da jogada
        media = (idadeSelecionada-idadeReal);
        mediaBd = cursor1.getFloat(indexMediaErro);

        //calcula a media de erros para mais do aluno pegando os dados do bd
        media = ((media+mediaBd)/2);

        //incrementa o total de erros
        totalErros = cursor1.getInt(indexErro);
        totalErros++;

        //recebe o nome do aluno para o argumento e cria a String whereArgs
        nome = cursor1.getString(indexNome);
        String[] whereArgs   = new String[]{nome};

        //coloca os dados que serão alterados no Content Values
        cv.put("erros", totalErros);
        cv.put("mediaidademenor",media);

        //Toast.makeText(getContext(), totalErros+" "+media, Toast.LENGTH_LONG).show();

        //realiza o update na tabela
        sqLiteDatabase.update("tabela", cv, whereClause, whereArgs);

        cursor1.close();
    }

    //funcao para atualizar quando a idade jogada é menor do que a idade real
    private void atualizaIdadeMenor(int pos, int idadeReal, int idadeSelecionada){
        int indexMediaErro, indexErro, indexNome, totalErros;
        float media;
        float mediaBd;
        ContentValues cv = new ContentValues();
        String nome;

        String whereClause = "nome = ?";



        //cria um cursor e move ele para a posicao correta
        Cursor cursor2 = sqLiteDatabase.rawQuery(selectCursor, null);
        cursor2.moveToPosition(pos);

        //inicializa os valores de index
        indexMediaErro = cursor2.getColumnIndex("mediaidademenor");
        indexErro = cursor2.getColumnIndex("erros");
        indexNome = cursor2.getColumnIndex("nome");

        //calcula diferenca da jogada
        media = idadeReal-idadeSelecionada;
        mediaBd = cursor2.getFloat(indexMediaErro);

        //calcula a media de erros para menos do aluno pegando os dados do bd
        media = ((media+mediaBd)/2);

        //incrementa o total de erros
        totalErros = cursor2.getInt(indexErro);
        totalErros++;

        //recebe o nome do aluno para o argumento e cria a String whereArgs
        nome = cursor2.getString(indexNome);
        String[] whereArgs   = new String[]{nome};

        //coloca os dados que serão alterados no Content Values
        cv.put("erros", totalErros);
        cv.put("mediaidademenor",media);

        //Toast.makeText(getContext(), totalErros+" "+media, Toast.LENGTH_LONG).show();


        //realiza o update na tabela
        sqLiteDatabase.update("tabela", cv, whereClause, whereArgs);

        cursor2.close();
    }

    private void insereErroIdadeMaior(int id, String nome, int idadeReal, int idadeSelecionada){
        float media;
        ContentValues cv = new ContentValues();

        //calcula diferenca da jogada
        media = idadeSelecionada-idadeReal;


        //Toast.makeText(getContext(), id+" "+nome+" "+media, Toast.LENGTH_LONG).show();

        //coloca os dados que serão alterados no Content Values
        cv.put("_id", id);
        cv.put("nome", nome);
        cv.put("erros", 1);
        cv.put("acertos", 0);
        cv.put("mediaidademenor",0);
        cv.put("mediaidademaior", media);

        //realiza o update na tabela
        sqLiteDatabase.insert("tabela", null, cv);
    }

    private void insereErroIdadeMenor(int id, String nome, int idadeReal, int idadeSelecionada){
        float media;
        ContentValues cv = new ContentValues();

        //calcula diferenca da jogada
        media = idadeReal-idadeSelecionada;

        //coloca os dados que serão alterados no Content Values
        cv.put("_id", id);
        cv.put("nome", nome);
        cv.put("erros", 1);
        cv.put("acertos", 0);
        cv.put("mediaidademenor",media);
        cv.put("mediaidademaior", 0);

        //Toast.makeText(getContext(), id+" "+nome+" "+media, Toast.LENGTH_LONG).show();

        //realiza o update na tabela
        sqLiteDatabase.insert("tabela", null, cv);

    }

    public void onStop() {
        super.onStop();

        sqLiteDatabase.close();
        dbHelper.close();
    }

}
