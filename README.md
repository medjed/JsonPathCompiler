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
    compile 'io.github.medjed:JsonPathCompiler:0.1.+'
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
partialPath.toString(); //=> $['aaa']['bbb']
```

```java
Path compiledPath = PathCompiler.compile("$.aaa[0].bbb");
compiledPath.toString(); //=> $['aaa'][0]['bbb']
compiledPath.getParentPath(); //=> $['aaa'][0]
compiledPath.getTailPath(); //=> ['bbb']
```

```java
new StringBuilder("['").append(Utils.escape("foo.bar", true)).append("']").toString();
//=> ['foo.bar']
PropertyPathToken.getPathFragment("foo.bar", true);
//=> ['foo.bar']
ArrayPathToken.getPathFragment(0);
//=> [0]
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

Add gradle.properties to your $homeDir/.gradle/. Java8 is required for `bintrayUpload`.

```gradle.properties
bintray_user=your_bintray_user
bintray_apikey=your_bintray_apikey
org.gradle.java.home=/path/to/java8/home
```

Run release:

```
$ ./gradlew release
```
