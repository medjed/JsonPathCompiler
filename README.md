# JsonPathCompiler

[![Build Status](https://travis-ci.org/kysnm/JsonPathCompiler.svg?branch=master)](https://travis-ci.org/kysnm/JsonPathCompiler)[ ![Download](https://api.bintray.com/packages/kysnm/maven/JsonPathCompiler/images/download.svg) ](https://bintray.com/kysnm/maven/JsonPathCompiler/_latestVersion)

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
    compile 'com.dena.analytics:JsonPathCompiler:0.0.4'
}
```

## Usage

Path path = PathCompiler.compile("$.aaa.bbb")

More details, Please read a [Javadoc](https://kysnm.github.io/JsonPathCompiler/)

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

```gradle properties
bintray_user=your_bintray_user
bintray_apikey=your_bintray_apikey
bintray_gpg_password=your_bintray_password
```

Run release:

```
$ ./gradlew release
```
