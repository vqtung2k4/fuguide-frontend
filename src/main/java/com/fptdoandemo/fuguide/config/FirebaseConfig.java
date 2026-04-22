package com.fptdoandemo.fuguide.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials.path:}")
    private String firebaseCredentialsPath;

    @Value("${firebase.project-id:}")
    private String firebaseProjectId;

    @Bean
    public Firestore firestore() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            GoogleCredentials credentials = resolveCredentials();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setProjectId(resolveProjectId())
                    .build();
            FirebaseApp.initializeApp(options);
        }
        return FirestoreClient.getFirestore();
    }

    private GoogleCredentials resolveCredentials() throws IOException {
        if (StringUtils.hasText(firebaseCredentialsPath)) {
            String normalizedPath = normalizePath(firebaseCredentialsPath);
            try (InputStream serviceAccountStream = new FileInputStream(normalizedPath)) {
                return GoogleCredentials.fromStream(serviceAccountStream);
            }
        }

        String credentialsFromEnv = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (StringUtils.hasText(credentialsFromEnv)) {
            String normalizedPath = normalizePath(credentialsFromEnv);
            try (InputStream serviceAccountStream = new FileInputStream(normalizedPath)) {
                return GoogleCredentials.fromStream(serviceAccountStream);
            }
        }

        throw new IOException(
                "Firebase credentials not found. Set firebase.credentials.path in application.properties " +
                        "or set GOOGLE_APPLICATION_CREDENTIALS environment variable to your service-account JSON path."
        );
    }

    private String resolveProjectId() {
        if (StringUtils.hasText(firebaseProjectId)) {
            return firebaseProjectId;
        }
        String projectIdFromEnv = System.getenv("FIREBASE_PROJECT_ID");
        return StringUtils.hasText(projectIdFromEnv) ? projectIdFromEnv : null;
    }

    private String normalizePath(String rawPath) {
        String path = rawPath.trim();
        if (path.length() >= 2 && path.startsWith("\"") && path.endsWith("\"")) {
            path = path.substring(1, path.length() - 1);
        }
        return path;
    }
}
