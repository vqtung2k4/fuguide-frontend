package com.fptdoandemo.fuguide.repository;

import com.fptdoandemo.fuguide.model.Program;
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
public class ProgramRepository {
    private static final String COLLECTION_NAME = "programs";
    private final Firestore firestore;

    public ProgramRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Program> findAll() {
        List<Program> programs = new ArrayList<>();
        CollectionReference collection = firestore.collection(COLLECTION_NAME);
        ApiFuture<QuerySnapshot> future = collection.get();
        try {
            for (DocumentSnapshot doc : future.get().getDocuments()) {
                Program program = doc.toObject(Program.class);
                if (program != null) {
                    program.setId(doc.getId());
                    programs.add(program);
                }
            }
            return programs;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while reading programs from Firebase", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to read programs from Firebase", e);
        }
    }

    public Program save(Program program) {
        try {
            DocumentReference docRef;
            if (program.getId() == null || program.getId().isBlank()) {
                docRef = firestore.collection(COLLECTION_NAME).document();
                program.setId(docRef.getId());
            } else {
                docRef = firestore.collection(COLLECTION_NAME).document(program.getId());
            }
            docRef.set(program).get();
            return program;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while saving program to Firebase", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to save program to Firebase", e);
        }
    }
}
