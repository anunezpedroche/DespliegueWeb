import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class E10_27_principal extends HttpServlet {

    ArrayList<String> lista = new ArrayList<String>();

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {

        {
            HttpSession misesion;
            misesion = peticion.getSession(true);

            Cookie migalleta = null;
            Cookie[] misgalletas;
            misgalletas = peticion.getCookies();

            if (misgalletas == null) {

                migalleta = new Cookie("idCookie", misesion.getId());

                migalleta.setMaxAge(99999); // 1 minuto
                respuesta.addCookie(migalleta);
            }

            Cookie[] cookies = peticion.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("idCookie")) {
                        migalleta = cookie;
                    }
                }
            }

            

            respuesta.setContentType("text/html");
            PrintWriter salida = respuesta.getWriter();
            String titulo = "Tienda cervecera 2";
            salida.println("<TITLE>" + titulo + "</TITLE>\n");
            salida.println("<BODY BGCOLOR=\"#FDF5E6\">\n");

            salida.println("<FORM align='right' ACTION='/Tienda/E10.27_vendedor' METHOD='POST'>");
            salida.println("<INPUT TYPE='text' NAME='user'>");
            salida.println("<INPUT TYPE='password' NAME='password'>");
            salida.println("<button type=''>Login</button>");
            salida.println("</FORM>");

            salida.println("<FORM align='left' ACTION='/Tienda/E10.27_compras' METHOD='POST'>");
            salida.println("<INPUT TYPE='hidden' value='"+migalleta.getValue()+"' NAME='cookie'>");
            salida.println("<button type=''>Ver mis compras</button>");
            salida.println("</FORM>");

            try {
                salida.println("<H1 ALIGN=CENTER>" + titulo + "</H1>\n\n");
                DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
                String SourceURL = "jdbc:mysql://localhost:3306/bdprueba";
                String user = "miusuario";
                String password = "mipassword";
                Connection miconexion;
                miconexion = DriverManager.getConnection(SourceURL, user, password);

                Statement misentencia;
                ResultSet misresultados;
                misentencia = miconexion.createStatement();
                misresultados = misentencia.executeQuery("SELECT * FROM productos");

                salida.println("<TABLE BORDER=1 ALIGN=CENTER>\n" + "<TR BGCOLOR=\"#FFAD00\">\n"
                        + "<TH>Producto<TH>Precio<TH>");

                while (misresultados.next()) {

                    String nombre = misresultados.getString("nombre");
                    String precio = misresultados.getString("precio");
                    int id = misresultados.getInt("id");

                    salida.println("<FORM ACTION='/Tienda/E10.27_principal' METHOD='POST'>");
                    salida.println("<INPUT TYPE='hidden' value='" + id + "' NAME='id'>");

                    salida.println("<TR><TD>" +

                            nombre + "\n<TD>" + precio + " &euro;" + "\n<TD>"
                            + "<button type=''>Añadir a Carrito</button>");

                    salida.println("</FORM>");

                }
                salida.println("</TABLE>");

                salida.println("<CENTER>");
                salida.println("<FORM ACTION='/Tienda/E10.27_principal' METHOD='POST'>");
                salida.println("<button type=''>Enviar pedidos</button>");
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

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        HttpSession misesion;
        misesion = peticion.getSession(true);
        misesion.setMaxInactiveInterval(10);

        Cookie migalleta = null;
        Cookie[] cookies = peticion.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("idCookie")) {
                    migalleta = cookie;
                }
            }
        }

        respuesta.setContentType("text/html");
        PrintWriter salida = respuesta.getWriter();
        String titulo = "Tienda cervecera 2";

        if (peticion.getParameter("envio") != null) {

            try {
                salida.println("<H1 ALIGN=CENTER>" + titulo + "</H1>\n\n");
                DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
                String SourceURL = "jdbc:mysql://localhost:3306/bdprueba";
                String user = "miusuario";
                String password = "mipassword";
                Connection miconexion;
                miconexion = DriverManager.getConnection(SourceURL, user, password);

                Statement misentencia;
                ResultSet misresultados;
                misentencia = miconexion.createStatement();
                String aux = peticion.getParameter("envio");
                String listaString = aux.replace("[", "").replace("]", "");
                String[] lista_id = listaString.split(",");

                float total = 0;
                String nombre = "";
                int costeEnvio = 2 + lista_id.length;
                for (int i = 0; i < lista_id.length; i++) {

                    lista_id[i] = lista_id[i].replace(" ", "").replace("+", "");

                    misresultados = misentencia.executeQuery("SELECT * FROM productos where id = " + lista_id[i] + ";");

                    while (misresultados.next()) {

                        nombre += "|" + misresultados.getString("nombre") + "|";

                        total += Float.parseFloat(misresultados.getString("precio"));

                    }
                }
                total += costeEnvio;
                String consulta = "INSERT INTO envios (lista,total, idcookie) values ('" + nombre + "','" + total
                        + "','" + migalleta.getValue() + "')";
                misentencia.executeUpdate(consulta);

                miconexion.close();
            } catch (SQLException sqle) {

                salida.println(sqle);

            }

            misesion.invalidate();
            lista.clear();

            salida.println("<TITLE> Enviado </TITLE>\n");
            salida.println("<BODY BGCOLOR=\"#FDF5E6\">\n");
            salida.println("<H1 ALIGN=CENTER>Gracias por tu compra</H1>\n\n");
            salida.println("<CENTER>");
            salida.println("<FORM ACTION='/Tienda/E10.27_principal' METHOD='GET'>");
            salida.println("<INPUT TYPE='hidden' value='' NAME='regresar'>");
            salida.println("<button type=''>Volver a la Tienda</button>");
            salida.println("</FORM>");
            salida.println("</CENTER>");
            salida.println("</BODY></HTML>");
            return;
        } else {

            String productos = peticion.getParameter("id");
            lista.add(productos);
            misesion.setAttribute("lista", lista);

            {
                salida.println("<TITLE>" + titulo + "</TITLE>\n");
                salida.println("<BODY BGCOLOR=\"#FDF5E6\">\n");
                salida.println("<FORM align='right' ACTION='/Tienda/E10.27_vendedor' METHOD='POST'>");
                salida.println("<INPUT TYPE='text' NAME='user'>");
                salida.println("<INPUT TYPE='password' NAME='password'>");
                salida.println("<button type=''>Login</button>");
                salida.println("</FORM>");
                salida.println("<FORM align='left' ACTION='/Tienda/E10.27_compras' METHOD='POST'>");
                salida.println("<button type=''>Ver mis compras</button>");
                salida.println("</FORM>");

                try {
                    salida.println("<H1 ALIGN=CENTER>" + titulo + "</H1>\n\n");
                    DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
                    String SourceURL = "jdbc:mysql://localhost:3306/bdprueba";
                    String user = "miusuario";
                    String password = "mipassword";
                    Connection miconexion;
                    miconexion = DriverManager.getConnection(SourceURL, user, password);

                    Statement misentencia;
                    ResultSet misresultados;
                    misentencia = miconexion.createStatement();
                    misresultados = misentencia.executeQuery("SELECT * FROM productos");

                    salida.println("<TABLE BORDER=1 ALIGN=CENTER>\n" + "<TR BGCOLOR=\"#FFAD00\">\n"
                            + "<TH>Producto<TH>Precio<TH>");

                    while (misresultados.next()) {

                        String nombre = misresultados.getString("nombre");
                        String precio = misresultados.getString("precio");
                        int id = misresultados.getInt("id");

                        salida.println("<FORM ACTION='/Tienda/E10.27_principal' METHOD='POST'>");
                        salida.println("<INPUT TYPE='hidden' value='" + id + "' NAME='id'>");

                        salida.println("<TR><TD>" +

                                nombre + "\n<TD>" + precio + " &euro;" + "\n<TD>"
                                + "<button type=''>Añadir a Carrito</button>");

                        salida.println("</FORM>");

                    }
                    salida.println("</TABLE>");

                    ArrayList<String> lista = (ArrayList<String>) misesion.getAttribute("lista");

                    salida.println("<CENTER>");
                    salida.println("<FORM ACTION='/Tienda/E10.27_envio' METHOD='POST'>");
                    salida.println("<INPUT TYPE='hidden' value='" + lista + "' NAME='ids'>");
                    salida.println("<button type=''>Enviar pedidos</button>");
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
    }
}