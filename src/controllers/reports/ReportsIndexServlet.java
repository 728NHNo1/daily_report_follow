package controllers.reports;

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
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    //EntityManager取得
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //リクエストページを取得し、int型へ変換
        int page;
        //日報一覧をページ番号に基づいて取得
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        //フォローしている人のにを取得
        List<Report> followerReports = em.createNamedQuery("getFollowerReports", Report.class)
                .setParameter("login_employee", login_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        //フォローしている人の日報の数を取得
        long reports_count = (long) em.createNamedQuery("getFollowerReportsCount", Long.class)
                .setParameter("login_employee", login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("followerReports", followerReports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("reports", followerReports);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
    }

}
