package br.unicamp.ft.l024446_g173341_aula6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.ft.l024446_g173341_aula6.interfaces.OnBiografiaRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlunosFragmento extends Fragment {
    public RecyclerView mRecyclerView;
    public MyFirstAdapter mAdapter;
    View view;
    private OnBiografiaRequest onBiografiaRequest;

    public void setOnBiografiaRequest(OnBiografiaRequest onBiografiaRequest) {
        this.onBiografiaRequest = onBiografiaRequest;
    }

    public AlunosFragmento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_alunos_fragmento, container, false);
        }
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ArrayList aluno;
        aluno = new ArrayList(Arrays.asList(Alunos.alunos));
        mAdapter = new MyFirstAdapter(aluno);
        mRecyclerView.setAdapter(mAdapter);




        mAdapter.setMyOnItemClickListener(
                new MyFirstAdapter.MyOnItemClickListener() {
                    @Override
                    public void MyOnItemClick(String nome) {
                        //Toast.makeText(getContext(),"voce clicou em " + nome,Toast.LENGTH_SHORT).show();

                    }
                }
        );


        mAdapter.setMyOnLongItemClickListener(new MyFirstAdapter.MyOnLongItemClickListener() {
            @Override
            public void MyOnLongItemClick(String nome, int pos) {
                Toast.makeText(getContext(),"voce selecionou " + nome + " da posicao" + pos,Toast.LENGTH_SHORT).show();
                if (onBiografiaRequest != null){
                    onBiografiaRequest.onRequest(pos);
                }
            }

        });
    return view;
    }

}
