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

public class PendingRecyclerAdapter extends RecyclerView.Adapter<PendingRecyclerAdapter.ViewHolder> {

    public List<EventPost> event_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public PendingRecyclerAdapter(List<EventPost> event_list){

        this.event_list = event_list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_list_item, parent, false);
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

        final String eventName = event_list.get(position).getName();
        holder.setNameText(eventName);

        final String eventDate = event_list.get(position).getDate();
        final String eventTime = event_list.get(position).getTime();
        final String eventVenue = event_list.get(position).getVenue();
        final String eventDesc = event_list.get(position).getDesc();
        final String eventImage = event_list.get(position).getImage_url();
        final Date eventTimestamp = event_list.get(position).getTimestamp();








        final String eventHost = event_list.get(position).getUser_id();
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



        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Map<String, Object> approveMap = new HashMap<>();
                approveMap.put("event_id", eventPostId);
                approveMap.put("name", eventName);
                approveMap.put("date", eventDate);
                approveMap.put("time", eventTime);
                approveMap.put("venue", eventVenue);
                approveMap.put("desc", eventDesc);
                approveMap.put("image_url", eventImage);
                approveMap.put("timestamp", eventTimestamp);
                approveMap.put("user_id", eventHost);

                firebaseFirestore.collection("Approved_Events").document(eventPostId).set(approveMap);

                firebaseFirestore.collection("Pending_Events").document(eventPostId).delete();
                event_list.remove(position);
                notifyDataSetChanged();

                context.startActivity(new Intent (context,AdminHome.class));
                Toast.makeText(v.getContext(), "Successfully approved", Toast.LENGTH_SHORT).show();


            }
        });

        holder.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Map<String, Object> declineMap = new HashMap<>();
                declineMap.put("event_id", eventPostId);
                declineMap.put("name", eventName);
                declineMap.put("date", eventDate);
                declineMap.put("time", eventTime);
                declineMap.put("venue", eventVenue);
                declineMap.put("desc", eventDesc);
                declineMap.put("image_url", eventImage);
                declineMap.put("timestamp", eventTimestamp);
                declineMap.put("user_id", eventHost);

                firebaseFirestore.collection("Declined_Events").document(eventPostId).set(declineMap);

                firebaseFirestore.collection("Pending_Events").document(eventPostId).delete();
                event_list.remove(position);
                notifyDataSetChanged();

                context.startActivity(new Intent (context,AdminHome.class));
                Toast.makeText(v.getContext(), "Successfully declined", Toast.LENGTH_SHORT).show();

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

        private Button approveBtn;
        private Button declineBtn;





        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            approveBtn = mView.findViewById(R.id.approve_btn);
            declineBtn = mView.findViewById(R.id.decline_btn);
            nameView = mView.findViewById(R.id.name_et);
            eventUserName = mView.findViewById(R.id.organizer_et);


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