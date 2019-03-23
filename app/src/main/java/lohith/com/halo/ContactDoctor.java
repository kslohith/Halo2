package lohith.com.halo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactDoctor extends AppCompatActivity {

    private Button added , heartrate , submit;
    private EditText sym;
    private TextView textView;
    private String[] symlist;
    DatabaseReference firebaseDatabase;
    String listsym = "";
    int i = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_doctor);
        added = (Button) findViewById(R.id.add);
        heartrate = (Button) findViewById(R.id.addheart);
        sym = (EditText) findViewById(R.id.symptom);
        textView = (TextView) findViewById(R.id.displaysym);
        symlist = new String[100];
        submit = (Button) findViewById(R.id.submit);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Patients");

        added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                String newsym = sym.getText().toString();
                symlist[i] = newsym;
                i++;
                for(int j = i-1; j < 100; j++ ){
                    if(symlist[j] == null ){
                        continue;
                    }
                    else {

                        listsym += symlist[j] + " ";
                    }
                }
                sym.getText().clear();
                textView.setVisibility(View.VISIBLE);
                textView.setText(listsym);
            }
        });

        heartrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactDoctor.this,video.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               consult newconsult = new consult("Lohith","aspirin paracetomol",listsym,"23" );
               firebaseDatabase.child("lohith").setValue(newconsult);
               Toast.makeText(ContactDoctor.this,"Successfully submitted",Toast.LENGTH_LONG).show();
            }
        });
    }
}
