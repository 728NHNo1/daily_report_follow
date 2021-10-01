package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesCreateServlet
 */
@WebServlet("/follow/create")
public class FollowCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String _token = request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            //リクエストパラメータを取得
            Employee followee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

            //登録先DTO（Follow）のインスタンスを生成
            Follow f = new Follow();

            //そのインスタンスをDTOインスタンスへセット
            f.setFollowee(followee);
            f.setFollower(login_employee);

            //登録処理
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();

            //結果画面へリダイレクト
            response.sendRedirect(request.getContextPath() + "/follow/show?status=followed&id="+request.getParameter("id"));
        }
    }
}