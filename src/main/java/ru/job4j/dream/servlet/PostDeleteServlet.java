package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            Post post = PsqlStore.instOf().findPostById(Integer.parseInt(id));
            PsqlStore.instOf().delete(post);
        }
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
