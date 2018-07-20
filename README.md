# creditcard-store-app
Import it as a maven project with Java 8 compiler.
Run as Spring boot application.
Swagger is path is http://localhost:8080/swagger-ui.html and http://localhost:8080/store/credit/v1/api-docs

Implemented APIs
1. Add new card
2. Charge card

The code is running with in-memory mongo db.
Please safely ignore below error in the logs which you may see while the application is starting up. It's a mongodb jar issue.
java.lang.NoSuchFieldException: handle
	at java.base/java.lang.Class.getDeclaredField(Class.java:2370)
	at de.flapdoodle.embed.process.runtime.Processes.windowsProcessId(Processes.java:109)
 
Unit test cases written only for crtitical classes are passing.
