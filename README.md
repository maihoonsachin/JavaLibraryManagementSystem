Library Management System (Servlets + JSP + XML)
A Library Book Management System built for Eclipse + Tomcat 10.1 using jakarta.servlet.*. Data is persisted in XML files (no SQL DB). Targets JDK 17.

Tech Stack
Java 17
Apache Tomcat 10.1 (Jakarta EE 10)
Servlets + JSP + JSTL
XML (DOM) for persistence
Bootstrap 5 for UI
Project Structure
src/com/models — POJOs (Book, User, IssueRecord)
src/com/dao — DAOs for XML read/write
src/com/servlets — Controllers
src/com/filters — AuthenticationFilter
src/com/utils — XMLUtils
WebContent/jsp — JSP pages
WebContent/WEB-INF/web.xml — deployment descriptor
WebContent/WEB-INF/data — XML data files (books.xml, users.xml, issuedBooks.xml)
Setup (Eclipse + Tomcat 10.1)
Install JDK 17 and set as default in Eclipse.
Add Apache Tomcat 10.1 to Eclipse Servers.
Create a Dynamic Web Project: JavaProject2 (Target runtime: Tomcat 10.1).
Ensure Servlet API is jakarta.servlet.* (Tomcat 10.1 provides it; do not add legacy javax.* jars).
Import this folder as an existing project, or copy src and WebContent into a new Dynamic Web Project named JavaProject2.
Ensure XML data files are under WebContent/WEB-INF/data.
Run
Deploy to Tomcat inside Eclipse.
Visit http://localhost:8080/JavaProject2
Sample users:
Librarian: admin / admin123
Student: student / stud123
Next Steps
Modify fine rate or loan period in IssueDAO if needed.
Expand JSPs for richer UX.
Add input validation and better error handling as desired.
