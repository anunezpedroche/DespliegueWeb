
#!/bin/bash

javac E10_27_envio.java
javac E10_27_principal.java
javac E10_27_vendedor.java
javac E10_27_compras.java

mkdir Tienda
mkdir Tienda/WEB-INF/
mkdir Tienda/WEB-INF/classes/

sudo mv E10_27_envio.class Tienda/WEB-INF/classes/

sudo mv E10_27_principal.class Tienda/WEB-INF/classes/

sudo mv E10_27_vendedor.class Tienda/WEB-INF/classes/

sudo mv E10_27_compras.class Tienda/WEB-INF/classes/

sudo mv web.xml Tienda/WEB-INF/

cd Tienda

sudo rm Tienda.war

jar -cvf Tienda.war *

sudo rm -rf /opt/tomcat/latest/webapps/Tienda.war 

sudo cp Tienda.war /opt/tomcat/latest/webapps/


