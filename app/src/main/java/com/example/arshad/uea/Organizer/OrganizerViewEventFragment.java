package com.example.arshad.uea.Organizer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arshad.uea.EventPost;
import com.example.arshad.uea.R;
import com.example.arshad.uea.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizerViewEventFragment extends Fragment {

    private RecyclerView event_list_view;
    private List<EventPost> event_list;
    private List<User> user_list;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private OrganizerEventRecyclerAdapter eventRecyclerAdapter;

    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;

    public OrganizerViewEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_organizer_view_event, container, false);

        event_list = new ArrayList<>();
        user_list = new ArrayList<>();
        event_list_view = view.findViewById(R.id.organizer_event_list_view);

        firebaseAuth = FirebaseAuth.getInstance();

        eventRecyclerAdapter = new OrganizerEventRecyclerAdapter(event_list, user_list);
        event_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        event_list_view.setAdapter(eventRecyclerAdapter);
        event_list_view.setHasFixedSize(true);

        if(firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            event_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if(reachedBottom){

                        loadMorePost();

                    }

                }
            });

            Query firstQuery = firebaseFirestore.collection("Events").orderBy("timestamp", Query.Direction.DESCENDING).limit(100);
            firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        if (isFirstPageFirstLoad) {

                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                            event_list.clear();
                            user_list.clear();

                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String eventPostId = doc.getDocument().getId();
                                final EventPost eventPost = doc.getDocument().toObject(EventPost.class).withId(eventPostId);

                                String eventUserId = doc.getDocument().getString("user_id");
                                firebaseFirestore.collection("Users").document(eventUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if(task.isSuccessful()){

                                            User user = task.getResult().toObject(User.class);


                                            if (isFirstPageFirstLoad) {
                                                user_list.add(user);
                                                event_list.add(eventPost);

                                            } else {
                                                user_list.add(0,user);
                                                event_list.add(0, eventPost);

                                            }
                                            eventRecyclerAdapter.notifyDataSetChanged();


                                        }

                                    }
                                });



                            }
                        }

                        isFirstPageFirstLoad = true;

                    }

                }

            });

        }

        // Inflate the layout for this fragment
        return view;
    }

    public void loadMorePost(){

        if(firebaseAuth.getCurrentUser() != null) {

            Query nextQuery = firebaseFirestore.collection("Events")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .startAfter(lastVisible)
                    .limit(100);

            nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String eventPostId = doc.getDocument().getId();
                                final EventPost eventPost = doc.getDocument().toObject(EventPost.class).withId(eventPostId);
                                String eventUserId = doc.getDocument().getString("user_id");
                                firebaseFirestore.collection("Users").document(eventUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if(task.isSuccessful()){

                                            User user = task.getResult().toObject(User.class);

                                            user_list.add(user);
                                            event_list.add(eventPost);


                                            eventRecyclerAdapter.notifyDataSetChanged();


                                        }

                                    }
                                });

                            }

                        }
                    }

                }
            });

        }

    }

}

