<%@ page import="ru.mystudies.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <a href='resume?&action=add'>Добавить запись<img src="img/add.png"></a><br></br>
    <div class="table-scroll">
        <table>
            <thead>
            <tr>
                <th>Имя</th>
                <th>Email</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            </tbody>
        </table>
            <div class="table-scroll-body">

                <table>

            <tbody>


    <c:forEach var="resume" items="${resumes}">
    <jsp:useBean id="resume" type="ru.mystudies.basejava.model.Resume"/>
        <tr>
                    <td><a href='resume?uuid=${resume.uuid}&action=view'>${resume.fullName}</a></td>
                    <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%></td>
                    <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                    <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>



</section>
</body>
</html>
