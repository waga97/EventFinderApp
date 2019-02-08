package com.example.arshad.uea.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.arshad.uea.Organizer.OrganizerViewParticipant;
import com.example.arshad.uea.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ApprovedByMonth extends AppCompatActivity {

    private TextView janCount;
    private TextView febCount;
    private TextView marCount;
    private TextView aprCount;
    private TextView mayCount;
    private TextView junCount;
    private TextView julCount;
    private TextView augCount;
    private TextView sepCount;
    private TextView octCount;
    private TextView novCount;
    private TextView decCount;

    private Button backBtn;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_by_month);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToDashboard();

            }
        });

        firebaseFirestore.collection("Approved_By_Month/").document("1").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                janCount = findViewById(R.id.jan_count);

                if(documentSnapshots.isEmpty()){

                    janCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    janCount.setText(String.valueOf(count));
                    janCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "1");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });

        firebaseFirestore.collection("Approved_By_Month/").document("2").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                febCount = findViewById(R.id.feb_count);

                if(documentSnapshots.isEmpty()){

                    febCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    febCount.setText(String.valueOf(count));
                    febCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "2");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });

        firebaseFirestore.collection("Approved_By_Month/").document("3").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                marCount = findViewById(R.id.mar_count);

                if(documentSnapshots.isEmpty()){

                    marCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    marCount.setText(String.valueOf(count));
                    marCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "3");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });

        firebaseFirestore.collection("Approved_By_Month/").document("4").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                aprCount = findViewById(R.id.apr_count);

                if(documentSnapshots.isEmpty()){

                    aprCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    aprCount.setText(String.valueOf(count));
                    aprCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "4");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });

        firebaseFirestore.collection("Approved_By_Month/").document("5").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                mayCount = findViewById(R.id.may_count);

                if(documentSnapshots.isEmpty()){

                    mayCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    mayCount.setText(String.valueOf(count));
                    mayCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "5");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });

        firebaseFirestore.collection("Approved_By_Month/").document("6").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                junCount = findViewById(R.id.jun_count);

                if(documentSnapshots.isEmpty()){

                    junCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    junCount.setText(String.valueOf(count));
                    junCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "6");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });


        firebaseFirestore.collection("Approved_By_Month/").document("7").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                julCount = findViewById(R.id.jul_count);

                if(documentSnapshots.isEmpty()){

                    julCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    julCount.setText(String.valueOf(count));
                    julCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "7");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });


        firebaseFirestore.collection("Approved_By_Month/").document("8").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                augCount = findViewById(R.id.aug_count);

                if(documentSnapshots.isEmpty()){

                    augCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    augCount.setText(String.valueOf(count));
                    augCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "8");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });

        firebaseFirestore.collection("Approved_By_Month/").document("9").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                sepCount = findViewById(R.id.sep_count);

                if(documentSnapshots.isEmpty()){

                    sepCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    sepCount.setText(String.valueOf(count));
                    sepCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "9");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });

        firebaseFirestore.collection("Approved_By_Month/").document("10").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                octCount = findViewById(R.id.oct_count);

                if(documentSnapshots.isEmpty()){

                    octCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    octCount.setText(String.valueOf(count));
                    octCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "10");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });


        firebaseFirestore.collection("Approved_By_Month/").document("11").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                novCount = findViewById(R.id.nov_count);

                if(documentSnapshots.isEmpty()){

                    novCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    novCount.setText(String.valueOf(count));
                    novCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "11");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });


        firebaseFirestore.collection("Approved_By_Month/").document("12").collection("Month").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                decCount = findViewById(R.id.dec_count);

                if(documentSnapshots.isEmpty()){

                    decCount.setText("N/A");


                } else if (!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    decCount.setText(String.valueOf(count));
                    decCount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent viewinetnt = new Intent(ApprovedByMonth.this, ApprovedMonthlyView.class);
                            viewinetnt.putExtra("month_id", "12");
                            startActivity(viewinetnt);

                        }
                    });

                }

            }
        });





    }

    private void  sendToDashboard (){
        Intent logoutintent = new Intent(ApprovedByMonth.this, AdminDashboard.class);
        startActivity(logoutintent);
        finish();
    }

    private void  sendToApproved (){
        Intent logoutintent = new Intent(ApprovedByMonth.this, ApprovedEvent.class);
        startActivity(logoutintent);
        finish();
    }
}
