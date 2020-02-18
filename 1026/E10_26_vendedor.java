import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class E10_26_vendedor extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        String usuario = peticion.getParameter("user");
        String contrasena = peticion.getParameter("password");

        // Se llama al recibir el POST del GET anterior
        respuesta.setContentType("text/html");
        PrintWriter salida = respuesta.getWriter();
        String titulo = "Zona de administración";
        salida.println("<TITLE>" + titulo + "</TITLE>\n");
        salida.println("<BODY BGCOLOR=\"#FDF5E6\">\n");

        try {

            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            String SourceURL = "jdbc:mysql://localhost:3306/bdprueba";
            String user = "miusuario";
            String password = "mipassword";
            Connection miconexion;
            miconexion = DriverManager.getConnection(SourceURL, user, password);
            Statement misentencia;
            ResultSet misresultados;
            misentencia = miconexion.createStatement();

            misresultados = misentencia.executeQuery("SELECT password FROM vendedor where usuario like '"+usuario+"';");
            while (misresultados.next()) {
                if (!misresultados.getString("password").equals(contrasena)) {
                    respuesta.sendRedirect("/Tienda/E10.26_principal");
                }
            }
            
            salida.println(
                    "<TABLE BORDER=1 ALIGN=CENTER>\n" + "<TR BGCOLOR=\"#FFAD00\">\n" + "<TH>ID<TH>Lista<TH>Total");

            misresultados = misentencia.executeQuery("SELECT * FROM envios;");

            while (misresultados.next()) {

                int id = misresultados.getInt("id");
                String lista = misresultados.getString("lista");
                String total = misresultados.getString("total");

                salida.println("<TR><TD>" + id + "\n<TD>" + lista + "\n<TD>" + total);
            }

            salida.println("</TABLE>");

            salida.println("<CENTER>");
            salida.println("<FORM ACTION='/Tienda/E10.26_principal' METHOD='GET'>");
            salida.println("<INPUT TYPE='hidden' value='' NAME='regresar'>");
            salida.println("<button type=''>Volver a la Tienda</button>");
            salida.println("</FORM>");
            salida.println("</CENTER>");

            salida.println("</BODY></HTML>");
            miconexion.close();
        } catch (SQLException sqle) {
            salida.println(sqle);
            salida.println("</BODY></HTML>");
        }
    }
}