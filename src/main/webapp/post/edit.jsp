<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page contentType="text/html; charset=UTF-8" %>
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
    <script>
        function validate() {
            var name = $("#post_name").val();
            var description = $("#description").val();
            if (name === "") {
                alert("Введите название вакансии\n");
                return false;
            }
            if (description === "") {
                alert("Введите описание вакансии\n");
                return false;
            }
            return true;
        }
    </script>
    <title>Работа мечты</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Post post = new Post(0, "", "");
    if (id != null) {
        post = PsqlStore.instOf().findPostById(Integer.parseInt(id));
    }
%>
<div class="container pt-3">
    <div class="row">
        <jsp:include page="../menu.jsp"/>
    </div>
    <div class="row pt-3">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новая вакансия
                <% } else { %>
                Редактирование вакансии
                <% } %>
            </div>
            <div class="card-body col-12">
                <form class="form-group row" action="<%=request.getContextPath()%>/posts.do?id=<%=post.getId()%>"
                      method="post">
                    <div class="form-group col-4">
                        <label for="post_name" class="col-form-label">Имя</label>
                        <input type="text" class="form-control" name="name" id="post_name"
                               value="<%=post.getName()%>">
                    </div>
                    <div class="form-group col-8">
                        <label for="description" class="col-form-label">Описание</label>
                        <input type="text" class="form-control" name="description" id="description"
                               value="<%=post.getDescription()%>">
                    </div>
                    <div class="form-group col-2 pt-3">
                        <button type="submit" class="btn btn-primary" onclick="return validate()">Сохранить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>