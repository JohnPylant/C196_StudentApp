package com.example.c196_studentapp.GUI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_studentapp.Entity.courseEntity;
import com.example.c196_studentapp.R;

import java.util.List;

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.courseHolder> {

    List<courseEntity> mAllCourses;
    Context context;

    public courseAdapter(Context contexts, List<courseEntity> allCourses){
        context = contexts;
        mAllCourses = allCourses;
    }

    @NonNull
    @Override
    public courseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_items, parent, false);
        return new courseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull courseHolder holder, int position) {

        holder.name.setText(mAllCourses.get(position).getCourseName());
        holder.start.setText(mAllCourses.get(position).getStartDate());
        holder.end.setText(mAllCourses.get(position).getEndDate());
    }

    public void resetLists(List<courseEntity> allCourses){
        mAllCourses = allCourses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mAllCourses.size();
    }

    public class courseHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView start;
        TextView end;

        public courseHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textClassName);
            start = itemView.findViewById(R.id.textClassStart);
            end = itemView.findViewById(R.id.textClassEnd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final courseEntity current = mAllCourses.get(position);
                    Intent intent = new Intent(context, courseDetails.class);
                    intent.putExtra("id", String.valueOf(current.getCourseID()));
                    intent.putExtra("name", String.valueOf(current.getCourseName()));
                    intent.putExtra("term", String.valueOf(current.getTermID()));
                    intent.putExtra("start", String.valueOf(current.getStartDate()));
                    intent.putExtra("end", String.valueOf(current.getEndDate()));
                    intent.putExtra("status", String.valueOf(current.getCourseStatus()));
                    intent.putExtra("instructorName",String.valueOf(current.getInstructorName()));
                    intent.putExtra("instructorPhone",String.valueOf(current.getInstructorPhone()));
                    intent.putExtra("instructorEMail",String.valueOf(current.getInstructorEmail()));
                    intent.putExtra("notes",String.valueOf(current.getCourseNotes()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
