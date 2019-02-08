package com.example.arshad.uea.Admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arshad.uea.EventPost;
import com.example.arshad.uea.MyEvent;
import com.example.arshad.uea.Organizer.Participants;
import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ApprovedMonthlyRecyclerAdapter extends RecyclerView.Adapter<com.example.arshad.uea.Admin.ApprovedMonthlyRecyclerAdapter.ViewHolder> {

    public List<MyEvent> myEventList;
    public Context context;

    private FirebaseFirestore firebaseFirestore;

    public ApprovedMonthlyRecyclerAdapter(List<MyEvent> myEventList){

        this.myEventList = myEventList;

    }



    @Override
    public com.example.arshad.uea.Admin.ApprovedMonthlyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_monthly_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new com.example.arshad.uea.Admin.ApprovedMonthlyRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final com.example.arshad.uea.Admin.ApprovedMonthlyRecyclerAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);



        String event_id = myEventList.get(position).getEvent_id();
        firebaseFirestore.collection("Approved_Events").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    String eventName = task.getResult().getString("name");

                    holder.setEventName(eventName);

                } else {
                    //Firebase Exception
                }
            }
        });



    }


    @Override
    public int getItemCount() {

        if(myEventList != null) {

            return myEventList.size();

        } else {

            return 0;

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;


        private TextView eventName;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }



        public void setEventName(String name){
            eventName = mView.findViewById(R.id.name_et);
            eventName.setText(name);

        }


    }


}