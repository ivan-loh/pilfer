## Pilfer
"scripts" to download details about malaysian citizens. Requires a local mongodb to execute. Data will be downloaded into spr.people

## Requires
* java1.8
* maven
* mongodb

## States Available

| State           | State Class    |
|:----------------|:---------------|
| Perlis          | Perlis         |
| Kedah           | Kedah          |
| Pulau Pinang    | PulauPinang    |
| Perak           | Perak          |
| Selangor        | Selangor       |
| WP Kuala Lumpur | WPKualaLumpur  |
| WP Putrajaya    | WPPutrajaya    |
| Negeri Sembilan | NegeriSembilan |
| Melaka          | Melaka         |
| Johor           | Johor          |
| Pahang          | Pahang         |
| Terengganu      | Terengganu     |
| Kelantan        | Kelantan       |
| Sabah           | Sabah          |
| Sarawak         | Sarawak        |
| WP Labuan       | WPLabuan       |

## Execute

Sample execution for Selangor:

```bash
mvn exec:java -Dexec.mainClass="com.eriad.app.states.Selangor"
```

Sample execution for WP Kuala Lumpur:

```bash
mvn exec:java -Dexec.mainClass="com.eriad.app.states.WPKualaLumpur"
```