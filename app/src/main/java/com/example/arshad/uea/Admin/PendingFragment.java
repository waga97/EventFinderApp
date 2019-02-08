package com.example.arshad.uea.Admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arshad.uea.EventPost;
import com.example.arshad.uea.R;
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
public class PendingFragment extends Fragment {

    private RecyclerView approve_list_view;
    private List<EventPost> event_list;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private PendingRecyclerAdapter pendingRecyclerAdapter;

    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;

    public PendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        event_list = new ArrayList<>();
        approve_list_view = view.findViewById(R.id.approve_list_view);

        firebaseAuth = FirebaseAuth.getInstance();

        pendingRecyclerAdapter = new PendingRecyclerAdapter(event_list);
        approve_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        approve_list_view.setAdapter(pendingRecyclerAdapter);
        approve_list_view.setHasFixedSize(true);

        if(firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            approve_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if(reachedBottom){

                        loadMorePost();

                    }

                }
            });

            Query firstQuery = firebaseFirestore.collection("Pending_Events").orderBy("timestamp", Query.Direction.DESCENDING).limit(100);
            firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        if (isFirstPageFirstLoad) {
                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                            event_list.clear();
                        }
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String eventPostId = doc.getDocument().getId();
                                EventPost eventPost = doc.getDocument().toObject(EventPost.class).withId(eventPostId);

                                if (isFirstPageFirstLoad) {
                                    event_list.add(eventPost);
                                } else {
                                    event_list.add(0, eventPost);
                                }
                                pendingRecyclerAdapter.notifyDataSetChanged();
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

            Query nextQuery = firebaseFirestore.collection("Pending_Events")
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
                                EventPost eventPost = doc.getDocument().toObject(EventPost.class).withId(eventPostId);
                                event_list.add(eventPost);

                                pendingRecyclerAdapter.notifyDataSetChanged();
                            }

                        }
                    }

                }
            });

        }

    }

}
