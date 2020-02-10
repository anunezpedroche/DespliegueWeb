import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class MiServlet extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        respuesta.setContentType("text/html");
        PrintWriter salida = respuesta.getWriter();
        String titulo = "Jorge 10.21 Conexión JDBC a MySQL";
        salida.println("<TITLE>" + titulo + "</TITLE>\n");
        salida.println("<BODY BGCOLOR=\"#FDF5E6\">\n");
        salida.println("<H1 ALIGN=CENTER>Añadir nuevo empleado</H1>\n\n");
        salida.println("<FORM ACTION='/Proyecto1/Jorge_10.21' METHOD='POST'>");
        salida.println("DNI");
        salida.println("<INPUT TYPE='number' NAME='DNI'><BR>");
        salida.println("Nombre");
        salida.println("<INPUT TYPE='text' NAME='nombre'><BR>");
        salida.println("Sueldo");
        salida.println("<INPUT TYPE='number' NAME='sueldo'><BR>");
        salida.println("Plus");
        salida.println("<INPUT TYPE='number' NAME='plus'><BR>");
        salida.println("<CENTER>");
        salida.println("<INPUT TYPE='SUBMIT'>");
        salida.println("</CENTER>");
        salida.println("</FORM>");
        salida.println("<BR><BR>");

        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            String SourceURL = "jdbc:mysql://192.168.4.65:3306/bdprueba";
            String user = "miusuario";
            String password = "mipassword";

            int dni = Integer.parseInt(peticion.getParameter("DNI"));
            String nombre = peticion.getParameter("nombre");
            int sueldo = Integer.parseInt(peticion.getParameter("sueldo"));
            int plus = Integer.parseInt(peticion.getParameter("plus"));

            Connection miconexion;
            miconexion = DriverManager.getConnection(SourceURL, user, password);

            Statement misentencia;
            ResultSet misresultados;
            misentencia = miconexion.createStatement();
            misentencia.executeUpdate(
                    "INSERT INTO empleados values(" + dni + ", '" + nombre + "', " + sueldo + ", " + plus + " );");
            misresultados = misentencia.executeQuery("SELECT * FROM empleados where dni = " + dni + ";");

            salida.println("<TABLE BORDER=1 ALIGN=CENTER>\n" + "<TR BGCOLOR=\"#FFAD00\">\n"
                    + "<TH>DNI<TH>Nombre<TH>Sueldo<TH>PLus");

            while (misresultados.next()) {
                salida.println("<TR><TD>" + misresultados.getString("DNI") + "\n<TD>"
                        + misresultados.getString("nombre") + "\n<TD>" + misresultados.getString("sueldo") + "\n<TD>"
                        + misresultados.getFloat("plus"));
            }
            salida.println("</TABLE></BODY></HTML>");
            miconexion.close();
        } catch (SQLException sqle) {
            salida.println(sqle);
            salida.println("</BODY></HTML>");
        }
    }

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {

        respuesta.setContentType("text/html");
        PrintWriter salida = respuesta.getWriter();
        String titulo = "Jorge 10.19 Conexión JDBC a MySQL";
        salida.println("<TITLE>" + titulo + "</TITLE>\n");
        salida.println("<BODY BGCOLOR=\"#FDF5E6\">\n");
        salida.println("<H1 ALIGN=CENTER>Añadir nuevo empleado</H1>\n\n");
        salida.println("<FORM ACTION='/Proyecto1/Jorge_10.21' METHOD='POST'>");
        salida.println("DNI");
        salida.println("<INPUT TYPE='number' NAME='DNI'><BR>");
        salida.println("Nombre");
        salida.println("<INPUT TYPE='text' NAME='nombre'><BR>");
        salida.println("Sueldo");
        salida.println("<INPUT TYPE='number' NAME='sueldo'><BR>");
        salida.println("Plus");
        salida.println("<INPUT TYPE='number' NAME='plus'><BR>");
        salida.println("<CENTER>");
        salida.println("<INPUT TYPE='SUBMIT'>");
        salida.println("</CENTER>");
        salida.println("</FORM>");
        salida.println("</TABLE></BODY></HTML>");

    }

}