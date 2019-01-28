package com.example.arshad.uea.Admin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshad.uea.EventPost;
import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApprovedRecyclerAdapter extends RecyclerView.Adapter<ApprovedRecyclerAdapter.ViewHolder> {

    public List<EventPost> event_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public ApprovedRecyclerAdapter(List<EventPost> event_list){

        this.event_list = event_list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        final String eventPostId = event_list.get(position).EventPostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        String eventName = event_list.get(position).getName();
        holder.setNameText(eventName);








        String eventHost = event_list.get(position).getUser_id();
        //User Data will be retrieved here...
        firebaseFirestore.collection("Users").document(eventHost).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");


                    holder.setUserData(userName);


                } else {

                    //Firebase Exception

                }

            }
        });







        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Intent viewinetnt = new Intent(context, FullEvent.class);
                viewinetnt.putExtra("event_id", eventPostId);
                context.startActivity(viewinetnt);


            }
        });

        holder.eventUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Intent viewinetnt = new Intent(context, FullEvent.class);
                viewinetnt.putExtra("event_id", eventPostId);
                context.startActivity(viewinetnt);


            }
        });








    }

    @Override
    public int getItemCount() {
        return event_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView nameView;
        private TextView eventUserName;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            eventUserName = mView.findViewById(R.id.organizer_et);
            nameView = mView.findViewById(R.id.name_et);


        }


        public void setNameText(String nameText){

            nameView = mView.findViewById(R.id.name_et);
            nameView.setText(nameText);

        }

        public void setUserData(String name){
            eventUserName = mView.findViewById(R.id.organizer_et);
            eventUserName.setText(name);

        }


    }


}