package ru.mystudies.basejava.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        String resume = "<html>\n" +
                "<head>\n" +
                "    <title>Resume</title>\n" +
                "</head>\n" +
                "<body style=\"background-color:#85adad;\">\n" +
                "    <div>\n" +
                "        <h1><p>Петров Петр Петрович <br/> </p></h1>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "        <ul>\n" +
                "            <li>Тел: - 111-111-11-11 </li>\n" +
                "            <li>Skype: - qwerty</li>\n" +
                "            <li>Email: - mail</li>\n" +
                "            <li>Profile linkedin: - LinkedIn</li>\n" +
                "            <li>Profile GitHub: - GitHub</li>\n" +
                "            <li>Profile StackOverflow: - Stackoverflow</li>\n" +
                "            <li>Profile Home Page: - Home</li>\n" +
                "        </ul>   \n" +
                "    </div>\n" +
                "    <div>\n" +
                "        <h2><p>Позиция:</p></h2>\n" +
                "        <p>Objective<br/></p>\n" +
                "        <h2><p>Личные качества:</p></h2>\n" +
                "        <p>Personal<br/></p>\n" +
                "        <h2><p>Достижения:</p></h2>\n" +
                "        <ul>            \n" +
                "            <li>Achieve_1</li>\n" +
                "            <li>Achieve_2</li>\n" +
                "        </ul>        \n" +
                "        <h2><p>Квалификация:</p></h2>\n" +
                "        <ul>            \n" +
                "            <li>Qualifications_1</li>\n" +
                "            <li>Qualifications_2</li>\n" +
                "        </ul>       \n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        String tableResumes = "<html>\n" +
                "<head>\n" +
                "    <title>Resume</title>\n" +
                "</head>\n" +
                "<body style=\"background-color:#85adad;\">\n" +
                "    <div>\n" +
                "        <h2><p>Список резюме<br/> </p></h2>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "        <table border=\"1\" width=\"40%\">\n" +
                "            <tr>\n" +
                "                <td align=\"center\"> UUID_1</td>\n" +
                "                <td>Петров Петр Петрович</td>\n" +
                "            </tr>\n" +
                "            </tr>\n" +
                "            <td align=\"center\">UUID_2</td>\n" +
                "            <td>Иванов Иван Иванович</td>\n" +
                "            </tr>\n" +
                "            </tr>\n" +
                "            <td align=\"center\">UUID_3</td>\n" +
                "            <td>Сидоров Сергей Сергеев</td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        response.getWriter().write(name == null ? tableResumes : resume);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
