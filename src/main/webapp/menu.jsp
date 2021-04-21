<%@ page contentType="text/html; charset=UTF-8" %>
<ul class="nav">
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Главная</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/editpost.do">Добавить вакансию</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
    </li>
    <c:if test="${empty user}">
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
        </li>
    </c:if>
    <c:if test="${not empty user}">
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
        </li>
    </c:if>
</ul>