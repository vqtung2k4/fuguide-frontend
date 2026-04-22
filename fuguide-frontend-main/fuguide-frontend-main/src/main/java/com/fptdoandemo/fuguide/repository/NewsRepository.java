package com.fptdoandemo.fuguide.repository;

import com.fptdoandemo.fuguide.model.News;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class NewsRepository {
    private static final String COLLECTION_NAME = "news";
    private final Firestore firestore;

    public NewsRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<News> findAll() {
        List<News> newsItems = new ArrayList<>();
        CollectionReference collection = firestore.collection(COLLECTION_NAME);
        ApiFuture<QuerySnapshot> future = collection.get();
        try {
            for (DocumentSnapshot doc : future.get().getDocuments()) {
                News news = doc.toObject(News.class);
                if (news != null) {
                    news.setId(doc.getId());
                    newsItems.add(news);
                }
            }
            return newsItems;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while reading news from Firebase", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to read news from Firebase", e);
        }
    }

    public List<News> findAllByOrderByPublishedDateDesc() {
        List<News> newsItems = findAll();
        newsItems.sort(
                Comparator.comparing(News::getPublishedDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
        );
        return newsItems;
    }

    public News save(News news) {
        try {
            DocumentReference docRef;
            if (news.getId() == null || news.getId().isBlank()) {
                docRef = firestore.collection(COLLECTION_NAME).document();
                news.setId(docRef.getId());
            } else {
                docRef = firestore.collection(COLLECTION_NAME).document(news.getId());
            }
            docRef.set(news).get();
            return news;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while saving news to Firebase", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to save news to Firebase", e);
        }
    }
}
