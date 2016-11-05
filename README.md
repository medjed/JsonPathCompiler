# JsonPathCompiler

[![Build Status](https://travis-ci.org/medjed/JsonPathCompiler.svg?branch=master)](https://travis-ci.org/medjed/JsonPathCompiler)[ ![Download](https://api.bintray.com/packages/medjed/maven/JsonPathCompiler/images/download.svg) ](https://bintray.com/medjed/maven/JsonPathCompiler/_latestVersion)

Porting from https://github.com/jayway/JsonPath equivalent version 2.2.0, latest commit is 4dc0ca9

Cut out a PathCompiler.

## Download

Download [JAR](https://bintray.com/medjed/maven/JsonPathCompiler) from Bintray,
or write Gradle dependency as follows.

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'io.github.medjed:JsonPathCompiler:0.0.4'
}
```

## Usage

```java
Path compiledPath = PathCompiler.compile("$.aaa.bbb");
PathToken parts = compiledPath.getRoot();
StringBuilder partialPath = new StringBuilder("$");
while (! parts.isLeaf()) {
    parts = parts.next(); // first next() skips "$"
    partialPath.append(parts.getPathFragment());
}
System.out.println(partialPath.toString()); //=> $['aaa']['bbb']
```

```java
String jsonPath = new StringBuilder("$['").append(expressions.Utils.escape("foo.bar", true)).append("']").toString();
System.out.println(jsonPath); //=> $['foo.bar']
```

More details, Please read a [Javadoc](https://medjed.github.io/JsonPathCompiler/)

## Development

Run test:

```
$ ./gradlew test
```

Run test with coverage reports:

```
$ ./gradlew test jacocoTestReport
```

open build/reports/jacoco/test/html/index.html

## Release

Add gradle.properties to your $homeDir/.gradle/

```gradle.properties
bintray_user=your_bintray_user
bintray_apikey=your_bintray_apikey
```

Run release:

```
$ ./gradlew release
```
