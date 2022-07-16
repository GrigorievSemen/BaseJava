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

            <c:set var="listTextToOut"
                   value="<%=ResumeUtil.sectionDataToString(sectionEntry.getKey(),resume)%>"/>

        <c:if test="${listTextToOut.size() != 0}">
    <h3><%=sectionEntry.getKey().getTitle()%></h3>
    <ul>
        <c:forEach var="text" items="${listTextToOut}">
            <li>${text}</li>
        </c:forEach>
    </ul>
    </c:if>
    </c:forEach>
    </p>
</section>


<jsp:include page="fragments/footer.jsp"/>
</body>
</html>


