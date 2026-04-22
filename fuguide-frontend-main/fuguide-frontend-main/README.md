# Fuguide (Spring Boot + Firebase Firestore)

This project is a Spring Boot web app using Thymeleaf for server-rendered UI and Firebase Firestore as the database.

## Requirements

- JDK 17
- Maven Wrapper (already included)
- Firebase project with Firestore enabled
- Firebase service-account JSON key

## Local Setup

1. Create a Firebase project and enable Firestore.
2. Generate a service-account key JSON in Firebase Console.
3. Configure app properties:
   - Copy `application.properties.example` values into `src/main/resources/application.properties`
   - Set:
     - `firebase.credentials.path`
     - `firebase.project-id`

Example:

```properties
spring.web.locale=en_US
firebase.credentials.path=C:/Users/your-user/Downloads/firebase-service-account.json
firebase.project-id=your-firebase-project-id
```

## Run

```powershell
.\mvnw.cmd spring-boot:run
```

## Test

```powershell
.\mvnw.cmd test
```

## Firebase Behavior

- Data source is Firestore collections: `programs`, `news`, `campusLife`.
- On startup, if a collection is empty, the app inserts sample data into that collection.

## Security Notes

- Never commit service-account JSON keys to Git.
- `.gitignore` includes common Firebase key filename patterns.
