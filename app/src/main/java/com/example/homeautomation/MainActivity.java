package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
    final DatabaseReference smartAC=myRef.child("smartAC").child("status");
    Switch s1;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1=(Switch) findViewById(R.id.button1);
        textView1=(TextView) findViewById(R.id.textView1);
        smartAC.addValueEventListener(new ValueEventListener() {
            boolean firstTime=true;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);
                Log.d("file","value is:"+value);
                textView1.setText("smartAC"+value);
                if (firstTime){
                    if (value.equals("ON")){
                        s1.setChecked(true);
                        firstTime=false;
                    }else
                    {
                        s1.setChecked(false);
                        firstTime=false;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            Log.w("file","Failed to read value", error.toException());
            }
        });
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
           if (s1.isChecked())
               smartAC.setValue(("ON"));
                else
                    smartAC.setValue("OFF");
            }
        });
    }
}