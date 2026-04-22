package com.fptdoandemo.fuguide.repository;

import com.fptdoandemo.fuguide.model.CampusLife;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class CampusLifeRepository {
    private static final String COLLECTION_NAME = "campusLife";
    private final Firestore firestore;

    public CampusLifeRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<CampusLife> findAll() {
        List<CampusLife> features = new ArrayList<>();
        CollectionReference collection = firestore.collection(COLLECTION_NAME);
        ApiFuture<QuerySnapshot> future = collection.get();
        try {
            for (DocumentSnapshot doc : future.get().getDocuments()) {
                CampusLife campusLife = doc.toObject(CampusLife.class);
                if (campusLife != null) {
                    campusLife.setId(doc.getId());
                    features.add(campusLife);
                }
            }
            return features;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while reading campus life from Firebase", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to read campus life from Firebase", e);
        }
    }

    public CampusLife save(CampusLife campusLife) {
        try {
            DocumentReference docRef;
            if (campusLife.getId() == null || campusLife.getId().isBlank()) {
                docRef = firestore.collection(COLLECTION_NAME).document();
                campusLife.setId(docRef.getId());
            } else {
                docRef = firestore.collection(COLLECTION_NAME).document(campusLife.getId());
            }
            docRef.set(campusLife).get();
            return campusLife;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while saving campus life to Firebase", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to save campus life to Firebase", e);
        }
    }
}
