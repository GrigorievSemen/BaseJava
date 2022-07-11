package ru.mystudies.basejava.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.mystudies.basejava.Config;
import ru.mystudies.basejava.model.ContactType;
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
       String uuid = request.getParameter("uuid");
       String action = request.getParameter("action");
       if(action == null){
           request.setAttribute("resumes", storage.getAllSorted());
           request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
           return;
       }

        Resume r;
       switch (action){
           case "delete":
               storage.delete(uuid);
               response.sendRedirect("resume");
               return;
           case "view":
           case "edit":
               r = storage.get(uuid);
               break;
           default:
               throw new IllegalArgumentException("Action " + action + " is illegal");
       }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp" )
                .forward(request, response);

        //        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
//        PrintWriter printWriter = response.getWriter();
//
//        printWriter.write(
//                "<html>\n" +
//                        "<head>\n" +
//                        "    <title>All resumes</title>\n" +
//                        "</head>\n" +
//                        "<body>\n" +
//                        "<table border=\"1\">\n" +
//                        "    <tr>\n" +
//                        "        <th>Имя</th>\n" +
//                        "        <th>Email</th>\n" +
//                        "    </tr>\n");
//
//        for (Resume resume : storage.getAllSorted()) {
//            printWriter.write(
//                    "<tr>\n" +
//                            "     <td><a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
//                            "     <td>" + resume.getContact(ContactType.EMAIL) + "</td>\n" +
//                    "</tr>\n");
//        }
//
//        printWriter.write("</table>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
