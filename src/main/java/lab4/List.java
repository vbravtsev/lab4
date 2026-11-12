package lab4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class List extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String name = request.getParameter("name");
        String adminEmail = getServletConfig().getInitParameter("adminEmail");
        
        // 1. Получаем параметр языка (lang)
        String lang = request.getParameter("lang");
        if (lang == null) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Ожидался параметр lang");
            return;
        }
        if (!"en".equalsIgnoreCase(lang) && !"ru".equalsIgnoreCase(lang)) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Параметр lang может принимать значение en или ru");
            return;
        }

        // 2. Загружаем нужный файл ресурсов (.properties)
        Locale locale = "en".equalsIgnoreCase(lang) ? Locale.ENGLISH : Locale.forLanguageTag("ru");
        ResourceBundle res = ResourceBundle.getBundle("Book", locale);

        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>" + res.getString("title") + "</title></head>");
            out.println("<body>");
            
            // Заголовок с учетом локализации
            out.println("<h1>" + res.getString("header_list") + " " + (name != null ? name : "") + "</h1>");
            
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<td><b>" + res.getString("author") + "</b></td>");
            out.println("<td><b>" + res.getString("book.title") + "</b></td>");
            out.println("<td><b>" + res.getString("read") + "</b></td>");
            out.println("</tr>");
            
            out.println("<tr><td>Булгаков</td><td>Мастер и Маргарита</td><td>Да</td></tr>");
            out.println("<tr><td>Пелевин</td><td>Чапаев и Пустота</td><td>Нет</td></tr>");
            out.println("</table>");
            
            if (adminEmail != null) {
                out.println("<p><small>Контакт: " + adminEmail + "</small></p>");
            }
            
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}