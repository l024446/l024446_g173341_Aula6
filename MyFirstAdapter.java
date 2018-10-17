package br.unicamp.ft.l024446_g173341_aula6;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MyFirstAdapter extends RecyclerView.Adapter{

    private ArrayList<Aluno> alunos;
    public MyOnItemClickListener setMyOnItemClickListener;
    public MyOnLongItemClickListener setMyOnLongItemClickListener;

    public MyFirstAdapter(ArrayList<Aluno> alunos ){
        this.alunos= alunos;
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.setMyOnItemClickListener = myOnItemClickListener;
    }

    public void setMyOnLongItemClickListener(MyOnLongItemClickListener myOnLongItemClickListener) {
        this.setMyOnLongItemClickListener= myOnLongItemClickListener;
    }

    public interface MyOnItemClickListener {
        void MyOnItemClick(String nome);

    }

    public interface MyOnLongItemClickListener {
        void MyOnLongItemClick(String nome, int pos);

    }


   /* public void setMyOnItemLongClickListener(AdapterView.OnItemLongClickListener myOnItemLongClickListener){
        public boolean onItemLongClick(ArrayList<alunos>, int position){
            alunos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, alunos.size());
        }
    }*/




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_layout, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setMyOnItemClickListener != null) {
                    TextView txt = view.findViewById(R.id.nome);
                    setMyOnItemClickListener.MyOnItemClick(txt.getText().toString());
                }
            }
        });
        return new MyFirstViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyFirstViewHolder)holder).bind(alunos.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (setMyOnItemClickListener != null) {
                    TextView txt = view.findViewById(R.id.nome);
                    setMyOnLongItemClickListener.MyOnLongItemClick(txt.getText().toString(),position);

                    notifyDataSetChanged();
                }

                return false;


            }
        });
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    class MyFirstViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;

        public MyFirstViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.nome);
            textView2 = itemView.findViewById(R.id.ra);
            textView3 = itemView.findViewById(R.id.miniCV);

        }

        public void bind(final Aluno aluno) {
            imageView.setImageResource(aluno.getFoto());
            textView1.setText(aluno.getNome());
            textView2.setText(aluno.getRa());
            textView3.setText(Html.fromHtml(aluno.getMiniCV()));

        }

    }




}
