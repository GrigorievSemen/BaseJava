<%@ page import="ru.mystudies.basejava.model.ContactType" %>
<%@ page import="ru.mystudies.basejava.model.SectionType" %>
<%@ page import="ru.mystudies.basejava.util.ResumeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.mystudies.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.mystudies.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>

    <p>
        <c:forEach var="sectionEntry" items="${resume.section}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.mystudies.basejava.model.SectionType, ru.mystudies.basejava.model.AbstractSection>"/>

            <c:set var="sectionType" value="<%=sectionEntry.getKey()%>"/>
            <c:set var="objective" value="<%=SectionType.OBJECTIVE%>"/>
            <c:set var="personal" value="<%=SectionType.PERSONAL%>"/>
            <c:set var="achievement" value="<%=SectionType.ACHIEVEMENT%>"/>
            <c:set var="qualifications" value="<%=SectionType.QUALIFICATIONS%>"/>
            <c:set var="experiense" value="<%=SectionType.EXPERIENCE%>"/>
            <c:set var="education" value="<%=SectionType.EDUCATION%>"/>

        <c:choose>
        <c:when test="${sectionType.equals(objective) || sectionType.equals(personal) ||
             sectionType.equals(achievement) || sectionType.equals(qualifications)}">
            <c:set var="listTextToOut" value="${ResumeUtil.sectionDataToList(sectionType,resume)}"/>

        <c:if test="${listTextToOut.size() != 0}">
    <h3><%=sectionEntry.getKey().getTitle()%>
    </h3>

    <ul>
        <c:forEach var="text" items="${listTextToOut}">
            <c:if test="${text.trim().length() != 0}">
                <li>${text}</li>
            </c:if>
        </c:forEach>
    </ul>
    </c:if>
    </c:when>


    <c:when test="${sectionType.equals(experiense) || sectionType.equals(education)}">
        <c:set var="listOrganization" value="<%=sectionEntry.getValue().getItemsString()%>"/>

        <c:if test="${listOrganization.size() != 0}">
            <h3><%=sectionEntry.getKey().getTitle()%>
            </h3>
            <c:forEach var="organization" items="${listOrganization}">
                <ul>
                    <div class="section">
                        <li>
                            <div class="title"><a class="webLink"
                                                  href="${organization.getWebsite()}">${organization.getTitle()}</a>
                            </div>
                        </li>
                        <div class="period_position">
                            <c:forEach var="period" items="${organization.getPeriods()}">

                                <div class="period">${ResumeUtil.getPeriod(period)}</div>

                                <c:if test="${organization.getPosition().trim().length() != 0}">
                                    <div class="position">${organization.getPosition()}</div>
                                </c:if>
                                <div style="clear: both;"></div>

                                <div class="description">${period.getDescription()}</div>
                                <br/></c:forEach>
                        </div>
                    </div>
                </ul>
            </c:forEach>
        </c:if>
    </c:when>
    </c:choose>
    </c:forEach>
    </div>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>


