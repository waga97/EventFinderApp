package com.example.arshad.uea.Organizer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arshad.uea.Admin.AdminHome;
import com.example.arshad.uea.EventPost;
import com.example.arshad.uea.R;
import com.example.arshad.uea.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizerEventRecyclerAdapter extends RecyclerView.Adapter<OrganizerEventRecyclerAdapter.ViewHolder> {

    public List<EventPost> event_list;
    public List<User> user_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public OrganizerEventRecyclerAdapter(List<EventPost> event_list, List<User> user_list){

        this.event_list = event_list;
        this.user_list = user_list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.organizer_event_list_item, parent, false);
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

        String name_data = event_list.get(position).getName();
        holder.setNameText(name_data);

        String date_data = event_list.get(position).getDate();
        holder.setDateText(date_data);


        String user_id = event_list.get(position).getUser_id();

        if(user_id.equals(currentUserId)){

            holder.deleteBtn.setEnabled(true);
            holder.editBtn.setEnabled(true);
            holder.viewBtn.setEnabled(true);

            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.editBtn.setVisibility(View.VISIBLE);
            holder.viewBtn.setVisibility(View.VISIBLE);
        }



        String userName = user_list.get(position).getName();
        holder.setUserData(userName);

        try {
            long millisecond = event_list.get(position).getTimestamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
            holder.setTime(dateString);
        } catch (Exception e) {

            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        final DocumentReference ref_details =firebaseFirestore.collection("Approved_Events").document(eventPostId);
        final DocumentReference ref_participant =firebaseFirestore.collection("Approved_Events").document(eventPostId).collection("Participant").document("Participant");
        final DocumentReference ref_student_register= firebaseFirestore.collection("Users").document().collection("Register").document(eventPostId);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                new android.app.AlertDialog.Builder(v.getContext())
                        .setMessage("Confirm delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //firebaseFirestore.collection("Events").document(eventPostId).delete();
                                //ref_details.delete();
                                firebaseFirestore.collection("Approved_Events").document(eventPostId).delete();

                                event_list.remove(position);
                                user_list.remove(position);
                                notifyDataSetChanged();

                                context.startActivity(new Intent (context,OrganizerHome.class));
                                Toast.makeText(v.getContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Alert Dialog","Negative");
                            }
                        })
                        .show();






            }
        });

        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent viewinetnt = new Intent(context, OrganizerViewParticipant.class);
                viewinetnt.putExtra("event_id", eventPostId);
                context.startActivity(viewinetnt);


            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewinetnt = new Intent(context, OrganizerEventUpdate.class);
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
        private TextView dateView;

        private TextView eventDate;

        private TextView eventUserName;

        private ImageView viewBtn;
        private ImageView editBtn;
        private ImageView deleteBtn;






        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            viewBtn = mView.findViewById(R.id.view_btn);
            editBtn = mView.findViewById(R.id.edit_btn);
            deleteBtn = mView.findViewById(R.id.delete_btn);


        }


        public void setNameText(String nameText){

            nameView = mView.findViewById(R.id.event_namr);
            nameView.setText(nameText);

        }

        public void setDateText(String dateText){

            dateView = mView.findViewById(R.id.event_date);
            dateView.setText(dateText);

        }

        public void setTime(String date) {

            eventDate = mView.findViewById(R.id.event_post_date);
            eventDate.setText(date);

        }


        public void setUserData(String name){
            eventUserName = mView.findViewById(R.id.event_host_name);
            eventUserName.setText(name);

        }





    }



}

