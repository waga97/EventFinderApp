package com.example.arshad.uea;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {

    public List<EventPost> event_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public EventRecyclerAdapter(List<EventPost> event_list){

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

        final String eventPostId = event_list.get(position).EventPostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        String name_data = event_list.get(position).getName();
        holder.setNameText(name_data);

        String date_data = event_list.get(position).getDate();
        holder.setDateText(date_data);

        String time_data = event_list.get(position).getTime();
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

        private TextView eventDate;

        private TextView eventUserName;

        private ImageView eventRegisterBtn;
        private ImageView imageView;





        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            eventRegisterBtn = mView.findViewById(R.id.event_register_btn);


        }





        public void setNameText(String nameText){

            nameView = mView.findViewById(R.id.event_name);
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



    }


}