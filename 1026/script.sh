
#!/bin/bash

javac E10_26_envio.java
javac E10_26_principal.java
javac E10_26_vendedor.java

mkdir Tienda
mkdir Tienda/WEB-INF/
mkdir Tienda/WEB-INF/classes/

sudo mv E10_26_envio.class Tienda/WEB-INF/classes/

sudo mv E10_26_principal.class Tienda/WEB-INF/classes/

sudo mv E10_26_vendedor.class Tienda/WEB-INF/classes/

sudo mv web.xml Tienda/WEB-INF/

cd Tienda

sudo rm Tienda.war

jar -cvf Tienda.war *

sudo rm -rf /opt/tomcat/latest/webapps/Tienda.war 

sudo cp Tienda.war /opt/tomcat/latest/webapps/


