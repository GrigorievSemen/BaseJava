<%@ page import="ru.mystudies.basejava.util.HtmlUtil" %>
<%@ page import="ru.mystudies.basejava.model.*" %>
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
    <h1>${resume.fullName}<a href="resume?uuid=${resume.uuid}&action=edit">  <img src="img/pencil.png"></a></h1>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.mystudies.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.mystudies.basejava.model.SectionType, ru.mystudies.basejava.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.mystudies.basejava.model.AbstractSection"/>

            <c:choose>
                <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                        <c:if test="${!empty HtmlUtil.sectionDataToString(type,resume)}">
                            <tr>
                                <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <p>${HtmlUtil.sectionDataToString(type,resume)}</p>
                                </td>
                            </tr>
                        </c:if>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <c:if test="${!empty HtmlUtil.sectionDataToString(type,resume)}">
                        <tr>
                            <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <ul>
                                    <c:forEach var="item" items="${HtmlUtil.sectionDataToList(type,resume)}">
                                        <li>${item}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:if>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">

                    <c:set var="organizations" value="<%=((OrganizationSection) section).getOrganizations()%>"/>
                    <c:if test = "${organizations.size()!=0}">
                        <tr>
                            <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
                        </tr>
                    <c:forEach var="org" items="${organizations}">
                            <tr>
                                <td colspan="2">
                                    <c:choose>
                                        <c:when test="${empty org.website}">
                                            <h3>${org.title}</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3><a href="${org.website}">${org.title}</a></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="period" items="${org.periods}">
                                <jsp:useBean id="period" type="ru.mystudies.basejava.model.Period"/>
                                <tr>
                                    <td width="15%" style="vertical-align: top"><%=HtmlUtil.getPeriod(period)%>
                                    </td>
                                    <td><b>${period.position}</b><br>${period.description}</td>
                                </tr>
                            </c:forEach>
                    </c:forEach>
                </c:if>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>
    <button onclick="window.history.back()">ОК</button>
</section>
</body>
</html>


