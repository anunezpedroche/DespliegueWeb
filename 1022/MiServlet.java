import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class MiServlet extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        respuesta.setContentType("text/html");
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            String SourceURL = "jdbc:mysql://192.168.4.66:3306/bdprueba";
            String user = "miusuario";
            String password = "mipassword";
            Connection miconexion;
            miconexion = DriverManager.getConnection(SourceURL, user, password);
            Statement misentencia;
            ResultSet misresultados;
            misentencia = miconexion.createStatement();

            if (peticion.getParameter("registro") == null) {

                PrintWriter salida = respuesta.getWriter();

                String usuario = peticion.getParameter("usuario");

                String contrasenya = peticion.getParameter("password");

                if (usuario.contains("@")) {
                    misresultados = misentencia.executeQuery("SELECT nombre FROM amazon_user WHERE contraseña like '"
                            + contrasenya + "' AND (email LIKE '"+ usuario + "')");

                } else {
                    misresultados = misentencia.executeQuery("SELECT nombre FROM amazon_user WHERE contraseña like '"
                            + contrasenya + "' AND (movil = " + usuario + " )");
                }

                if (misresultados.next()) {

                    salida.println("<TITLE> Jorge 10.22 </TITLE>\n");
                    salida.println("<BODY BGCOLOR=\"#FDF5E6\">\n");
                    String nombre = misresultados.getString("nombre");
                    salida.println("<p>Buenos días, " + nombre + "</p>");
                    salida.println("</BODY></HTML>");
                } else {

                    miconexion.close();
                    PrintWriter out = respuesta.getWriter();
                    respuesta.setContentType("text/html");
                    out.println("<meta http-equiv='refresh' content='4; URL=/web/Jorge_10_22_login.html'>");
                    out.println("<p style='color:red;'>Usuario o contraseña incorrecta!</p>");
                }

            } else {

                PrintWriter salida = respuesta.getWriter();
                salida.println("<TITLE> Jorge 10.22 </TITLE>\n");
                salida.println("<BODY BGCOLOR=\"#FDF5E6\">\n");

                String nombre = peticion.getParameter("nombre");
                String email = peticion.getParameter("email");
                int movil = Integer.parseInt(peticion.getParameter("movil"));
                String contrasenya = peticion.getParameter("password");

                misresultados = misentencia.executeQuery(
                        "SELECT nombre FROM amazon_user WHERE ( email like '" + email + "' OR movil = " + movil + " )");

                if (misresultados.next()) {

                    miconexion.close();
                    PrintWriter out = respuesta.getWriter();
                    respuesta.setContentType("text/html");
                    out.println("<meta http-equiv='refresh' content='4; URL=/web/Jorge_10_22_registro.html'>");// redirects

                    out.println("<p style='color:red;'>Email o Movil ya registrados!</p>");
                } else {

                    misentencia.executeUpdate("INSERT INTO amazon_user (nombre, email, movil, contraseña) values('"
                            + nombre + "', '" + email + "', " + movil + ", '" + contrasenya + "' );");
                    miconexion.close();
                    respuesta.sendRedirect("/web/Jorge_10_22_login.html");
                    salida.println("</BODY></HTML>");
                }

            }

        } catch (SQLException sqle) {
            PrintWriter salida = respuesta.getWriter();

            salida.println(sqle);
            salida.println("</BODY></HTML>");
        }

    }
}