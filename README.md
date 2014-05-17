## Pilfer
"scripts" to download details about malaysian citizens. Requires a local mongodb to execute. Data will be downloaded into spr.people

## Compile
mvn compile

## Execute

Download Data For Selangor:

```bash
mvn exec:java -Dexec.mainClass="com.eriad.app.states.Selangor"
```

## States Available
* Perlis
* Kedah
* Pulau Pinang
* Perak
* Selangor
* WP Kuala Lumpur
