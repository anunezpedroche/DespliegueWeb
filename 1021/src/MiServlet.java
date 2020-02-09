import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class MiServlet extends HttpServlet {
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
        respuesta.setContentType("text/html");
        PrintWriter salida = respuesta.getWriter();
        String titulo = "Conexión JDBC a MySQL";
        salida.println("<TITLE>"+titulo+"</TITLE>\n");
        salida.println("<BODY BGCOLOR=\"#FDF5E6\">");
        salida.println("<H1 ALIGN=CENTER>"+titulo+"</H1>\n\n");

        try{
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            String SourceURL = "jdbc:mysql://127.0.0.1:3306/bdprueba";
            String user = "miusuario";
            String password = "mipassword";

            Connection miconexion;
            miconexion = DriverManager.getConnection(SourceURL,user,password);

            String dni = peticion.getParameter("dni");
            String nombre = peticion.getParameter("nombre");
            String sueldo = peticion.getParameter("sueldo");

            salida.println("<form action='servlet'><br>"
            +"<input type='number' value=0 min=0 name='dni' max=9999999999><br>"
            +"<input type='text' name='nombre'><br>"
            +"<input type='text' name='sueldo'><br>"
            +"<input type='submit' name='enviar'><br>"+"</form>"
            );

            if(!dni.equals(0)){
                Statement misentencia;
                Statement miinsert;
                ResultSet misresultados;

                miinsert = miconexion.createStatement();
                miinsert.executeUpdate("INSERT INTO empleados(dni,nombre,sueldo) VALUES("+dni+","+nombre+","+sueldo+")");
                misentencia = miconexion.createStatement();
                misresultados = misentencia.executeQuery("SELECT nombre,sueldo FROM empleados WHERE dni="+dni+"");

                salida.println("<TABLE BORDER=1 ALIGN=CENTER>\n"+
                "<TR BGCOLOR=\"#FFAD00\">\n"+"<TH>Nombre<TH>Sueldo");
                while(misresultados.next()) {salida.println("<TR><TD>"+
                misresultados.getString("nombre")+"\n<TD>"+
                misresultados.getFloat("sueldo"));
                }
                salida.println("</TABLE></BODY></HTML>");
            }
            miconexion.close();
        }catch(SQLException sqle){
            salida.println(sqle);
            salida.println("</BODY></HTML>");
        }
    }
}