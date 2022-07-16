<%@ page import="ru.mystudies.basejava.model.ContactType" %>
<%@ page import="ru.mystudies.basejava.model.SectionType" %>
<%@ page import="ru.mystudies.basejava.util.ResumeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.mystudies.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 required value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>

        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <jsp:useBean id="type"
                         type="ru.mystudies.basejava.model.SectionType"/>

            <dl>
                <dt>${type.title}</dt>
                <c:set var="text"
                       value="<%=ResumeUtil.sectionDataToString(type,resume)%>"/>

                <c:if test="${text.length() == 0}">
                    <p><textarea placeholder="Введите данные" wrap="hard" rows="3" cols="50"
                                 name="${type.name()}"></textarea></p>
                </c:if>

                <c:if test="${text.length() != 0}">
                    <p><textarea wrap="hard" rows="3" cols="50" name="${type.name()}">${text}</textarea></p>
                </c:if>
            </dl>

        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>