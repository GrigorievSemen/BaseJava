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

        <h3>Секции:</h3>
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
                               value="<%=ResumeUtil.sectionDataToString(type,resume)%>"/>

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
                    <c:set var="listOrganization" value="<%=ResumeUtil.organizationsList(type,resume)%>"/>

                    <dl>
                        <dt>${type.title}</dt>
                        <c:if test="${listOrganization.size() == 0}">
                            <div class="titleAndWeb">
                                <p class="editTitle"><textarea class="editTitle" placeholder="Название места"
                                                               wrap="hard"
                                                               required
                                                               pattern=" *[A-Za-zА-Яа-яЁё0-9]+( +[A-Za-zА-Яа-яЁё0-9\s]+[ ]*)*$"
                                                               title="Поле должно быть заполнено"
                                                               rows="1" cols="50"
                                                               name="${type.name()}"></textarea></p>

                                <p class="editWeb="><textarea placeholder="Веб ссылка" wrap="hard" rows="1" cols="50"
                                                              name="${type.name()}"></textarea>
                                </p>
                                <div style="clear: both;"></div>
                            </div>

                            <div class="editPeriod">
                                <p class="editPosition"><textarea placeholder="Занимаемая должность" wrap="hard"
                                                                  rows="1" cols="30"
                                                                  name="${type.name()}"></textarea>
                                </p>

                                <p class="editStartPeriod"><input type="text" name="${type.name()}"
                                                                  value=""
                                                                  size=20
                                                                  required
                                                                  placeholder="Дата в формате YYYY-MM-DD"
                                                                  pattern="(19[0-9]{2}|20[0-9]{2})-(0[1-9]{1}|1[012]{1})"
                                                                  title="Поле должно иметь дату в формате (YYYY-MM)">
                                </p>
                                <p class="editEndPeriod"><input type="text" name="${type.name()}"
                                                                value=""
                                                                size=20 placeholder="Дата в формате YYYY-MM-DD"
                                                                pattern="((19[0-9]{2}|20[0-9]{2})-(0[1-9]{1}|1[012]{1}))|[ ]*"
                                                                title="Поле должно иметь дату в формате (YYYY-MM) или пустое">
                                </p>

                                <p class="editDescription"><textarea placeholder="Описание" wrap="hard" rows="1"
                                                                     cols="100"
                                                                     name="${type.name()}"></textarea>
                                </p>
                            </div>

                        </c:if>

                        <c:if test="${listOrganization.size() != 0}">

                            <c:forEach var="organization" items="${listOrganization}">
                                <div class="titleAndWeb">
                                    <p class="editTitle"><textarea placeholder="Название места" wrap="hard" rows="1"
                                                                   cols="50"
                                                                   required
                                                                   pattern=" *[A-Za-zА-Яа-яЁё0-9]+( +[A-Za-zА-Яа-яЁё0-9\s]+[ ]*)*$"
                                                                   title="Поле должно быть заполнено"
                                                                   name="${type.name()}">${organization.getTitle()}</textarea>
                                    </p>
                                    <p class="editWeb"><textarea placeholder="Веб ссылка" wrap="hard" rows="1" cols="50"
                                                                 name="${type.name()}">${organization.getWebsite()}</textarea>
                                    </p>
                                    <div style="clear: both;"></div>
                                </div>

                                <div class="editPeriod">
                                    <c:forEach var="period" items="${organization.getPeriods()}">
                                        <p class="editPosition"><textarea placeholder="Занимаемая должность" wrap="hard"
                                                                          rows="1" cols="30"
                                                                          name="${type.name()}">${organization.getPosition()}</textarea>
                                        </p>

                                        <p class="editStartPeriod"><input type="text" name="${type.name()}"
                                                                          value="${ResumeUtil.getPeriodStart(period)}"
                                                                          size=20
                                                                          placeholder="Дата в формате YYYY-MM-DD"
                                                                          pattern="(19[0-9]{2}|20[0-9]{2})-(0[1-9]{1}|1[012]{1})"
                                                                          required
                                                                          title="Поле должно иметь дату в формате (YYYY-MM)">
                                        </p>
                                        <p class="editEndPeriod"><input type="text" name="${type.name()}"
                                                                        value="${ResumeUtil.getPeriodEnd(period)}"
                                                                        size=20 placeholder="Дата в формате YYYY-MM-DD"
                                                                        pattern="((19[0-9]{2}|20[0-9]{2})-(0[1-9]{1}|1[012]{1}))|[ ]*"
                                                                        title="Поле должно иметь дату в формате (YYYY-MM) или пустое">
                                        </p>

                                        </p>


                                        <p class="editDescription"><textarea placeholder="Описание" wrap="hard" rows="1"
                                                                             cols="100"
                                                                             name="${type.name()}">${period.getDescription()}</textarea>
                                        </p>


                                    </c:forEach><br/>
                                </div>


                            </c:forEach>
                        </c:if>


                    </dl>

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