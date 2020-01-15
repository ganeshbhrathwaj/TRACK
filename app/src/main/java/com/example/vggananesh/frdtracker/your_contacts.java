package com.example.vggananesh.frdtracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class your_contacts extends AppCompatActivity {
    ListView lv;
    ArrayList<adapt> ad;
    Button so;
    FirebaseAuth firebaseAuth;
    ArrayList<String> gani;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_contacts);
        ad=new ArrayList<adapt>();



        so=findViewById(R.id.so);
        lv=findViewById(R.id.lv);

        gani=new ArrayList<String>();



        firebaseAuth=FirebaseAuth.getInstance();


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myref=rootRef.child("users");



       ValueEventListener eventListener=new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot)
           {
               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String n=ds.child("phone").getValue(String.class);
                   //Toast.makeText(your_contacts.this,n, Toast.LENGTH_SHORT).show();
                    re(n);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       };
        myref.addListenerForSingleValueEvent(eventListener);
        //myref.child("users").child("+919962403175").child("req").setValue("0");

       lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = lv.getItemAtPosition(position).toString();
                Toast.makeText(your_contacts.this,gani.get(position), Toast.LENGTH_SHORT).show();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference myref=database.getReference();
                myref.child("users").child(gani.get(position)).child("req").setValue("1");
                Intent it=new Intent(your_contacts.this,maps.class);
                startActivity(it);

                //startService(new Intent(your_contacts.this,myservice.class));






            }


        });
       job.eq(this,new Intent(your_contacts.this,job.class));
        //startService(new Intent(your_contacts.this,myservice.class));


    }


    public  void  re(String s)
    {

        final Cursor cursor =getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        startManagingCursor(cursor);

        while(cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).equalsIgnoreCase(s))
            {
                gani.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                adapt a = new adapt(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)), cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                ad.add(a);
            }


        }
        cursor.close();
        adp r=new adp(your_contacts.this,ad);
        lv.setAdapter(r);



    }


    public void sign_out(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }





}
