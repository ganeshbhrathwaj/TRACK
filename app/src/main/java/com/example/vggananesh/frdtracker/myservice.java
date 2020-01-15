package com.example.vggananesh.frdtracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.Channel;

public class myservice extends Service {
    private final String noti="personal_notification";
    private final int id=001;
    public String hi;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        FirebaseDatabase database=FirebaseDatabase.getInstance();

        String c2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.getReference("users1").child(c2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {


                hi=dataSnapshot.child("phone").getValue(String.class);
               // Toast.makeText(myservice.this, "value of hi is:", Toast.LENGTH_SHORT).show();
                //Toast.makeText(myservice.this,hi, Toast.LENGTH_LONG).show();

                FirebaseDatabase database1=FirebaseDatabase.getInstance();
                //Toast.makeText(myservice.this,hi, Toast.LENGTH_SHORT).show();

                database1.getReference("users").child(hi).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {

                        String bye=dataSnapshot.child("req").getValue(String.class);
                        if(bye.equalsIgnoreCase("1")) {
                            Toast.makeText(myservice.this, "hi", Toast.LENGTH_SHORT).show();
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
       // Toast.makeText(this, "hi da vikki", Toast.LENGTH_SHORT).show();

        return START_STICKY;
        }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "hiiiiiiiiiii", Toast.LENGTH_SHORT).show();
    }


    public void notific()
    {
        NotificationCompat.Builder bul=new NotificationCompat.Builder(this,noti);
        bul.setSmallIcon(R.drawable.loc3);
        bul.setContentTitle("simple");
        bul.setContentText("hi man i am gani");
        bul.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(id,bul.build());
    }

}
