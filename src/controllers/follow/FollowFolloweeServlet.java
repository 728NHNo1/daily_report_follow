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
 * Servlet implementation class FollowFolloweeServlet
 */
@WebServlet("/follow/followee")
public class FollowFolloweeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowFolloweeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
        }
        List<Employee> employees = em.createNamedQuery("getAllEffectiveEmployees", Employee.class)
                //45行目と51行目を確認
         //       .setParameter("me", employee_code)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long employees_count = (long) em.createNamedQuery("getAllEffectiveEmployeesCount", Long.class)
             //   .setParameter("me", employee_name)
                .getSingleResult();

        em.close();

        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/followee.jsp");
        rd.forward(request, response);
    }

}
