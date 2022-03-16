package com.example.c196_studentapp.GUI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_studentapp.Entity.assessmentEntity;
import com.example.c196_studentapp.R;

import java.util.List;

public class assessmentAdapter extends RecyclerView.Adapter<assessmentAdapter.assessmentViewHolder> {

    List<assessmentEntity> mAllAssessments;
    Context context;

    public assessmentAdapter(Context contexts, List<assessmentEntity> allAssessments){
        context = contexts;
        mAllAssessments = allAssessments;
    }

    @Override
    public void onBindViewHolder(@NonNull assessmentViewHolder holder, int position) {

        holder.name.setText(mAllAssessments.get(position).getAssessmentName());
        holder.type.setText(mAllAssessments.get(position).getAssessmentType());
        holder.end.setText(mAllAssessments.get(position).getEndDate());
    }

    @NonNull
    @Override
    public assessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.assessment_items, parent, false);
        return new assessmentViewHolder(view);
    }

    @Override
    public int getItemCount() {

        return mAllAssessments.size();
    }

    public class assessmentViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView type;
        TextView end;

        public assessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewAssessName);
            type = itemView.findViewById(R.id.textViewAssessType);
            end = itemView.findViewById(R.id.textViewAssessEndDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final assessmentEntity current = mAllAssessments.get(position);
                    Intent intent = new Intent(context, assessmentDetails.class);
                    intent.putExtra("id" , String.valueOf(current.getAssessmentID()));
                    intent.putExtra("name",String.valueOf(current.getAssessmentName()));
                    intent.putExtra("course",String.valueOf(current.getCourseID()));
                    intent.putExtra("type",String.valueOf(current.getAssessmentType()));
                    intent.putExtra("end", String.valueOf(current.getEndDate()));
                    context.startActivity(intent);
                }
            });
        }
    }

    public void resetList(List<assessmentEntity> allAssessments){
        mAllAssessments = allAssessments;
        notifyDataSetChanged();
    }
}
