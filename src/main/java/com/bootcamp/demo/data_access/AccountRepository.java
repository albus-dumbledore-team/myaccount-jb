package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class AccountRepository implements Repo<Account> {
    @Override
    public Account findOne(String username) throws ExecutionException, InterruptedException {
        // return optional?
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(username);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
//        if(documentSnapshot.exists())
//            return documentSnapshot.toObject(Account.class);
        if (documentSnapshot.exists()) {
            Map<String, Object> data = documentSnapshot.getData();
            System.out.println(data);
            String name = data.get("name").toString();
            String email =  data.get("email").toString();
            String password =  data.get("password").toString();
            String phoneNumber =  data.get("phoneNumber").toString();
            String address =  data.get("address").toString();
            String dateOfBirth =  data.get("dateOfBirth").toString();

            return new Account(name, email, username, password, phoneNumber, address, dateOfBirth);
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
    public String update(Account account) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("accounts");
        DocumentReference documentReference = collectionReference.document(account.getUsername());
        ApiFuture<WriteResult> writeResult = documentReference.set(account);
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

    public List<Account> getAll() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("accounts").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Account> accountList = new ArrayList<>();
        for (QueryDocumentSnapshot q : documents) {
            if (q.exists()) {
                Account account = q.toObject(Account.class);
                accountList.add(q.toObject(Account.class));
                }
            }


        return accountList;
    }
}