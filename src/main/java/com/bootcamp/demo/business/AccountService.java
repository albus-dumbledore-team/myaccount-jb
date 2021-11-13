package com.bootcamp.demo.business;

import com.bootcamp.demo.model.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class AccountService implements iService<Account>{

    public String add(final Account account) throws ExecutionException, InterruptedException {
        //todo add encryption, validate
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("accounts").document();
        ApiFuture<WriteResult> writeResult = docRef.set(account);
        return writeResult.get().getUpdateTime().toString();
    }

}
