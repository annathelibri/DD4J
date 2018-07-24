# DD4J
[ ![Download](https://api.bintray.com/packages/adriantodt/maven/DD4J/images/download.svg) ](https://bintray.com/adriantodt/maven/DD4J/_latestVersion)

Java API for DuckDuckGo Instant Answers API

# Adding to your project

Maven:
```xml
<dependency>
  <groupId>pw.aru.api</groupId>
  <artifactId>DD4J</artifactId>
  <version>VERSION</version>
  <type>pom</type>
</dependency>
```
Gradle:
```gradle
compile 'pw.aru.api:DD4J:VERSION'
```

You can find the latest version [here](https://bintray.com/adriantodt/maven/DD4J)

# Usage

To get started, you need an instance of `DD4J`
```java
DD4J api = new DD4J.Builder().build();
```

To make a query, you need to use the `query(String)` method

```java
InstantAnswer answer = api.query("duckduckgo").execute();
System.out.println("About DuckDuckGo: " + answer.getAbstractText());
```


Additional info can be found on the javadocs for the DD4J class and [on the official API docs](https://duckduckgo.com/api).
