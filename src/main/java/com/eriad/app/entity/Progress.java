package com.eriad.app.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import java.util.Date;

@Entity(value = "progress", noClassnameStored = true)
@Indexes({
        @Index(value = "generatorID")
})
public class Progress {

    @Id public ObjectId _id;

    public String generatorID;
    public Date   activeDate;
    public int    activeCount;

}
