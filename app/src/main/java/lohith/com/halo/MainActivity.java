package lohith.com.halo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private TextView mTextMessage;
    private StorageReference videoRef;
    private Uri videouri;
    private String medicines , symptoms , name , age;
    private DatabaseReference databaseReference;
    private static final int request_code = 101;
    private TextView nameh , medicineh , symh , ageh;
    private String myname = "lohith";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent1 = new Intent(MainActivity.this,ContactDoctor.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent = new Intent(MainActivity.this,video.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference("Patients");
        mTextMessage = (TextView) findViewById(R.id.message);
        nameh = findViewById(R.id.nameemain);
      //  ageh = findViewById(R.id.age);
        medicineh = findViewById(R.id.medicine);
       // symh = findViewById(R.id.symptom);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long length = dataSnapshot.getChildrenCount();

                for (DataSnapshot def : dataSnapshot.getChildren()) {
                    consult oldconsult = def.getValue(consult.class);
                    medicines = oldconsult.medicines;
                    //symptoms = oldconsult.symptoms;
                   // age = oldconsult.age;
                    name = oldconsult.name;
                    Log.d( name, "onDataChange: gdsfgdsfg");


                        nameh.setText(name);
                        medicineh.setText(medicines);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}