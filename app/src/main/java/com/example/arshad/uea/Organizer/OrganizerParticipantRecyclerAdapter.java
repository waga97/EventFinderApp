package com.example.arshad.uea.Organizer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arshad.uea.Organizer.Participants;
import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrganizerParticipantRecyclerAdapter extends RecyclerView.Adapter<com.example.arshad.uea.Organizer.OrganizerParticipantRecyclerAdapter.ViewHolder> {

    public List<Participants> participantsList;
    public Context context;

    private FirebaseFirestore firebaseFirestore;

    public OrganizerParticipantRecyclerAdapter(List<Participants> participantsList){

        this.participantsList = participantsList;

    }

    @Override
    public com.example.arshad.uea.Organizer.OrganizerParticipantRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new com.example.arshad.uea.Organizer.OrganizerParticipantRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final com.example.arshad.uea.Organizer.OrganizerParticipantRecyclerAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);



        String user_id = participantsList.get(position).getUser_id();
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");
                    String userid = task.getResult().getString("id");
                    String useremail = task.getResult().getString("email");


                    holder.setUserData(userName);
                    holder.setUserId(userid);
                    holder.setUserEmail(useremail);


                } else {

                    //Firebase Exception

                }

            }
        });

    }


    @Override
    public int getItemCount() {

        if(participantsList != null) {

            return participantsList.size();

        } else {

            return 0;

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;


        private TextView participant_name;
        private TextView participant_id;
        private TextView participant_email;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }



        public void setUserData(String name){
            participant_name = mView.findViewById(R.id.reg_student_name);
            participant_name.setText(name);

        }

        public void setUserId(String userid){
            participant_id = mView.findViewById(R.id.reg_student_id);
            participant_id.setText(userid);

        }

        public void setUserEmail(String email){
            participant_email = mView.findViewById(R.id.reg_student_email);
            participant_email.setText(email);

        }

    }

}