package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.Promotion;
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
public class AccountRepository {
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

    private CollectionReference getAccountsCollection() {
        Firestore db = FirestoreClient.getFirestore();
        return db.collection("accounts");
    }

    public void update(Account account) throws ExecutionException, InterruptedException, Exception {
        CollectionReference collectionReference = this.getAccountsCollection();
        DocumentReference documentReference = collectionReference.document(account.getUsername());
        if(documentReference.get().get().exists()){
            documentReference.set(account);
        }
        else {
            throw new Exception("Account does not exist!");
        }
    }


    public void delete(String username) throws ExecutionException, InterruptedException, IllegalArgumentException {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null.");
        }

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);
        if(!documentReference.get().get().exists()){
            throw new IllegalArgumentException("Account not found");
        }
        documentReference.delete();
    }


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

    public void addPromotionToAccount(String accountId, Promotion promotion) throws ExecutionException, InterruptedException, IllegalArgumentException {
        DocumentReference accountReference = FirestoreClient.getFirestore().collection("accounts").document(accountId);
        if(!accountReference.get().get().exists()){
            throw new IllegalArgumentException("Account does not exist");
        }
        ApiFuture<WriteResult> union = accountReference.update("promotions", FieldValue.arrayUnion(promotion));
        System.out.println(union.get().toString());
    }
}