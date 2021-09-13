package controllers.follow;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesShowServlet
 */
@WebServlet("/follow/show")
public class FollowShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        long follower_count = (long) em.createNamedQuery("getFollowerCount", Long.class)
                .setParameter("me", e)
                .getSingleResult();
        long followee_count = (long) em.createNamedQuery("getFolloweeCount", Long.class)
                .setParameter("me", e)
                .getSingleResult();

        //フォローするためのもの
        List<Follow> follow = em.createNamedQuery("getFollow", Follow.class)
                .setParameter("follower", login_employee)
                .setParameter("followee", e)
                .getResultList();

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ex) {
            page = 1;
        }
        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                .setParameter("employee", e)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long reports_count = (long) em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", e)
                .getSingleResult();
        em.close();

        request.setAttribute("employee", e);
        request.setAttribute("reports", reports);
        request.setAttribute("page", page);
        request.setAttribute("follower_count", follower_count);
        request.setAttribute("followee_count", followee_count);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("_token", request.getSession().getId());

        //ビューとなるJSPを指定して表示する
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/otherIndex.jsp");
        rd.forward(request, response);
    }

}