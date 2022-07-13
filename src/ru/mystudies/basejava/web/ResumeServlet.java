package ru.mystudies.basejava.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.mystudies.basejava.Config;
import ru.mystudies.basejava.model.*;
import ru.mystudies.basejava.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume resume;

        if(uuid == null){
            resume = new Resume();
            storage.save(resume);
            uuid = resume.getUuid();
        }

            switch (action) {
                case "delete":
                    storage.delete(uuid);
                    response.sendRedirect("resume");
                    return;
                case "view":
                case "edit":
                    resume = storage.get(uuid);
                    break;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal");
            }
            request.setAttribute("resume", resume);
            request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
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
        request.setCharacterEncoding("UTF-8");

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        List<String> listAchievement = new ArrayList<>();
        List<String> listQualifications = new ArrayList<>();

        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);

        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(contactType, value);
            } else {
                resume.getContacts().remove(contactType);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String[] value = request.getParameterValues(sectionType.name());
            if (value != null) {
                for (String str : value) {
                    switch (sectionType) {
                        case OBJECTIVE:
                        case PERSONAL:
                            resume.addSection(sectionType, new TextSection(str));
                            break;
                        case ACHIEVEMENT:
                            listAchievement.add(str);
                            break;
                        case QUALIFICATIONS:
                            listQualifications.add(str);
                            break;
                    }
                }
            }
        }
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(listAchievement));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(listQualifications));

        storage.update(resume);
        response.sendRedirect("resume");
    }
}
