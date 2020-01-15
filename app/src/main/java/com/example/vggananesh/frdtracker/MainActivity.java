package com.example.vggananesh.frdtracker;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText phno,code;
    Button b1,b;
    private String pvid;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks vc;
    private PhoneAuthProvider.ForceResendingToken rt;
    private FirebaseAuth fbauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phno= findViewById(R.id.phno);
        code=findViewById(R.id.code);
        b1= findViewById(R.id.b1);
        b=findViewById(R.id.b);
        code.setEnabled(false);
        b1.setEnabled(false);
        fbauth=FirebaseAuth.getInstance();



    }
    public void sendcode(View view)
    {
        String ph=phno.getText().toString().trim();
        if(ph.isEmpty())
        {
            Toast.makeText(this, "enter your phone number", Toast.LENGTH_SHORT).show();
        }
        else {
            setUpVerificationcallbacks();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(ph, 60, TimeUnit.SECONDS, this, vc);
        }
    }

    private void setUpVerificationcallbacks() {
        vc=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                code.setEnabled(false);
                b1.setEnabled(false);
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(String vid,PhoneAuthProvider.ForceResendingToken token)
            {
                Toast.makeText(MainActivity.this, "code sent", Toast.LENGTH_SHORT).show();
                pvid=vid;
                rt=token;
                code.setEnabled(true);
                b1.setEnabled(true);
                b.setEnabled(false);
            }

        };
    }
    public void verifyCode(View view) {

        String cod = code.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(pvid,cod);
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        fbauth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Intent i =new Intent(getApplicationContext(),your_contacts.class);
                            //i.putExtra("myval",phno.getText().toString());
                            startActivity(i);
                            FirebaseUser user = task.getResult().getUser();
                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            DatabaseReference myref=database.getReference();
                            myref.child("users").child(phno.getText().toString()).child("phone").setValue(phno.getText().toString());
                            DatabaseReference myref1=database.getReference();
                            myref1.child("users1").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            myref1.child("users1").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phone").setValue(phno.getText().toString());



                        }
                        else {

                            if (task.getException() instanceof

                                    FirebaseAuthInvalidCredentialsException) {

                                // The verification code entered was invalid

                            }

                        }

                    }

                });

    }

    public void resendCode(View view) {



        String phoneNumber = phno.getText().toString();



        setUpVerificationcallbacks();



        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                phoneNumber,

                60,

                TimeUnit.SECONDS,

                this,

                vc,

                rt);

    }


}
