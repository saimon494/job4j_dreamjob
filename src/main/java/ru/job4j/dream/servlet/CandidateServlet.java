package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = PsqlStore.instOf();
        req.setAttribute("candidates", new ArrayList<>(store.findAllCandidates()));
        req.setAttribute("cities", new ArrayList<>(store.findAllCities()));
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Candidate candidate = new Candidate(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"));
        candidate.setCityId(Integer.parseInt(req.getParameter("city_id")));
        PsqlStore.instOf().save(candidate);
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
