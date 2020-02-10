import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
public class Vendedor extends HttpServlet {
public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws
ServletException, IOException {
PrintWriter salida = respuesta.getWriter();
HttpSession misesion = peticion.getSession(true);
String usuario = peticion.getParameter("usuario");
String password = peticion.getParameter("password");
if (!usuario.equals("admin") && !password.equals("admin")) {
respuesta.sendRedirect("tienda");
return;
}
salida.println("<!DOCTYPE html>");
salida.println("<html><head>");
salida.println("<title> VENDEDOR </title></head>");
salida.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"//fonts.googleapis.com/css?
family=Mate+SC\" />");
salida.println("<link rel=\"stylesheet\" href=\""+peticion.getContextPath()
+"/css/vendedor.css\" type=\"text/css\">");
salida.println("<body>");
salida.println("<a href='tienda'>Salir</a>");
salida.println("<h1>Pedidos Almacenados</h1>");
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos",
"tomcat", "tomcat");
String query = "select nombre, apellido, precio from usuarios, pedidos where usuarios.id =
pedidos._idusuario";
PreparedStatement pst = conn.prepareStatement(query);
ResultSet rs = pst.executeQuery();
salida.println("<div class='wrapPedidos'>");
while (rs.next()) {
    salida.println("<div class='pedido'>");
salida.println("<p class='nombre'>"+rs.getString(1)+" "+rs.getString(2)+"</p>");
salida.println("<p class='precio'>"+rs.getString(3)+"$</p>");
salida.println("</div>");
}
conn.close();
}c
atch(Exception e) {
}
salida.println("</div></body></html>");
}}