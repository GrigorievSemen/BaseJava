package ru.mystudies.basejava.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.mystudies.basejava.Config;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.storage.Storage;

import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();

        printWriter.write(
                "<html>\n" +
                        "<head>\n" +
                        "    <title>All resumes</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table border=\"1\">\n" +
                        "    <tr>\n" +
                        "        <th>UUID</th>\n" +
                        "        <th>Full name</th>\n" +
                        "    </tr>\n");

        for (Resume resume : storage.getAllSorted()) {
            printWriter.write(
                    "<tr>\n" +
                            "<td>" + resume.getUuid() + "\n" +
                            "<td>" + resume.getFullName() + "\n" +
                    "</tr>\n");
        }

        printWriter.write("</table>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
