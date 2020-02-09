#/bin/bash

# Pequenyo Script para compilar y hacer
# deploy en el tomcat instalado.

# Es necesario modificar las rutas
# para adaptarlo a las necesidades
# del sistema
# Asi como las opciones de compilador de 
# java.

WEBAPPS_FOLDER="/var/lib/tomcat9/webapps"
TOMCAT_CLASSPATH="/usr/share/tomcat9/lib/servlet-api.jar"

# Librerias de Java necesarias
JAR_MYSQL_CONNECTOR="/usr/share/java/mysql-connector-java-8.0.19.jar"
JAR_JSON_CONNECTOR="/usr/share/java/json.jar"

# Variables de JAVA
JAVA_RUNTIME_FOLDER="/usr/lib/jvm/java-11-openjdk-amd64"
JAVA_OPTS="-target 11 -cp .:$TOMCAT_CLASSPATH:$JAR_JSON_CONNECTOR"

echo " [ SANITY CHECKS ] "
test -f $JAR_MYSQL_CONNECTOR || (echo " -- No tienes el conector mysql" && exit 1)
test -f $JAR_JSON_CONNECTOR || (echo " -- No tienes la libreria json-android" && exit 1)
test -f $TOMCAT_CLASSPATH || (echo " -- No tienes la libreria de servlet" && exit 1)


# Compilacion del proyecto
echo " [ Compilando ] : Detalles"
$JAVA_RUNTIME_FOLDER/bin/javac $JAVA_OPTS src/MiServlet.java


echo " [ Preparando WAR ] : Creamos el directorio "
mkdir -p web/WEB-INF/classes/

echo " [ Preparando WAR ] : Copiando JARs Externos "
mkdir -p web/WEB-INF/lib/
cp $JAR_MYSQL_CONNECTOR  web/WEB-INF/lib/
cp $JAR_JSON_CONNECTOR web/WEB-INF/lib/

echo " [ Preparando WAR ] : Copiamos las clases "
cp src/*.class web/WEB-INF/classes/

echo " [ Building the WAR ] "
cd web
jar -cf MiServlet.war *

echo " [ Deploying ] : $WEBAPPS_FOLDER"
sudo cp MiServlet.war $WEBAPPS_FOLDER

echo " [ Cleaning ] "
rm MiServlet.war
cd ..

