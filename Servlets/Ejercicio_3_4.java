import java.io.*;
import java.servlet.*;

public class MiServlet extends HttpServlet {
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
        int cont = 1;
        respuesta.setContentType("text/html");
        PrintWriter salida = respuesta.getWriter();
        String titulo = "Contador";
        salida.println("<TITLE>"+titulo+"</TITLE>\n<BODY>");
        salida.println("<H1 ALIGN=CENTER>"+titulo+"</H1>\n\n");
        salida.println("El contador vale: "+cont);
        salida.println("</BODY></HTML>");
        cont++;
    }
}