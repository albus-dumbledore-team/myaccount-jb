package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class AccountRepository implements Repo<Account> {
    @Override
    public Account findOne(String username) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();

        if (documentSnapshot.exists()) {
            Map<String, Object> data = documentSnapshot.getData();
            System.out.println(data);
            String name = (String) data.get("name");
            String email = (String) data.get("email");
            String password = (String) data.get("password");
            String phoneNumber = (String) data.get("phoneNumber");
            String address = (String) data.get("address");
            String dateOfBirth = (String) data.get("dateOfBirth");

            Account account = new Account(name, email, username, password, phoneNumber, address, dateOfBirth);

            return account;
        }

        return null;
    }

    public String add(Account account) throws ExecutionException, InterruptedException {
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
}