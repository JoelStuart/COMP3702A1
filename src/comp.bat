md build\classes
javac -sourcepath src -d build\classes src\com\company\NavApp.java
java -cp build\classes com.company.NavApp.java
md build\jar
jar cfm build\jar\NavApp.jar myManifest -C build\classes .
java -jar build\jar\Navapp.jar