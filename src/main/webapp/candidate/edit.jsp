<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script>
        function validate() {
            var name = $("#candidate_name").val();
            if (name === "") {
                alert("Введите имя кандидата\n");
                return  false;
            }
            return true;
        }

        $(function() {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/cities',
            }).done(function(data) {
                var city = document.querySelector('#city');
                var mas = JSON.parse(data);
                $.each(mas, function (i, el) {
                    const optionElement = document.createElement('option');
                    optionElement.value = el.id;
                    optionElement.innerText = el.name;
                    city.append(optionElement);
                });
            }).fail(function(err){
                alert(err);
            })
        });
    </script>
    <title>Работа мечты</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "");
    if (id != null) {
        candidate = PsqlStore.instOf().findCandidateById(Integer.parseInt(id));
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
                Новый кандидат
                <% } else { %>
                Редактирование кандидата
                <% } %>
            </div>
            <div class="card-body col-12">
                <form class="form-group row" action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" method="post">
                    <div class="form-group col-4">
                        <label for="candidate_name" class="col-form-label">Имя</label>
                        <input type="text" class="form-control" name="name" id="candidate_name"
                               value="<%=candidate.getName()%>">
                    </div>
                    <div class="form-group col-4">
                        <label for="city" class="col-form-label">Город</label>
                        <select class="form-control" id="city" name="city_id"></select>
                    </div>
                    <div class="form-group col-2" style="padding-top:38px">
                        <button type="submit" class="btn btn-primary" onclick="return validate()">Сохранить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>