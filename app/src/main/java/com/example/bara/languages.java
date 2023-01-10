package com.example.bara;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class languages {
    private String[] bahasaFrom = {
            "From", "", "", ""
    };
    private String[] bahasaTo = {
            "To", "", "", ""
    };
    private int idxFrom;
    private int idxTo;

    public String[] getBahasaFrom() {
        return bahasaFrom;
    }

    public String[] getBahasaTo() {
        return bahasaTo;
    }

    public int getIdxFrom() {
        return idxFrom;
    }

    public int getIdxTo() {
        return idxTo;
    }

    public void setIdxFrom(int idxFrom) {
        this.idxFrom = idxFrom;
    }

    public void setIdxTo(int idxTo) {
        this.idxTo = idxTo;
    }
}
