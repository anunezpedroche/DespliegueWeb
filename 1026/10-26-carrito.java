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
} else {
this.almacen.put(pedido, 1);
} this.pedidos++;
}}
public class Carrito extends HttpServlet {
static HashMap<String, Double> getGamePrices(PrintWriter out) {
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos",
"tomcat", "tomcat");
PreparedStatement pst = conn.prepareStatement("Select * from productos;");
ResultSet rs = pst.executeQuery();
HashMap<String, Double> precios = new HashMap<>();
while (rs.next()) {
precios.put(rs.getString(2), rs.getDouble(4));
}
// for (Map.Entry<String, Double> entry : precios.entrySet()) {
// out.println(entry.getKey() + " -> " + entry.getValue() + "<br>");
// }
conn.close();
return precios;
} catch (Exception e) {
return null;
}}
static Double getPrecioPedido(HashMap<String, Integer> pedidos, HashMap<String,
Double> precios) {
Double precio = 0.0;
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos",
"tomcat", "tomcat");
PreparedStatement pst = conn.prepareStatement("Select * from productos;");
ResultSet rs = pst.executeQuery();
for (Map.Entry<String, Integer> entry : pedidos.entrySet()) {
precio += (entry.getValue() * precios.get(entry.getKey()));
}
conn.close();
} catch (Exception e) {
}
return precio;
}
public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws
ServletException, IOException {
PrintWriter salida = respuesta.getWriter();
HttpSession misesion = peticion.getSession(true);
boolean hayProductos = misesion.getAttribute("almacen") != null;
HashMap<String, Double> precios = getGamePrices(salida);
salida.println("<!DOCTYPE html>");
salida.println("<html><head>");
salida.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
salida.println("<title> Carrito </title></head>");
salida.println(
"<link rel=\"stylesheet\" type=\"text/css\" href=\"//fonts.googleapis.com/css?
family=Mate+SC\" />");  
salida.println("<link rel=\"stylesheet\" href=\"" + peticion.getContextPath()
+ "/css/tienda.css\" type=\"text/css\">");
salida.println("<body>");
salida.println("<header>");
salida.println("<h1> CARRITO </h1>");
salida.println("<a class=\"enlaceDetalles\" href=\"tienda\"> Seguir Comprando </a>");
salida.println("</header");
salida.println("<main>");
salida.println("<div class='wrapPedidos'>");
if (!hayProductos) {
salida.println("<div class='producto'>");
salida.println("<h3>El carrito se encuentra vacio</h3>");
salida.println("</div>");
}
else {
HashMap<String, Integer> almacen = ((Pedidos)
misesion.getAttribute("almacen")).almacen;
for (Map.Entry<String, Integer> entry : almacen.entrySet()) {
salida.println("<div class='producto'>");
salida.println("<p class='nombreProducto'>" + entry.getKey() + "</p>");
salida.println("<img src='" + peticion.getContextPath() + "/imgs/" + entry.getKey() +
".png'>");
salida.println("<p class='cantidaProducto'>x" + entry.getValue() + "</p>");
salida.println("<p> " + precios.get(entry.getKey()) + "$</p>");
salida.println("</div>");
}
}
if (hayProductos) {
Double precio = getPrecioPedido(((Pedidos) misesion.getAttribute("almacen")).almacen,
precios);
salida.println("<div class='wrapPagar'>");
salida.println("<p>Precio: " + precio + "$</p>");
salida.println("<a href='comprar'>Realizar Pedido</a>");
salida.println("</div>");
}
salida.println("</div>");
salida.println("</main>");
salida.println("</body></html>");
} }