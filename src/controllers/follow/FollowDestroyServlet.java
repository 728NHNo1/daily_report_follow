package controllers.follow;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class FollowDestroyServlet
 */
@WebServlet("/follow/destroy")
public class FollowDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        //リクエストパラメータ取得
        Employee followee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        Follow f = new Follow();

        //リクエストパラメータから削除したいインスタンスをテーブルから取得
        List<Follow> follows = em.createNamedQuery("getFollow", Follow.class)
                .setParameter("follower", login_employee)
                .setParameter("followee", followee)
                .getResultList();

        //削除処理
        em.getTransaction().begin();
        em.remove(follows.get(0));
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush","フォローを外しました。");

      //結果画面へリダイレクト
        response.sendRedirect(request.getContextPath() + "/follow/show?status=followed&id="+request.getParameter("id"));
    }

}
