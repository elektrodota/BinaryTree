package com.elefank.binfa;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet("/binfa")
    @MultipartConfig
    public class binfaServ extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
            String parameter = httpServletRequest.getParameter("text_input");
            try {
                PrintWriter printWriter = httpServletResponse.getWriter();

                printWriter.write(
                        "<html>" +
                                "<body>" +
                                "<h1>" + parameter + "</h1>" +
                                "</body>" +
                                "</html>"
                );
                printWriter.close();
            } catch (IOException ioe) {
                System.err.print(ioe);
            }
        }

        @Override
        protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
            Part part = httpServletRequest.getPart("file");
            InputStream inputStream = part.getInputStream();

            byte[] b = new byte[1];

            LZWBinFa binFa = new LZWBinFa();



            boolean kommentben = false;

            while (inputStream.read(b) != -1) {

                if (b[0] == 0x3e) {
                    kommentben = true;
                    continue;
                }

                if (b[0] == 0x0a) {
                    kommentben = false;
                    continue;
                }

                if (kommentben) {
                    continue;
                }

                if (b[0] == 0x4e)
                {
                    continue;
                }

                for (int i = 0; i < 8; ++i) {
                    if ((b[0] & 0x80) != 0)
                    {
                        binFa.egyBitFeldolg('1');
                    } else
                    {
                        binFa.egyBitFeldolg('0');
                    }
                    b[0] <<= 1;
                }

            }

            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);

            binFa.kiir(printWriter);

            printWriter.write(
                    "<br/>Mélység: " + binFa.getMelyseg() + "<br/>Átlag: " + binFa.getAtlag() + "<br/>Szórás: " + binFa.getSzoras()
            );

            httpServletRequest.setAttribute("result", stringWriter.toString());
            httpServletRequest.getRequestDispatcher("/results.jsp").forward(httpServletRequest, httpServletResponse);
            printWriter.close();
            stringWriter.close();;
        }
}
