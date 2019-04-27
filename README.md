# DD4J
Java API for DuckDuckGo Instant Answers API.

Licensed under the [Apache 2.0 License](https://github.com/arudiscord/DD4J/blob/master/LICENSE).

### Installation

![Latest Version](https://api.bintray.com/packages/arudiscord/maven/DD4J/images/download.svg)

Using in Gradle:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'pw.aru.libs:DD4J:LATEST' // replace LATEST with the version above
}
```

Using in Maven:

```xml
<repositories>
  <repository>
    <id>central</id>
    <name>bintray</name>
    <url>http://jcenter.bintray.com</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>pw.aru.libs</groupId>
    <artifactId>DD4J</artifactId>
    <version>LATEST</version> <!-- replace LATEST with the version above -->
  </dependency>
</dependencies>
```

### Usage

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

### Support

Support is given on [Aru's Discord Server](https://discord.gg/URPghxg)

[![Aru's Discord Server](https://discordapp.com/api/guilds/403934661627215882/embed.png?style=banner2)](https://discord.gg/URPghxg)
