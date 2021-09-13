package controllers.toppage;

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
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                .setParameter("employee", login_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long reports_count = (long) em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        //※ログインしている従業員のフォローとフォロワー
        long follower_count = (long) em.createNamedQuery("getFollowerCount", Long.class)
              .setParameter("me",login_employee)
                .getSingleResult();
        long followee_count = (long) em.createNamedQuery("getFolloweeCount", Long.class)
                .setParameter("me",login_employee)
                .getSingleResult();
        //ここまで

        em.close();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        //※2行追加
        request.setAttribute("follower_count", follower_count);
        request.setAttribute("followee_count", followee_count);
        //ここまで

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }
}
