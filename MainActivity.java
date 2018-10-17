package br.unicamp.ft.l024446_g173341_aula6;

import android.os.Bundle;
import android.content.ContentValues;
import android.support.design.widget.FloatingActionButton;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.unicamp.ft.l024446_g173341_aula6.interfaces.OnBiografiaRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        FragmentManager fragmentManager;


    EditText edtId;
    EditText edtTexto;
    TextView txtOutput;


    public void replaceFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 1);
        contentValues.put("texto", "Primeiro Texto");

        //edtId     = (EditText)findViewById(R.id.edtId);
        //edtTexto  = (EditText)findViewById(R.id.edtTexto);
        //txtOutput = (TextView)findViewById(R.id.txtOutput);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                replaceFragment(new MailFragment(),"mail");

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        MyFirstFragment f1 = (MyFirstFragment)fragmentManager.findFragmentById(R.id.frag1);

        if (savedInstanceState == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MyFirstFragment f2 = new MyFirstFragment();
            fragmentTransaction.add(R.id.frame,f2,"f2_tag");
            fragmentTransaction.commit();
        }



    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.email_settings){
            Toast.makeText(this,"voce cliclou em email",Toast.LENGTH_SHORT).show();
            Fragment mailFragment = fragmentManager.findFragmentByTag("mail");
            if (mailFragment == null){
                mailFragment = new MailFragment();
            }
            replaceFragment(mailFragment, "mail");
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Alunos) {
            Toast.makeText(this,"cliclou em Aluno",Toast.LENGTH_SHORT).show();

            Fragment alunosFragmento = fragmentManager.findFragmentByTag("alunos");
            if (alunosFragmento == null){
                alunosFragmento = new AlunosFragmento();
                ((AlunosFragmento)alunosFragmento).setOnBiografiaRequest(
                        new OnBiografiaRequest() {
                            @Override
                            public void onRequest(int position) {
                                Fragment biografiasFragment = fragmentManager.findFragmentByTag("bio");
                                biografiasFragment = new BiografiasFragment();
                                ((BiografiasFragment)biografiasFragment).setArguments(position);
                                replaceFragment(biografiasFragment,"bio");
                            }
                        }
                );

            }
            replaceFragment(alunosFragmento,"alunos");

        } else if (id == R.id.Biografias) {
            Fragment biografiasFragment = fragmentManager.findFragmentByTag("bio");
            if (biografiasFragment == null){
                biografiasFragment = new BiografiasFragment();
            }
            replaceFragment(biografiasFragment,"bio");

        } else if (id == R.id.Idade) {
            Fragment idadeFragment = fragmentManager.findFragmentByTag("idade");
                idadeFragment = new IdadeFragment();
                ((IdadeFragment)idadeFragment).setOnBiografiaRequest(
                        new OnBiografiaRequest() {
                            @Override
                            public void onRequest(int position) {
                                Fragment biografiasFragment = fragmentManager.findFragmentByTag("bio");
                                biografiasFragment = new BiografiasFragment();
                                ((BiografiasFragment)biografiasFragment).setArguments(position);
                                replaceFragment(biografiasFragment,"bio");
                            }
                        }
                );
            replaceFragment(idadeFragment,"idade");



        } else if (id == R.id.Altura) {
            Toast.makeText(this,"cliclou em Altura",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Coracao1) {
            Toast.makeText(this,"cliclou em coracao 1",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Coracao2) {
            Toast.makeText(this,"cliclou em coracao 2",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            Toast.makeText(this,"cliclou na share",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            Toast.makeText(this,"send ... voce sabe",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Stats) {
            Fragment statsFragment = fragmentManager.findFragmentByTag("stats");
            if (statsFragment == null){
                statsFragment = new StatsFragment();
            }
            replaceFragment(statsFragment,"stats");

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onStop(){
        super.onStop();
    }
}

