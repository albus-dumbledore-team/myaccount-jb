package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class AccountRepository implements Repo<Account> {
    public String add(final Account account) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("accounts").document(account.getUsername());
        ApiFuture<WriteResult> writeResult = docRef.set(account);
        return writeResult.get().getUpdateTime().toString();
    }

    @Override
    public boolean delete(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null.");
        }

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);

        if (documentReference != null) {
            documentReference.delete();
            return true;
        }

        return false;
    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);
        if (documentReference != null) {
            documentReference.update("password", newPassword);
            return true;
        }
        return false;
    }
}