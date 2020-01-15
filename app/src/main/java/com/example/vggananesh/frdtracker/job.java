package com.example.vggananesh.frdtracker;

import android.app.job.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class job extends JobIntentService {
    private final String noti="personal_notification";
    private final int id=001;
    public String hi;
    static void eq(Context context,Intent work) {
        enqueueWork(context,job.class,123,work);
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();

        String c2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.getReference("users1").child(c2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {


                hi=dataSnapshot.child("phone").getValue(String.class);
                //Toast.makeText(job.this,hi, Toast.LENGTH_LONG).show();

                FirebaseDatabase database1=FirebaseDatabase.getInstance();


                database1.getReference("users").child(hi).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {

                        String bye=dataSnapshot.child("req").getValue(String.class);
                        if(bye.equalsIgnoreCase("1")) {
                            Toast.makeText(job.this, "hi", Toast.LENGTH_SHORT).show();
                            notific();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean isStopped() {
        return super.isStopped();
    }

    public void notific()
    {
        NotificationCompat.Builder bul=new NotificationCompat.Builder(this,noti);
        bul.setSmallIcon(R.drawable.loc3);
        bul.setContentTitle("simple");
        bul.setContentText("hi man i am gani");
        bul.setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(id,bul.build());
    }
}
