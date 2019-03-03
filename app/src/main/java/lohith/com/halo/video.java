package lohith.com.halo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class video extends AppCompatActivity {
    private TextView mTextMessage;
    private StorageReference videoRef;
    private Uri videouri;
    private static final int request_code = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capturevideo);
        videoRef = FirebaseStorage.getInstance().getReference().child("/videos/user1");

        mTextMessage = (TextView) findViewById(R.id.message);
    }

    public void upload(View view) {
        if (videouri != null) {
            UploadTask uploadTask = videoRef.putFile(videouri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(video.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(video.this, "Upload complete", Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    updateProgress(taskSnapshot);
                }
            });
        } else {
            Toast.makeText(video.this, "Nothing to upload", Toast.LENGTH_LONG).show();
        }
    }
    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot) {
        long fileSize = taskSnapshot.getTotalByteCount();
        long uploadBytes = taskSnapshot.getBytesTransferred();
        long progress = (100 * uploadBytes) /fileSize;

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbar);
        progressBar.setProgress((int) progress);
    }

    public void record(View view){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent,request_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        videouri = data.getData();
        if(requestCode == request_code ){
            if(requestCode == RESULT_OK){
                Toast.makeText(video.this,"Video saved to" + videouri,Toast.LENGTH_LONG).show();
            }
            else if( resultCode == RESULT_CANCELED){
                Toast.makeText(video.this,"Video recording cancelled",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(video.this,"Failed to capture video",Toast.LENGTH_LONG).show();
        }
    }
}
