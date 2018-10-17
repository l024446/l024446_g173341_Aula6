package br.unicamp.ft.l024446_g173341_aula6;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "EXEMPLO";
    private static final int DB_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL("CREATE TABLE tabela " +
                "(_id INTEGER PRIMARY KEY);");*/

        String criaTabela = "CREATE TABLE tabela"+
                "(_id INTEGER PRIMARY KEY, "+
                  "nome Text, "+
                  "erros INTEGER, "+
                  "acertos INTEGER, "+
                  "mediaidademaior float, "+
                  "mediaidademenor float);";
        db.execSQL(criaTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
