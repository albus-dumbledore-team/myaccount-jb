package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

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
    public String updatePassword(String username, String newPassword) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);
        ApiFuture<WriteResult> writeResult = documentReference.update("password", newPassword);
        return writeResult.get().getUpdateTime().toString();
    }
}