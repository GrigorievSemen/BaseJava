<%@ page import="ru.mystudies.basejava.model.ContactType" %>
<%@ page import="ru.mystudies.basejava.model.SectionType" %>
<%@ page import="ru.mystudies.basejava.util.HtmlUtil" %>
<%@ page import="ru.mystudies.basejava.util.DateUtil" %>
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
            <dd><input type="text" name="fullName" size=50 placeholder="Ваше Имя" required
                       pattern=" *[A-Za-zА-Яа-яЁё]+( +[A-Za-zА-Яа-яЁё]+[ ]*)*$"
                       title="Поле должно содержать только слова" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <jsp:useBean id="type"
                         type="ru.mystudies.basejava.model.SectionType"/>
            <c:set var="objective" value="<%=SectionType.OBJECTIVE%>"/>
            <c:set var="personal" value="<%=SectionType.PERSONAL%>"/>
            <c:set var="achievement" value="<%=SectionType.ACHIEVEMENT%>"/>
            <c:set var="qualifications" value="<%=SectionType.QUALIFICATIONS%>"/>
            <c:set var="experiense" value="<%=SectionType.EXPERIENCE%>"/>
            <c:set var="education" value="<%=SectionType.EDUCATION%>"/>


            <c:choose>
                <c:when test="${type.equals(objective) || type.equals(personal) ||
             type.equals(achievement) || type.equals(qualifications)}">
                    <dl>
                        <dt>${type.title}</dt>
                        <c:set var="text"
                               value="<%=HtmlUtil.sectionDataToString(type,resume)%>"/>

                        <c:if test="${text.length() == 0}">
                            <p><textarea placeholder="Введите данные" wrap="hard" rows="3" cols="50"

                                         name="${type.name()}"></textarea></p>
                        </c:if>

                        <c:if test="${text.length() != 0}">
                            <p><textarea wrap="hard" rows="3" cols="50" name="${type.name()}">${text}</textarea></p>
                        </c:if>
                    </dl>
                </c:when>

                <c:when test="${type.equals(experiense) || type.equals(education)}">
                    <c:set var="listOrganization" value="<%=HtmlUtil.organizationsList(type,resume)%>"/>

                    <c:forEach var="organization" items="${listOrganization}" varStatus="counter">
                        <dl>
                            <dt>Название учреждения:</dt>
                            <dd><input type="text" placeholder="Название места" name='${type.name()}' size=100
                                       value="${organization.getTitle()}"></dd>
                        </dl>

                        <dl>
                            <dt>Сайт учреждения:</dt>
                            <dd><input type="text" placeholder="Веб ссылка" name='${type.name()}url' size=100
                                       value="${organization.getWebsite()}"></dd>
                            </dd>
                        </dl>
                        <div style="margin-left: 30px">
                            <c:forEach var="period" items="${organization.getPeriods()}">
                                <jsp:useBean id="period" type="ru.mystudies.basejava.model.Period"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${counter.index}${type.name()}startPeriod" size=10
                                               value="${HtmlUtil.getPeriodStart(period)}"
                                               placeholder="YYYY-MM"
                                               pattern="((19[0-9]{2}|20[0-9]{2})-(0[1-9]{1}|1[012]{1}))|[ ]*"
                                               title="Поле должно иметь дату в формате (YYYY-MM) или пустое">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${counter.index}${type.name()}endPeriod" size=10
                                               value="${HtmlUtil.getPeriodEnd(period)}"
                                               placeholder="YYYY-MM"
                                               pattern="((19[0-9]{2}|20[0-9]{2})-(0[1-9]{1}|1[012]{1}))|[ ]*"
                                               title="Поле должно иметь дату в формате (YYYY-MM) или пустое">
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name="${counter.index}${type.name()}position" size=75
                                               value="${period.getPosition()}" placeholder="Занимаемая должность">
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${counter.index}${type.name()}description" rows=5
                                                  cols=75 placeholder="Описание">${period.getDescription()}</textarea>
                                    </dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>

                </c:when>
            </c:choose>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>