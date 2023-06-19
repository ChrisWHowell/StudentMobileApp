package android.reserver.chrishowellstudentschedulerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewTermListAdapter extends RecyclerView.Adapter<RecycleViewTermListAdapter.MyViewHolder>
{
    private List<Term> termList;
    Context context;

    public RecycleViewTermListAdapter(List<Term> termList, Context context) {
        this.termList = termList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.termlistdisplaytemplate,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvt_idnum.setText(String.valueOf(termList.get(position).getId()));
        holder.tvt_termName.setText(termList.get(position).getTitle());
        holder.tvt_SD.setText(termList.get(position).getStartDate());
        holder.tvt_ED.setText(termList.get(position).getEndDate());
        holder.btn_View_Term.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int currentPosition = holder.getAdapterPosition();
                Intent intent = new Intent(context,TermView.class);
                intent.putExtra("id",termList.get(currentPosition).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvt_idnum;
        TextView tvt_termName;
        TextView tvt_SD;
        TextView tvt_ED;
        Button btn_View_Term;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvt_idnum = itemView.findViewById(R.id.tvt_idnum);
            tvt_termName = itemView.findViewById(R.id.tvt_termName);
            tvt_SD = itemView.findViewById(R.id.tvt_SD);
            tvt_ED = itemView.findViewById(R.id.tvt_ED);
            btn_View_Term = itemView.findViewById(R.id.btn_View_Term);
            parentLayout = itemView.findViewById(R.id.termlistTemplateID);
        }
    }
}
