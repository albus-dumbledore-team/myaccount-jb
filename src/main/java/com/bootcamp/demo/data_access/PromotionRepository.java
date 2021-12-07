package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.Promotion;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class PromotionRepository {
    private CollectionReference getDb() {
        return FirestoreClient.getFirestore().collection("promotions");
    }

    public String addPromotion(Promotion promotion) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getDb().document(promotion.getCode());

        //use transaction to make the operation atomic
        ApiFuture<String> futureTransaction = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(docRef).get();
            if(snapshot.exists()){
                throw new Exception(String.format("A promotion with the same code {%s} already exists!",snapshot.getId()));
            }
            else {
                Transaction writeResult =transaction.set(docRef,promotion);
                return "Promotion created successfully";
            }
        });
        return futureTransaction.get();
    }

    public Optional<Promotion> getPromotion(String code) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = getDb();
        DocumentReference documentReference = collectionReference.document(code);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        if (documentSnapshot.exists())
            return Optional.ofNullable(documentSnapshot.toObject(Promotion.class));
        return Optional.empty();
    }

    public Optional<Promotion> popPromotion(String code) throws ExecutionException, InterruptedException, Exception {
        DocumentReference promotionReference = getDb().document(code);
        ApiFuture<Promotion> futureTransation = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(promotionReference).get();
            if(!snapshot.exists()){
                throw new Exception("Promotion code is invalid");
            }
            Promotion promotion = snapshot.toObject(Promotion.class);
            transaction.delete(promotionReference);
            return promotion;
        });
        Promotion result = futureTransation.get();
        if(result == null){
            return Optional.empty();
        }
        return Optional.of(result);
    }



}
