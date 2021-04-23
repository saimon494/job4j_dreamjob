<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<ul class="nav">
    <c:if test="${not empty user}">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/index.do"/>">Главная</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/posts.do"/>">Вакансии</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/candidates.do"/>">Кандидаты</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/post/edit.jsp"/>">Добавить вакансию</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/candidate/edit.jsp"/>">Добавить кандидата</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/auth.do"/>"> <c:out value="${user.name}"/> | Выйти</a>
        </li>
    </c:if>
    <c:if test="${empty user}">
        <li class="nav-item">
            <a class="nav-link" href='<c:url value="/auth.do"/>'>Войти</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href='<c:url value="/reg.do"/>'>Регистрация</a>
        </li>
    </c:if>
</ul>