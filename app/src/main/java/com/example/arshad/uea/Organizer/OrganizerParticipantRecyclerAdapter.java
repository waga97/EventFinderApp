package com.example.arshad.uea.Organizer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrganizerParticipantRecyclerAdapter extends RecyclerView.Adapter<OrganizerParticipantRecyclerAdapter.ViewHolder> {

    public List<Participant> participantList;
    public Context context;

    private FirebaseFirestore firebaseFirestore;

    public OrganizerParticipantRecyclerAdapter(List<Participant> participantList){

        this.participantList = participantList;

    }

    @Override
    public OrganizerParticipantRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrganizerParticipantRecyclerAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);


        //User Data will be retrieved here...

        String user_id = participantList.get(position).getUserId();
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");


                    holder.setparticipantName(userName);


                } else {

                    //Firebase Exception

                }

            }
        });

    }

    @Override
    public int getItemCount() {

        if(participantList != null) {

            return participantList.size();

        } else {

            return 0;

        }

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView participantName;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setparticipantName(String name){

            participantName = mView.findViewById(R.id.reg_student_name);
            participantName.setText(name);

        }

    }

}