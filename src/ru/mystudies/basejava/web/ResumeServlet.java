package ru.mystudies.basejava.web;


import ru.mystudies.basejava.Config;
import ru.mystudies.basejava.model.*;
import ru.mystudies.basejava.storage.Storage;
import ru.mystudies.basejava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        if (uuid == null) {
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


    private static void createOrganization(String[] value, List<Organization> organizations) {
        for (int i = 0; i < value.length - 1; i++) {
            Organization organization = new Organization();
            List<Period> periods = new ArrayList<>();
            organization.setTitle(value[i].trim());
            organization.setWebsite(value[++i].trim());
            organization.setPosition(value[++i].trim());
            periods.add(new Period(DateUtil.fromInt(value[++i].trim()), DateUtil.fromInt(value[++i].trim()), value[++i]));
            while (i < value.length - 1 && value[i + 2].matches("(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)")) {
                organization.setPosition(value[++i]);
                periods.add(new Period(DateUtil.fromInt(value[++i].trim()), DateUtil.fromInt(value[++i].trim()), value[++i]));
            }
            organization.setPeriods(periods);
            organizations.add(organization);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        List<String> listAchievement = new ArrayList<>();
        List<String> listQualifications = new ArrayList<>();
        List<Organization> organizationExp = new ArrayList<>();
        List<Organization> organizationEd = new ArrayList<>();

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
                for (int i = 0; i < value.length; i++) {
                    switch (sectionType) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (value[i] != null && value[i].trim().length() != 0) {
                                resume.addSection(sectionType, new TextSection(value[i]));
                            } else {
                                resume.addSection(sectionType, new TextSection(""));
                            }
                            break;
                        case ACHIEVEMENT:
                            listAchievement.add(value[i]);
                            break;
                        case QUALIFICATIONS:
                            listQualifications.add(value[i]);
                            break;
                        case EXPERIENCE:
                            createOrganization(value, organizationExp);
                            i = value.length;
                            break;
                        case EDUCATION:
                            createOrganization(value, organizationEd);
                            i = value.length;
                            break;
                    }
                }
            }
        }
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(listAchievement));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(listQualifications));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(organizationExp));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(organizationEd));
        storage.update(resume);
        response.sendRedirect("resume");
    }
}
