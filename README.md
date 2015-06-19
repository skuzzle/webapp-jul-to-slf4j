webapp-jul-to-slf4j
================

Little helper library allowing to enable JDK logger bridging during deployment time of a 
web application. Please make sure you understand the implications of briding the JDK 
logger that are described in the slf4j 
[documentation](http://www.slf4j.org/legacy.html#jul-to-slf4j).

## Maven Dependency

```xml
<dependency>
    <groupId>de.skuzzle.slf4j</groupId>
    <artifactId>webapp-jul-to-slf4j</artifactId>
    <version>1.7.12</version>
</dependency>
```

## Why?
The design of slf4j is all centered around deployment time flexibility. For most 
slf4j extensions it is sufficient to place a certain jar into the class path and you are 
done. However, for bridging the JDK logger to redirect it to slf4j, you currently have 
to write additional setup code to have it run properly. 

This extension is for use in web applications that are run in a Java EE 6 compliant 
container. Those containers will query the `ServiceLoader` for service providers of the 
type `ServletContainerInitializer`. This library implements such a listener to perform 
the necessary steps for redirecting the JDK logger to slf4j.

## Usage
Simply put this library into the class path of your web application. As described above,
it will then automatically be recognized by the servlet container during startup.

Log messages sent to the JDK logger are only redirected to slf4j, if the respective logger
is enabled. Besides configuring the redirect to slf4j, this library will set the log level
of the JDK root logger to `ALL` in order to have it redirect all messages to slf4j.