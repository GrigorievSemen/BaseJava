package ru.mystudies.basejava.web;


import ru.mystudies.basejava.Config;
import ru.mystudies.basejava.model.*;
import ru.mystudies.basejava.storage.Storage;
import ru.mystudies.basejava.util.DateUtil;
import ru.mystudies.basejava.util.HtmlUtil;

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
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }


        String uuid = request.getParameter("uuid");

        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "add":
                resume = Resume.EMPTY;
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            // emptyFirstOrganizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Period> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.addAll(org.getPeriods());
                                    //  emptyFirstPositions.add(Period.EMPTY);
                                    emptyFirstOrganizations.add(new Organization(org.getTitle(), org.getWebsite(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            break;
                    }
                    resume.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                "view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"
        ).forward(request, response);
    }


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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        List<String> listAchievement = new ArrayList<>();
        List<String> listQualifications = new ArrayList<>();
        final boolean isCreate = (uuid == null || uuid.length() == 0);

        Resume resume;
        if (isCreate) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() != 0) {
                resume.setContact(contactType, value);
            } else {
                resume.getContacts().remove(contactType);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String[] value = request.getParameterValues(sectionType.name());

            if (value != null) {
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        if (value[0] != null && value[0].trim().length() != 0) {
                            resume.setSection(sectionType, new TextSection(value[0]));
                        } else {
                            resume.setSection(sectionType, new TextSection(""));
                        }
                        break;
                    case ACHIEVEMENT:
                        listAchievement.add(value[0]);
                        break;
                    case QUALIFICATIONS:
                        listQualifications.add(value[0]);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(sectionType.name() + "url");
                        for (int i = 0; i < value.length; i++) {
                            String nameOrganization = value[i];
                            if (!HtmlUtil.isEmpty(nameOrganization)) {
                                List<Period> periods = new ArrayList<>();
                                String[] position = request.getParameterValues(i + sectionType.name() + "position");
                                String[] startPeriod = request.getParameterValues(i + sectionType.name() + "startPeriod");
                                String[] endPeriod = request.getParameterValues(i + sectionType.name() + "endPeriod");
                                String[] description = request.getParameterValues(i + sectionType.name() + "description");

                                int counter = description != null ? description.length :
                                        position != null ? position.length : 0;

                                for (int k = 0; k < counter; k++) {
                                    if (!action.equals(k + "deletePeriod" + i) && (!HtmlUtil.isEmpty(description[k]) || !HtmlUtil.isEmpty(position[k]))) {
                                        periods.add(new Period(DateUtil.fromInt(startPeriod[k].trim()), DateUtil.fromInt(endPeriod[k].trim()),
                                                position[k] != null ? position[k] : "", description[k] != null ? description[k] : ""));
                                    }
                                }

                                if (action.equals(i + "addPeriod")) {
                                    periods.add(0, Period.EMPTY);
                                }

                                if (!action.equals(i + "deleteOrganizationEXPERIENCE") && sectionType.name().equals("EXPERIENCE")) {
                                    organizations.add(new Organization(nameOrganization, urls[i], periods));
                                }
                                if (!action.equals(i + "deleteOrganizationEDUCATION") && sectionType.name().equals("EDUCATION")) {
                                    organizations.add(new Organization(nameOrganization, urls[i], periods));
                                }
                            }
                        }

                        if (action.equals("EXPERIENCEaddOrganization") && sectionType.name().equals("EXPERIENCE")) {
                            organizations.add(0, Organization.EMPTY);
                            action = "";
                        }

                        if (action.equals("EDUCATIONaddOrganization") && sectionType.name().equals("EDUCATION")) {
                            organizations.add(0, Organization.EMPTY);
                            action = "";
                        }

                        resume.setSection(sectionType, new OrganizationSection(organizations));
                        break;
                }
            }
        }

        if (action.equals("EXPERIENCEaddOrganization")) {
            resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
        }
        if (action.equals("EDUCATIONaddOrganization")) {
            resume.setSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
        }

        resume.setSection(SectionType.ACHIEVEMENT, new ListSection(listAchievement));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection(listQualifications));

        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        request.setAttribute("resume", resume);

        action = request.getParameter("action");
        if (action != null &&
                (action.equals("EXPERIENCEaddOrganization") || action.equals("EDUCATIONaddOrganization")
                        || action.matches(".*addPeriod.*") || action.matches(".*deletePeriod.*")
                        || action.matches(".*deleteOrganizationEXPERIENCE.*") || action.matches(".*deleteOrganizationEDUCATION.*"))) {
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
        } else {
            response.sendRedirect("resume");
        }
    }
}
