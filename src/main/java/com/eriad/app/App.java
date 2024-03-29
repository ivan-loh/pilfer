package com.eriad.app;

import com.eriad.app.entity.Person;
import com.eriad.app.entity.Progress;
import com.eriad.app.stuff.ICGenerator;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

public class App {

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String ORIGIN       = "http://daftarj.spr.gov.my";
    public static final String REFERER      = "http://daftarj.spr.gov.my/DAFTARJ/DaftarjBM.aspx";
    public static final String HOST         = "daftarj.spr.gov.my";
    public static final String USER_AGENT   = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.137 Safari/537.36";
    public static final String URL          = "http://daftarj.spr.gov.my/DAFTARJ/DaftarjBM.aspx";


    private final Logger            LOG              = LoggerFactory.getLogger(this.getClass() + "::" + this.toString());
    private final DateTimeFormatter FORMAT           = DateTimeFormat.forPattern("yyMMdd");

    private final Datastore         ds;
    private       String            viewState;
    private final Class<?>          stateClass;
    private       String            eventValidation;

    private App(Class<?> stateClass, ServerAddress host, String viewState, String eventValidation) {

        this.stateClass      = stateClass;
        this.viewState       = viewState;
        this.eventValidation = eventValidation;

        /**
         * DB Stuff
         */

        MongoClient client = new MongoClient(host);
        Morphia morphia    = new Morphia();
        this.ds            = morphia.createDatastore(client, "spr");

        morphia.mapPackage("com.eriad.app.entity");
        this.ds.ensureIndexes();
    }

    private boolean _get(String ic) {

        LOG.trace("Trying:    " + ic);

        Document doc;

        try {
            Connection.Response response = Jsoup.connect(URL)
                                                .userAgent(USER_AGENT)
                                                .method(Connection.Method.POST)

                                                        // Header
                                                .header("Content-Type", CONTENT_TYPE)
                                                .header("Origin",       ORIGIN)
                                                .header("Referer",      REFERER)
                                                .header("Host",         HOST)

                                                        // Form Data
                                                .data("__VIEWSTATE",       viewState)
                                                .data("__EVENTVALIDATION", eventValidation)
                                                .data("Semak",             "Semak")
                                                .data("txtIC",             ic)

                                                .execute();

            doc = response.parse();
        } catch (Exception e) {
            LOG.trace("Probably a Connection Problem");
            LOG.trace(e.getMessage(), e);
            return false;
        }

        viewState       = doc.getElementById("__VIEWSTATE").val();
        eventValidation = doc.getElementById("__EVENTVALIDATION").val();


        /**
         * Persist
         */
        Person p = new Person();

        try {

            p.kadPengenalan  = doc.getElementById("LabelIC").text();
            p.nama           = doc.getElementById("Labelnama").text();
            p.jantina        = doc.getElementById("Labeljantina").text();
            p.lokaliti       = doc.getElementById("Labellokaliti").text();
            p.daerahMengundi = doc.getElementById("Labeldm").text();
            p.dun            = doc.getElementById("Labeldun").text();
            p.parlimen       = doc.getElementById("Labelpar").text();
            p.negeri         = doc.getElementById("Labelnegeri").text();
            p.statusRekord   = doc.getElementById("LABELSTATUSDPI").text();
            p.tarikhLahir    = FORMAT.parseDateTime(p.kadPengenalan.substring(0, 6)).toDate();

        } catch (Exception e) {
            LOG.trace("Most likely the record does not exist");
            LOG.trace(e.getMessage(), e);
            return true;
        }

        Query<Person> query          = ds.createQuery(Person.class).field("kadPengenalan").equal(p.kadPengenalan);
        UpdateOperations<Person> ops = ds.createUpdateOperations(Person.class).set("kadPengenalan",  p.kadPengenalan)
                                                                              .set("nama", p.nama)
                                                                              .set("tarikhLahir", p.tarikhLahir)
                                                                              .set("jantina", p.jantina)
                                                                              .set("lokaliti", p.lokaliti)
                                                                              .set("daerahMengundi", p.daerahMengundi)
                                                                              .set("dun", p.dun)
                                                                              .set("parlimen", p.parlimen)
                                                                              .set("negeri", p.negeri)
                                                                              .set("statusRekord", p.statusRekord);
                                       ds.update(query, ops, true);

        LOG.trace("Processed: " + ic);
        return true;
    }

    public final void process(ICGenerator generator) {

        LOG.trace("Begin Processing");
        LOG.trace("State:     " + stateClass.getName());
        LOG.trace("Year:      " + generator.getYear());
        LOG.trace("StateCode: " + generator.getStateCode());


        String generatorID = generator.getGeneratorId();
        String processID   = stateClass.getName() + ":" + generatorID;


        Query<Progress> query    = ds.createQuery(Progress.class).field("processID").equal(processID);
        Progress currentProgress = query.get();
        if (currentProgress != null) {
            generator.moveTo(currentProgress.activeDate, currentProgress.activeCount);
        }


        while (generator.hasNext()) {

            Date   activeDate  = generator.getActiveDate();
            int    activeCount = generator.getActiveCount();
            String ic          = generator.next();

            LOG.trace("generatorID: " + generatorID);
            LOG.trace("processing:  " + ic);
            LOG.trace("activeDate:  " + activeDate);
            LOG.trace("count:       " + activeCount);

            boolean processed = false;
            while (!processed) { processed = _get(ic); }

            Query<Progress>            q   = ds.createQuery(Progress.class).field("processID").equal(processID);
            UpdateOperations<Progress> ops = ds.createUpdateOperations(Progress.class).set("activeDate", activeDate)
                                                                                      .set("activeCount", activeCount);
                                             ds.update(q, ops, true);
        }

    }

    public static App createApp(Class<?> stateClass) {

        Document doc;
        try {
            Connection.Response response = Jsoup.connect(URL)
                                                .userAgent(USER_AGENT)
                                                .method(Connection.Method.GET)
                                                .execute();
            doc = response.parse();

        } catch (IOException e) {
            e.printStackTrace();
            return createApp(stateClass);
        }

        try {

            String viewState       = doc.getElementById("__VIEWSTATE").val();
            String eventValidation = doc.getElementById("__EVENTVALIDATION").val();

            return new App(stateClass, new ServerAddress("localhost"), viewState, eventValidation);

        } catch (UnknownHostException  | NullPointerException e) {
            e.printStackTrace();
            return createApp(stateClass);
        }

    }

}
