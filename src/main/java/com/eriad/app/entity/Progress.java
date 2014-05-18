package com.eriad.app.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

@Entity(value = "progress", noClassnameStored = true)
@Indexes({
        @Index(value = "processID")
})
public class Progress {

    @Id public ObjectId _id;

    @Property("pid") public String processID;
    @Property("ad")  public Date   activeDate;
    @Property("ac")  public int    activeCount;

}
