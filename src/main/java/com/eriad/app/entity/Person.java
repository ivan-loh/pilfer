package com.eriad.app.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import java.util.Date;


@Entity(value = "people", noClassnameStored = true)
@Indexes({
        @Index(value = "kadPengenalan"),
        @Index(value = "nama")
})
public class Person {

    @Id public ObjectId _id;

    public String kadPengenalan;
    public String nama;
    public Date tarikhLahir;
    public String jantina;
    public String lokaliti;
    public String daerahMengundi;
    public String dun;
    public String parlimen;
    public String negeri;
    public String statusRekord;

}
