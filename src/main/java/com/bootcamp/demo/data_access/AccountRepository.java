package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

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

    public Account findOne(String username) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = this.getAccountsCollection();
        DocumentReference documentReference = collectionReference.document(username);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();

        return documentSnapshot.toObject(Account.class);
    }

    public Account update(Account account) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = this.getAccountsCollection();

        DocumentReference documentReference = collectionReference.document(account.getUsername());
        documentReference.set(account);

        return this.findOne(account.getUsername());
    }

    private CollectionReference getAccountsCollection() {
        Firestore db = FirestoreClient.getFirestore();
        return db.collection("accounts");
    }


    public void delete(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null.");
        }

        CollectionReference collectionReference = this.getAccountsCollection();
        DocumentReference documentReference = collectionReference.document(username);

        documentReference.delete();
    }
}
