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

public class RVCourseListAdapter extends RecyclerView.Adapter<RVCourseListAdapter.MyViewHolder> {
    private List<Course> courseList;
    Context context;

    public RVCourseListAdapter(List<Course> courseList, Context context) {
        this.courseList = courseList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courselistdisplaytemplate,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_Course_Name.setText(String.valueOf(courseList.get(position).getTitle()));
        holder.tv_Start_Date.setText(courseList.get(position).getStartDate());

        holder.bt_view_course.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int currentPosition = holder.getAdapterPosition();
                Intent intent = new Intent(context,CourseView.class);
                intent.putExtra("id",courseList.get(currentPosition).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {return courseList.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Course_Name;
        TextView tv_Start_Date;

        Button bt_view_course;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Course_Name = itemView.findViewById(R.id.tv_Course_Name);
            tv_Start_Date = itemView.findViewById(R.id.tv_Start_Date);

            bt_view_course = itemView.findViewById(R.id.bt_view_course);
            parentLayout = itemView.findViewById(R.id.courseListTemplateID);
        }
    }
}
