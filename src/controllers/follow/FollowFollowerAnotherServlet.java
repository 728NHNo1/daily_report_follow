package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class FollowFollowerAnother
 */
@WebServlet("/Follow/FollowerAnother")
public class FollowFollowerAnotherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowFollowerAnotherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());

        EntityManager em = DBUtil.createEntityManager();

        //  Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException ex) {
        }

        //他人がフォローしている人の名前

        //他人がフォローしている人の人数

        //他人のフォローしている人との関係

        request.setAttribute("employee", e);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/followerAnother.jsp");
        rd.forward(request, response);

    }

}
