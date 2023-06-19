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

public class RVAssessmentListAdapter extends RecyclerView.Adapter<RVAssessmentListAdapter.MyViewHolder>{
    private List<Assessment> assessmentList;
    Context context;

    public RVAssessmentListAdapter(List<Assessment> assessmentList, Context context) {
        this.assessmentList = assessmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessmentlistlayouttemplate,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvAssessName.setText(String.valueOf(assessmentList.get(position).getTitle()));
        holder.tvassesstype.setText(assessmentList.get(position).getAssessmentType().toString());

        holder.bt_View_Assessment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int currentPosition = holder.getAdapterPosition();
                Intent intent = new Intent(context,AssessmentView.class);
                intent.putExtra("id",assessmentList.get(currentPosition).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {return assessmentList.size();}
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssessName;
        TextView tvassesstype;

        Button bt_View_Assessment;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAssessName = itemView.findViewById(R.id.tvAssessName);
            tvassesstype = itemView.findViewById(R.id.tvassesstype);

            bt_View_Assessment = itemView.findViewById(R.id.bt_View_Assessment);
            parentLayout = itemView.findViewById(R.id.assessmentListTemplateID);
        }
    }
}
