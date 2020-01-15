package com.example.vggananesh.frdtracker;
import android.app.Application;
import android.content.Intent;




import com.example.vggananesh.frdtracker.your_contacts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth fbauth=FirebaseAuth.getInstance();
        FirebaseUser fbuser= fbauth.getCurrentUser();

        if(fbuser!=null)
        {   //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(home.this, your_contacts.class));

        }

    }
}