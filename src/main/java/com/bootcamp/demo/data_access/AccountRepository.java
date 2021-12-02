package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class AccountRepository implements AbstractRepository<Account> {
    public String add(Account account) throws ExecutionException, InterruptedException {
        //checks if an account with the same username doesn't already exists and adds the new account
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("accounts").document(account.getUsername());

        //use transaction to make the operation atomic
        ApiFuture<String> futureTransaction = db.runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(docRef).get();
            if(snapshot.exists()){
                throw new Exception(String.format("An account with the same username {%s} already exists!",snapshot.getId()));
            }
            else {
                Transaction writeResult =transaction.set(docRef,account);
                return "Account created successfully";
            }
        });
        return futureTransaction.get();
    }

    @Override
    public Optional<Account> retrieve(String username) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        if (documentSnapshot.exists())
            return Optional.ofNullable(documentSnapshot.toObject(Account.class));
        return Optional.empty();
    }

    @Override
    public void delete(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null.");
        }

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);

        documentReference.delete();
    }

    @Override
    public String updatePassword(String username, String oldPassword, String newPassword) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);


        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();

        if(documentSnapshot.exists()) {
            String dbOldPassword = Objects.requireNonNull(documentSnapshot.getData()).get("password").toString();

            // checking if the provided oldPassword can generate a hash equal to the database hashed password
            if(BCrypt.checkpw(oldPassword, dbOldPassword)){
                documentReference.update("password", newPassword);
                return "Password changed successfully";
            }

            throw new Exception("Incorrect old password");
        }

        return null;
    }

    public List<Account> getAll() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("accounts").get();
        QuerySnapshot querySnapshot = query.get();
        return querySnapshot.getDocuments()
                .stream()
                .filter(QueryDocumentSnapshot::exists)
                .map(element-> element.toObject(Account.class))
                .collect(Collectors.toList());
    }
}