package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.PromotionType;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;

public class PromotionRepository {
    private CollectionReference getDb() {
        return FirestoreClient.getFirestore().collection("promotions");
    }

    void addPromotionType(PromotionType promotionType){
        getDb().document("promotionType").collection("ids").add(promotionType);
    }

    PromotionType getPromotionType(String id, String promotionTypeId) throws ExecutionException, InterruptedException {
        return getDb().document(promotionTypeId)
                .collection("ids")
                .document(id)
                .get()
                .get()
                .toObject(PromotionType.class);
    }

}
