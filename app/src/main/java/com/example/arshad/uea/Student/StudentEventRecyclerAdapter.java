package com.example.arshad.uea.Student;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.arshad.uea.EventPost;
import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentEventRecyclerAdapter extends RecyclerView.Adapter<StudentEventRecyclerAdapter.ViewHolder> {

    public List<EventPost> event_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public StudentEventRecyclerAdapter(List<EventPost> event_list){

        this.event_list = event_list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        final boolean[] isImageFitToScreen = new boolean[1];

        final String eventPostId = event_list.get(position).EventPostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        final String name_data = event_list.get(position).getName();
        holder.setNameText(name_data);

        final String date_data = event_list.get(position).getDate();
        holder.setDateText(date_data);

        final String time_data = event_list.get(position).getTime();
        holder.setTimeText(time_data);

        String venue_data = event_list.get(position).getVenue();
        holder.setVenueText(venue_data);

        String desc_data = event_list.get(position).getDesc();
        holder.setDescText(desc_data);



        String image_url = event_list.get(position).getImage_url();
        holder.setEventImage(image_url);



        String user_id = event_list.get(position).getUser_id();
        //User Data will be retrieved here...
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

        try {
            long millisecond = event_list.get(position).getTimestamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
            holder.setTime(dateString);
        } catch (Exception e) {

            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        //Get Register
        firebaseFirestore.collection("Approved_Events/" + eventPostId + "/Participant").document(currentUserId).addSnapshotListener((StudentHome) context, new EventListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if(documentSnapshot.exists()){

                    holder.eventRegisterBtn.setImageDrawable(context.getDrawable(R.mipmap.register_blue));


                } else {

                    holder.eventRegisterBtn.setImageDrawable(context.getDrawable(R.mipmap.register_grey));

                }



            }
        });

        //Register Feature
        holder.eventRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                firebaseFirestore.collection("Approved_Events/" + eventPostId + "/Participant").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(!task.getResult().exists()){

                            Map<String, Object> participateMap = new HashMap<>();
                            participateMap.put("user_id", currentUserId);
                            participateMap.put("timestamp", FieldValue.serverTimestamp());

                            Map<String, Object> registerMap = new HashMap<>();
                            registerMap.put("event_id", eventPostId);
                            registerMap.put("name", name_data);
                            registerMap.put("date", date_data);
                            registerMap.put("time", time_data);
                            registerMap.put("venue", eventPostId);
                            registerMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Approved_Events/" + eventPostId + "/Participant").document(currentUserId).set(participateMap);
                            firebaseFirestore.collection("Users/" + currentUserId + "/Register").document(eventPostId).set(registerMap);
                            Toast.makeText(v.getContext(), "Successfully registered", Toast.LENGTH_SHORT).show();

                        } else {

                            firebaseFirestore.collection("Approved_Events/" + eventPostId + "/Participant").document(currentUserId).delete();
                            firebaseFirestore.collection("Users/" + currentUserId + "/Register").document(eventPostId).delete();
                            Toast.makeText(v.getContext(), "Successfully unregistered", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });

        firebaseFirestore.collection("Approved_Events/" + eventPostId + "/Participant").addSnapshotListener((StudentHome) context, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if(!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();

                    holder.updateLikesCount(count);

                } else {

                    holder.updateLikesCount(0);

                }

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
        private TextView timeView;
        private TextView venueView;
        private TextView descView;
        private TextView favLikeCount;

        private TextView eventDate;

        private TextView eventUserName;

        private ImageView eventRegisterBtn;
        private ImageView imageView;






        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            eventRegisterBtn = mView.findViewById(R.id.event_register_btn);
            imageView = mView.findViewById(R.id.event_image);

        }





        public void setNameText(String nameText){

            nameView = mView.findViewById(R.id.a);
            nameView.setText(nameText);

        }

        public void setDateText(String dateText){

            dateView = mView.findViewById(R.id.event_date);
            dateView.setText(dateText);

        }

        public void setTimeText(String timeText){

            timeView = mView.findViewById(R.id.event_time);
            timeView.setText(timeText);

        }


        public void setTime(String date) {

            eventDate = mView.findViewById(R.id.event_post_date);
            eventDate.setText(date);

        }

        public void setVenueText(String venueText){

            venueView = mView.findViewById(R.id.event_venue);
            venueView.setText(venueText);

        }


        public void setDescText(String descText) {

            descView = mView.findViewById(R.id.event_desc);
            descView.setText(descText);

        }

        public void setUserData(String name){
            eventUserName = mView.findViewById(R.id.event_host_name);
            eventUserName.setText(name);

        }

        public void setEventImage(String downloadUri){

            imageView = mView.findViewById(R.id.event_image);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);

            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri).into(imageView);

        }

        public void updateLikesCount(int count){

            favLikeCount = mView.findViewById(R.id.register_count);
            favLikeCount.setText(count + " Registered");

        }



    }




}


/*
if(!task.getResult().exists()){

                            Map<String, Object> participantsMap = new HashMap<>();
                            participantsMap.put("user_id", currentUserId);
                            participantsMap.put("timestamp", FieldValue.serverTimestamp());


                            Map<String, Object> registerMap = new HashMap<>();
                            registerMap.put("event_id", eventPostId);
                            registerMap.put("timestamp", FieldValue.serverTimestamp());



                            firebaseFirestore.collection("Events/" + eventPostId + "Participant/").document(currentUserId).set(participantsMap);
                            firebaseFirestore.collection("Users/" + currentUserId + "/Register").document(eventPostId).set(registerMap);
                            Toast.makeText(v.getContext(), "Successfully registered", Toast.LENGTH_SHORT).show();

                        } else {

                            firebaseFirestore.collection("Events/" + eventPostId + "Participant/").document(currentUserId).delete();
                            firebaseFirestore.collection("Users/" + currentUserId + "/Register").document(eventPostId).delete();
                            Toast.makeText(v.getContext(), "Successfully unregistered", Toast.LENGTH_SHORT).show();

                        }
 */


/*
holder.imageView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(final View v) {

        if(isImageFitToScreen[0]) {
        isImageFitToScreen[0] =false;
        holder.imageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        holder.imageView.setAdjustViewBounds(true);
        }else{
        isImageFitToScreen[0] =true;
        holder.imageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }



        }
        });
*/