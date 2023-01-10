package com.example.bara;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner fromSpinner, toSpinner;
    private TextInputEditText sourceText;
    private MaterialButton translateBtn;
    private TextView translateTV;
    private ImageView swapBtn;

    private static final int REQUEST_PERMISSION_CODE = 1;

    textBox text = new textBox();
    dictionary kamus = new dictionary();
    languages bahasa = new languages();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        FirebaseFirestore.getInstance().collection("languages")
        .document("language").get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> temp = (List<String>) document.get("list");
                        for (int i = 0; i < temp.size(); i++) {
                            bahasa.getBahasaFrom()[i+1] = temp.get(i);
                            bahasa.getBahasaTo()[i+1] = temp.get(i);
                        }
                    } else {

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        fromSpinner = findViewById(R.id.idFromSpinner);
        toSpinner = findViewById(R.id.idToSpinner);
        sourceText = findViewById(R.id.idEditSource);
        translateBtn = findViewById(R.id.idBtnTranslation);
        translateTV = findViewById(R.id.idTranslatedTV);
        swapBtn = findViewById(R.id.idSwap);
        swapBtn.setClickable(true);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kamus.setBahasaFromID(kamus.getBahasaID(bahasa.getBahasaFrom()[i]));
                bahasa.setIdxFrom(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter fromAdapter = new ArrayAdapter(this, R.layout.spinner_item, bahasa.getBahasaFrom());
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kamus.setBahasaToID(kamus.getBahasaID(bahasa.getBahasaTo()[i]));
                bahasa.setIdxTo(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter toAdapter = new ArrayAdapter(this, R.layout.spinner_item, bahasa.getBahasaTo());
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);

        swapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromSpinner.setSelection(bahasa.getIdxTo());
                toSpinner.setSelection(bahasa.getIdxFrom());
            }
        });

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translateTV.setVisibility(View.VISIBLE);
                translateTV.setText("");
                if (sourceText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter text to translate", Toast.LENGTH_SHORT).show();
                } else if (kamus.getBahasaFromID() == "empty") {
                    Toast.makeText(MainActivity.this, "Please select source language", Toast.LENGTH_SHORT).show();
                } else if (kamus.getBahasaToID() == "empty") {
                    Toast.makeText(MainActivity.this, "Please select the language to make translation", Toast.LENGTH_SHORT).show();
                } else {
                    text.input(sourceText.getText().toString());
                    translateTV.setText("Translating...");
                    kamus.setKalimat(text.getKata());
                    translateTV.setText(kamus.translate());
                }
            }
        });
    }
}