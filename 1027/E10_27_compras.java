import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class E10_27_compras extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        String idUsuario = peticion.getParameter("cookie");

        // Se llama al recibir el POST del GET anterior
        respuesta.setContentType("text/html");
        PrintWriter salida = respuesta.getWriter();
        String titulo = "Compras cerveceras 2";
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

            salida.println("<TABLE BORDER=1 ALIGN=CENTER>\n" + "<TR BGCOLOR=\"#FFAD00\">\n" + "<TH>Lista<TH>Total");

            misresultados = misentencia.executeQuery("SELECT * FROM envios where idcookie like '" + idUsuario + "';");

            while (misresultados.next()) {

                String lista = misresultados.getString("lista");
                String total = misresultados.getString("total");

                salida.println("<TR><TD>" + lista + "\n<TD>" + total + "\n");
            }

            salida.println("</TABLE>");

            salida.println("<CENTER>");
            salida.println("<FORM ACTION='/Tienda/E10.27_principal' METHOD='GET'>");
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