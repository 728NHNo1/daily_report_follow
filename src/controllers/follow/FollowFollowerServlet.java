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
import utils.DBUtil;

/**
 * Servlet implementation class FollowFollowerServlet
 */
@WebServlet("/follow/follower")
public class FollowFollowerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowFollowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

      //   Employee i = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");


        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
        }

        List<Employee> employees = em.createNamedQuery("getFollower", Employee.class)
                .setParameter("me", login_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long employees_count = (long) em.createNamedQuery("getFollowerCount", Long.class)
                .setParameter("me", login_employee)
                .getSingleResult();

        List<Employee> employee = em.createNamedQuery("getAllEmployees", Employee.class)
               // .getParameter("employee")
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
               .getResultList();

        em.close();

        request.setAttribute("login_employee", login_employee);
        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);
        request.setAttribute("employee", employee);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/follower.jsp");
        rd.forward(request, response);

    }
}
