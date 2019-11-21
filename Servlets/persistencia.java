import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class MiContador extends HttpServlet
{
int contador=1;
public void doGet(HttpServletRequest peticion,
HttpServletResponse respuesta)
throws ServletException, IOException
{
respuesta.setContentType("text/html");
PrintWriter salida = respuesta.getWriter();
String titulo = "Contador";
salida.println ("<TITLE>"+titulo+"</TITLE>\n<BODY>");
salida.println ("<H1 ALIGN=CENTER>" +
titulo + "</H1>\n\n");
salida.println("El contador vale: "+contador);
salida.println("</BODY></HTML>");
contador++;
}
}