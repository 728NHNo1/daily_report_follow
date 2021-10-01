<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="../layout/app.jsp">
  <c:param name="content">
    <h2>${employee.name}</h2>

    <%--フォローが0件でフォローをする、0件以上でフォローを外すに変更 --%>
    <%-- <c:set var="a" value="o"/> --%>
    <c:choose>
      <c:when test="${follow_size=='1'}">
        <%--  <c:when test="${a>0}"> --%>
        <form action="<c:url value='/follow/destroy' />" method="get">
          <input type="hidden" name="_token" value="${_token}" /> <input
            type="hidden" name="id" value="${employee.id}" />
          <%--  <form action="<c:url value='/follow/create' />" method="post">--%>
          <button type="submit">フォロー外す</button>
        </form>
      </c:when>
      <c:otherwise>
        <form action="<c:url value='/follow/create' />" method="post">
          <input type="hidden" name="_token" value="${_token}" /> <input
            type="hidden" name="id" value="${employee.id}" />
          <%--  <form action="<c:url value='/follow/create' />" method="post">--%>
          <button type="submit">フォローする</button>
        </form>
      </c:otherwise>
    </c:choose>

    <div id="follower">
      <a href="<c:url value='/follow/follower?id=${employee.id}' />">フォロー${follower_count}人</a>
    </div>

    <div id="followee">
      <a href="<c:url value='/follow/followee?id=${employee.id}' />">フォロワー${followee_count}人</a>
    </div>

    <h3>【${employee.name}の日報 一覧】</h3>
    <table id="report_list">
      <tbody>
        <tr>
          <th class="report_name">氏名</th>
          <th class="report_date">日付</th>
          <th class="report_title">タイトル</th>
          <th class="report_action">操作</th>
        </tr>
        <c:forEach var="report" items="${reports}" varStatus="status">
          <tr class="row${status.count % 2}">
            <td class="report_name"><c:out
                value="${report.employee.name}" /></td>
            <td class="report_date"><fmt:formatDate
                value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
            <td class="report_title">${report.title}</td>
            <td class="report_action"><a
              href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <div id="pagination">
      （全 ${reports_count} 件）<br />
      <c:forEach var="i" begin="1" end="${((reports_count - 1) / 15) + 1}"
        step="1">
        <c:choose>
          <c:when test="${i == page}">
            <c:out value="${i}" />&nbsp;
                    </c:when>
          <c:otherwise>
            <a
              href="<c:url value='/follow/show?id=${employee.id}&page=${i}' />"><c:out
                value="${i}" /></a>&nbsp;
                    </c:otherwise>
        </c:choose>
      </c:forEach>
    </div>
    <p>
      <a href="<c:url value='/reports/new'/>">新規日報の登録</a>
    </p>
    <%--    <c:if test="${sessionScope.login_employee.admin_flag == 1}"> --%>
    <a href="<c:url value='/follow/index' />">フォロー対象従業員一覧</a>
    <%--   </c:if> --%>
  </c:param>
</c:import>