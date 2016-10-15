package com.example.nhan.keephealthyver2.database;

import com.example.nhan.keephealthyver2.models.BreathRealmObject;
import com.example.nhan.keephealthyver2.models.PhysicalRealmObject;
import com.example.nhan.keephealthyver2.models.StringRealmObject;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Nhan on 10/14/2016.
 */

public class RealmHandler {
    private static RealmHandler instance;
    private Realm realm;
    private RealmHandler(){
        this.realm = Realm.getDefaultInstance();
    }

    public static RealmHandler getInstance() {
        if (instance == null)
            instance = new RealmHandler();
        return instance;
    }

    public void setFavoriteOfBreathObjectInRealm(BreathRealmObject genres, Boolean favorite){
        realm.beginTransaction();
        genres.setFavorite(favorite);
        realm.commitTransaction();
    }

    public void addPhysicalObjectToRealm(PhysicalRealmObject genres){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(genres);
        realm.commitTransaction();
    }

    public List<PhysicalRealmObject> getListPhysicalObjectFromRealm(){
        return realm.where(PhysicalRealmObject.class).findAll();
    }

    public void addBreathObjectToRealm(BreathRealmObject genres){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(genres);
        realm.commitTransaction();
    }

    public List<BreathRealmObject> getListBreathObjectFromRealm(){
        return realm.where(BreathRealmObject.class).findAll();
    }

    public List<BreathRealmObject> getListGenresFavoriteFromRealm(){
        return realm.where(BreathRealmObject.class).equalTo("favorite",true).findAll();
    }

    public BreathRealmObject getOneBreathObjectFromRealm(String name){
        return realm.where(BreathRealmObject.class).equalTo("name", name).findFirst();
    }

    public void clearDataInRealm(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void addStringObjectIntoBreathObject(BreathRealmObject breathRealmObject, StringRealmObject stringRealmObject){
        realm.beginTransaction();
        breathRealmObject.getListStringGuide().add(stringRealmObject);
        realm.copyToRealm(breathRealmObject);
        realm.commitTransaction();
    }

}
