package br.unicamp.ft.l024446_g173341_aula6;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static br.unicamp.ft.l024446_g173341_aula6.Alunos.alunos;


/**
 * A simple {@link Fragment} subclass.
 */
public class BiografiasFragment extends Fragment{
    public ImageView imageView1;
    public TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public Button mButton;
    public int biografiaCount = 0;
    View view;




    public void mostrarBiografia() {
        imageView1.setImageResource(alunos[biografiaCount].getFoto());
        textView1.setText(alunos[biografiaCount].getNome());
        textView2.setText(alunos[biografiaCount].getRa());
        textView3.setText(Html.fromHtml(alunos[biografiaCount].getMiniCV()));
    }

    public void setArguments(int pos) {
        biografiaCount = pos;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final int size = Alunos.alunos.length;





        View.OnClickListener listener = new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                if (biografiaCount < size-1) {
                    biografiaCount++;
                }
                else {
                    biografiaCount=0;

                }
                Snackbar.make(view, "Posicao " +(biografiaCount+1) +" de " +size, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                    mostrarBiografia();
            }
        };


        if (view == null) {
            view = inflater.inflate(R.layout.fragment_biografias, container, false);
        }
        imageView1 = (ImageView) view.findViewById(R.id.foto);
        textView1 = (TextView)view.findViewById(R.id.nome);
        textView2 = (TextView) view.findViewById(R.id.ra);
        textView3 = (TextView) view.findViewById(R.id.miniCV);



        mButton = (Button) view.findViewById(R.id.buttonteste);

        mButton.setOnClickListener(listener);


        return view;

    }
    public void onStart() {
        super.onStart();
        mostrarBiografia();
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


}