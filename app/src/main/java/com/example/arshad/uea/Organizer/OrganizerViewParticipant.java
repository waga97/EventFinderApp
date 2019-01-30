package com.example.arshad.uea.Organizer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.arshad.uea.Admin.AdminHome;
import com.example.arshad.uea.MainActivity;
import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrganizerViewParticipant extends AppCompatActivity {

    private Toolbar orgeventpartToolbar;

    private RecyclerView participant_list;
    private OrganizerParticipantRecyclerAdapter organizerParticipantRecyclerAdapter;
    private List<Participants> participantsList;

    private FirebaseFirestore firebaseFirestore;



    private String event_id;

    private Bitmap bitmap;
    private ConstraintLayout viewP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_view_participant);

        firebaseFirestore = FirebaseFirestore.getInstance();
        event_id = getIntent().getStringExtra("event_id");


        firebaseFirestore.collection("Approved_Events").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String eventName = task.getResult().getString("name");

                orgeventpartToolbar = (Toolbar) findViewById(R.id.org_event_part_toolbar);
                setSupportActionBar(orgeventpartToolbar);
                getSupportActionBar().setTitle(eventName);

            }

        });


        participant_list = findViewById(R.id.my_event_list_view);

        //RecyclerView Firebase List
        participantsList = new ArrayList<>();
        organizerParticipantRecyclerAdapter = new OrganizerParticipantRecyclerAdapter(participantsList);
        participant_list.setHasFixedSize(true);
        participant_list.setLayoutManager(new LinearLayoutManager(this));
        participant_list.setAdapter(organizerParticipantRecyclerAdapter);


        firebaseFirestore.collection("Approved_Events/" + event_id + "/Participant")
                .addSnapshotListener(OrganizerViewParticipant.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (!documentSnapshots.isEmpty()) {

                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String commentId = doc.getDocument().getId();
                                    Participants participants = doc.getDocument().toObject(Participants.class);
                                    participantsList.add(participants);
                                    organizerParticipantRecyclerAdapter.notifyDataSetChanged();


                                }
                            }

                        }

                    }
                });




        viewP =findViewById(R.id.view_p);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.participantlist_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:

                Log.d("size"," "+viewP.getWidth() +"  "+viewP.getWidth());
                bitmap = loadBitmapFromView(viewP, viewP.getWidth(), viewP.getHeight());
                createPdf();
                return true;


            case R.id.action_back:

                sendToBack();
                return true;



            default:
                return false;
        }

    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        android.graphics.pdf.PdfDocument document = new android.graphics.pdf.PdfDocument();
        android.graphics.pdf.PdfDocument.PageInfo pageInfo = new android.graphics.pdf.PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        android.graphics.pdf.PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);




        // write the document content





        Date currentTime = java.util.Calendar.getInstance().getTime();
        String targetPdf = "/sdcard/" + currentTime + "_participant_list.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();



    }

    private void  sendToBack (){
        Intent logoutintent = new Intent(OrganizerViewParticipant.this, OrganizerViewEvent.class);
        startActivity(logoutintent);
        finish();
    }









}
