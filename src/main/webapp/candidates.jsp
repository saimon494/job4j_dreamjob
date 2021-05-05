<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <jsp:include page="menu.jsp"/>
    </div>
    <div class="row pt-3">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Кандидаты
            </div>
            <div class="card-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col" style="width:20%">Имя</th>
                        <th scope="col" style="width:30%">Фото</th>
                        <th scope="col" style="width:30%">Город</th>
                        <th scope="col" style="width:5%"></th>
                        <th scope="col" style="width:5%"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${candidates}" var="candidate">
                        <tr>
                            <td><c:out value="${candidate.name}"/></td>
                            <td>
                                <div class="card text-center" style="width: 100%">
                                    <img class="card-img-top" src='<c:url value="/photo.do?id=${candidate.photoId}"/>'
                                         alt="Нет фото" width="100" height="auto">
                                    <div class="card-body">
                                        <c:if test="${candidate.photoId == 0}">
                                            <a href='<c:url value="/candidate/photo.jsp?id=${candidate.id}"/>' class="btn btn-primary">Загрузить</a>
                                        </c:if>
                                        <c:if test="${candidate.photoId != 0}">
                                            <a href='<c:url value="/photo.do?id=${candidate.photoId}"/>' class="btn btn-primary">Скачать</a>
                                        </c:if>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <c:forEach items="${cities}" var="city">
                                    <c:if test="${candidate.cityId eq city.id}">
                                        <c:out value="${city.name}"/>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <a href='<c:url value="candidate/edit.jsp?id=${candidate.id}"/>'
                                   title="Редактирование кандидата">
                                    <i class="fa fa-edit mr-3"></i>
                                </a>
                            </td>
                            <td>
                                <a href='<c:url value="/candidate/delete.do?id=${candidate.id}"/>'
                                   title="Удаление кандидата">
                                    <i class="fa fa-trash mr-3"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>