# Log file processing in Java
Log file processing 

## Dependencies

- Java JDK8
- Maven 3.6.3
- [HSQLDB 2.5.1](http://hsqldb.org/)

## Usage

### Building

```bash
mvn clean package
```

The generated log-parser-1.0-jar-with-dependencies.jar file waives the need of installing lib dependencies and configuring classpath.  

### Running

```bash
java -jar log-parser-1.0-jar-with-dependencies.jar logfile.txt
```