import java.io.*;
import java.servlet.*;


int cont = 1;
String contadores[] = new String[20];


public class MiServlet extends HttpServlet {

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {

        respuesta.setContentType("text/html");
        PrintWriter salida = respuesta.getWriter();
        String titulo = "Contador";
        salida.println("<TITLE>"+titulo+"</TITLE>\n<BODY>");
        salida.println("<H1 ALIGN=CENTER>"+titulo+"</H1>\n\n");

        for(int i = 0; i < cont; i++){

            contadores[cont] = i;
            salida.println("El contador vale: "+contadores[i]+"<br>");
            cont++;

        }
        
        salida.println("</BODY></HTML>");
        cont++;
    }
}