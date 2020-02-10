// package src;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
class Pedidos {
public HashMap<String, Integer> almacen;
public int pedidos;
public Pedidos() {
this.almacen = new HashMap<>();
this.pedidos = 0;
}
public void agregarPedido(String pedido) {
if (this.almacen.containsKey(pedido)) {
this.almacen.put(pedido, this.almacen.get(pedido) + 1);
}e
lse {
this.almacen.put(pedido, 1);
} this.pedidos++;
}
}
public class Comprar extends HttpServlet {
static PrintWriter out;
static int getIdUser(String nombre, String apellidos) {
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos",
"tomcat", "tomcat");
String query = "Select id From usuarios where nombre='"+nombre+"' and
apellido='"+apellidos+"';";
PreparedStatement pst = conn.prepareStatement(query);
ResultSet rs = pst.executeQuery();
while (rs.next()) {
out.println("Clave primaria usuario -> " + rs.getInt(1) + "<br>");
return rs.getInt(1);
}}c
atch(Exception e) {
} return -1;
}
static boolean createOrder(String nombre, String apellidos, double precio) {
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos",
"tomcat", "tomcat");
int idUser = getIdUser(nombre, apellidos);
out.println("id usuario -> " + idUser + "<br>");
String query = "Insert Into pedidos (_idusuario, precio) Values ('"+idUser+"',
'"+precio+"')";
PreparedStatement pst = conn.prepareStatement(query);
pst.execute();
out.println("pedido creado" + "<br>");
return true;
}c
atch(Exception e) {
out.println("problemas creando el pedido" + "<br>");
return false;
}}
static boolean createUser(String nombre, String apellidos) {
out.println("Creando usuario: " + nombre + " " + apellidos + "<br>");
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos",
"tomcat", "tomcat");
String query = "Insert Into usuarios (nombre, apellido) Values ('"+nombre+"',
'"+apellidos+"');";
PreparedStatement pst = conn.prepareStatement(query);
pst.execute();
out.println("usuario creado" + "<br>");
return true;
}c
atch(Exception e) {
out.println("problemas creando el usuario" + "<br>");
return false;
}}
static boolean existsUser(String nombre, String apellidos) {
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos",
"tomcat", "tomcat");
String query = "Select id From usuarios where nombre='"+nombre+"' and
apellido='"+apellidos+"';";
PreparedStatement pst = conn.prepareStatement(query);
ResultSet rs = pst.executeQuery();
return rs.next();
}c
atch(Exception e) {
out.println(e.toString());
return false;
}}
static boolean almacenarPedido(HttpServletRequest req, HttpSession sesion) {
try {
String nombre = req.getParameter("nombre");
String apellidos = req.getParameter("apellidos");
out.println(nombre + " " + apellidos + "<br>");
if (!existsUser(nombre, apellidos)) {
createUser(nombre, apellidos);
out.println("creando usuario!" + "<br>");
} out.println("creando pedido!" +
"<br>");
return createOrder(nombre, apellidos, getPrecioPedido(sesion));
}c
atch (Exception e) {
return false;
}
}
static int getPrecioPedido(HttpSession sesion) {
int precio = 0;
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos",
"tomcat", "tomcat");
PreparedStatement pst = conn.prepareStatement("Select * from productos;");
ResultSet rs = pst.executeQuery();
// Mapeo el nombre del juego seguido de su precio
HashMap<String, Double> precios = new HashMap<>();
while (rs.next()) {
precios.put(rs.getString(2), rs.getDouble(4));
}
HashMap<String, Integer> almacen = ((Pedidos)sesion.getAttribute("almacen")).almacen;
for (Map.Entry<String, Integer> entry : almacen.entrySet()) {
precio += entry.getValue() * precios.get(entry.getKey());
}
conn.close();
}c
atch (Exception e) {
}
return precio;
}
public void doGet(HttpServletRequest req, HttpServletResponse res) throws
ServletException, IOException {
out = res.getWriter();
HttpSession sesion = req.getSession(true);
out.println("<html>");
out.println("<meta>");
out.println("<title>Datos</title>");
out.println("<link rel='stylesheet' href=' " + req.getContextPath() +
"/css/formUser.css'>");
out.println("</meta>");
out.println("<body>");
out.println("<h1 class='margin'>Introduce tu cuenta de usuario</h1>");
out.println("<form action='comprar' method='POST'>");
out.println("<input type='text' name='nombre' placeholder='nombre...'></input>");
out.println("<input type='text' name='apellidos' placeholder='apellidos...'></input>");
out.println("<input type='submit' value='Realizar Pedido'></input>");
out.println("</form>");
out.println("</body>");
out.println("</html>");
}
public void doPost(HttpServletRequest req, HttpServletResponse res) throws
ServletException, IOException {
out = res.getWriter();
HttpSession sesion = req.getSession(true);
out.println("<html>");
out.println("<meta>");
out.println("<title>DATOS USUARIO</title>");
out.println("<link rel='stylesheet' href=' " + req.getContextPath() +
"/css/formUser.css'>");
out.println("</meta>");
out.println("<body>");
out.println("<a href='tienda'>Seguir Comprando</a>");
if (almacenarPedido(req, sesion)) {
out.println("<h1 class='center'>Pedido correctamente almacenado. Gracias por su
compra.</h1>");
sesion.invalidate();
}e
lse {
out.println("<h1 class='center'>Problemas al gestionar el pedido.</h1>");
}
out.println("</body>");
out.println("</html>");
}}