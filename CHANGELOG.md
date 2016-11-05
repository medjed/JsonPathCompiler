# Changelog

## 0.1.1 (2016-11-06)

Enhancements:

* Add some utility functions
  * Utils.escape(String) which is a short-hand for Utils.escape(String, true)
  * PropertyPathToken.getPathFragment(String property , boolean singleQuote)
  * PropertyPathToken.getPathFragment(List<String> properties, boolean singleQuote)
  * ArrayIndexOperation.toString(Integer index)
  * ArrayIndexOperation.toString(List<Integer> indexes)
  * ArraySliceOperation.toString(Integer from, Integer to)
  * ArrayPathToken.getPathFragment(Integer index)
  * ArrayPathToken.getPathFragment(List<Integer> indexes)
  * ArrayPathToken.getPathFragment(Integer from, Integer to)

## 0.1.0 (2016-11-05)

Enhancements:

* Just version up

## 0.0.13 (2016-11-05)

Enhancements:

* Just version up

## 0.0.12 (2016-10-20)

Fixes:

* Escape properties

## 0.0.11 (2016-10-19)

Enhancements:

* make PathToken#isLeaf(), isRoot(), isUpstreamDefinite() be public

## 0.0.10 (2016-10-18)

Breaking Changes:

* rename package from com.dena.analytics => io.github.medjed

## 0.0.6

Breaking changes:

* Change method name to isProbablyJsonPath from isStartWithDoller

## 0.0.5

Fixes:

* Fix javadoc generation

## 0.0.4

Features:

* Release automation
* Generate Javadoc for github pages

## 0.0.3

Breaking changes:

* Change package name and group name
