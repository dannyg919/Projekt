package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekt.models.Projekt;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddProjektActivity extends AppCompatActivity {
    EditText etProjektName;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_projekt);

        etProjektName = findViewById(R.id.etProjektName);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projektName = etProjektName.getText().toString();
                if (projektName.isEmpty()) {
                    Toast.makeText(AddProjektActivity.this, "Projekt name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveProjekt(projektName, currentUser);


            }
        });

    }

    private void saveProjekt(String projektName, ParseUser currentUser) {
        Projekt projekt = new Projekt();
        projekt.setName(projektName);
        projekt.setUser(currentUser);

        projekt.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(AddProjektActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                etProjektName.setText("");

                //TODO: Take user to newly created projekt
            }
        });


    }
}