Overview
========

Liber Abaci is a demonstration project that uses the Spring Framework to calculate the Fibonacci sequence.  The user of
the service can submit a GET request to the /generate URL with a parameter named "n" which represent the number of
Fibonacci numbers that should be returned starting from 0.  The return values are returned in a JSON encoded format
For example the following GET request:

    http://localhost:8080/generate?n=5

Results in the following JSON document:

    [0,1,1,2,3]

Setup
=====

Liber Abaci is a Java application and should run on most operating systems supported by Java.  This guide is developed
using Mac OS X but with a little reading will almost certainly work on other system.  In order to setup and
run the application you will need to perform the following installation steps:

* [Install Java 8](http://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
* [Install Apache Maven](https://maven.apache.org/install.html)
* [Install Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

Once the required software is installed you will need to download the source code to do this clone the applicaiton using
git:

    git clone https://github.com/GrandPubba/LiberAbaci.git
    
When this command is complete change directories into the project:

    cd LiberAbaci
    
Running the Application
=======================

To run the application use Maven to download all project dependencies, compile the source code and package it into
an executable.  Use the following command:

    mvn spring-boot:run
    
When completed you will see the message:

    2015-10-15 19:44:59.644  INFO 14721 --- [berAbaci.main()] liber.abaci.LiberAbaci                   : Started LiberAbaci in 7.119 seconds (JVM running for 20.826)
    2015-10-15 19:45:38.873  INFO 14721 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring FrameworkServlet 'dispatcherServlet'
    2015-10-15 19:45:38.873  INFO 14721 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization started
    2015-10-15 19:45:38.910  INFO 14721 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization completed in 37 ms
    
You should now be able to use the application using any web browser.  The following will use the command line web browser
curl.

    curl -D - http://localhost:8080/generate?n=5
    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Fri, 16 Oct 2015 00:14:04 GMT
    
    [0,1,1,2,3]

Using the Application
=====================

This simple application only has one URL and the only variable "n" can be modified to be a number between 0 and 93.
If you pass a number for n less than 0, greater than 93 you will receive the response code 422 ( Unprocessable Entity ).
If you pass a value that is not a valid integer you will receive the response code 400 ( Bad Request ).  Any additional
parameters passed to the application will be ignored.  Below are a few example requests and the response they will 
return:


    curl -D - http://localhost:8080/generate?n=0
    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Fri, 16 Oct 2015 00:13:18 GMT
    
    []

 

    curl -D - http://localhost:8080/generate?n=3
    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Fri, 16 Oct 2015 00:12:56 GMT
    
    [0,1,1]

 

    curl -D - http://localhost:8080/generate?n=-1
    HTTP/1.1 422 Unprocessable Entity
    Server: Apache-Coyote/1.1
    Content-Length: 0
    Date: Fri, 16 Oct 2015 00:14:39 GMT

 

    curl -D - http://localhost:8080/generate?n=A
    HTTP/1.1 400 Bad Request
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Fri, 16 Oct 2015 00:15:11 GMT
    Connection: close
    
    {"timestamp":1444954511062,"status":400,"error":"Bad Request","exception":"org.springframework.beans.TypeMismatchException","message":"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; nested exception is java.lang.NumberFormatException: For input string: \"A\"","path":"/generate"}

Troubleshooting
===============

## Unsupported major.minor version 51.0 

If you recieve the following error message when executing "mvn spring-boot:run":

    [ERROR] Failed to execute goal org.springframework.boot:spring-boot-maven-plugin:1.2.6.RELEASE:run (default-cli) on project liber-abaci: An exception occured while running. null: InvocationTargetException: javax/annotation/ManagedBean : Unsupported major.minor version 51.0 -> [Help 1]
    
You are likely running JDK 6.  This application requires JDK 8 to fix this set the JAVA_HOME environment variable:

    export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_05.jdk/Contents/Home/
    
Support
=======

If you have trouble using this project please create an issue in GitHub.
    

What's With The Name?
=====================

Every application needs a name.  So after a bit of cursory research I choose Liber Abaci.  
[According to Wikipedia](https://en.wikipedia.org/wiki/Fibonacci#Fibonacci_sequence) Liber Abaci was the title of the 
first western book written by Leonardo Bonacci also known as Fibonacci in which the Fibonacci sequence was published.


