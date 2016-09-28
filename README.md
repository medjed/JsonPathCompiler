# JsonPathCompiler

[![Build Status](https://travis-ci.org/kysnm/JsonPathCompiler.svg?branch=master)](https://travis-ci.org/kysnm/JsonPathCompiler)[ ![Download](https://api.bintray.com/packages/kysnm/maven/JsonPathCompiler/images/download.svg) ](https://bintray.com/kysnm/maven/JsonPathCompiler/_latestVersion)[![Javadocs](http://javadoc.io/badge/com.github.kysnm/JsonPathCompiler.svg)](http://javadoc.io/doc/com.github.kysnm/JsonPathCompiler)

Porting from https://github.com/jayway/JsonPath equivalent version 2.2.0, latest commit is 4dc0ca9

Cut out a PathCompiler.

## Download

Download [JAR](https://bintray.com/kysnm/maven/JsonPathCompiler) from Bintray,
or write Gradle dependency as follows.

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.github.kysnm:JsonPathCompiler:0.0.3'
}
```

## Usage

Path path = PathCompiler.compile("$.aaa.bbb")

More details, Please read a JavaDoc

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
