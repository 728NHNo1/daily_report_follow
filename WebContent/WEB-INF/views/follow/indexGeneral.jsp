<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../layout/app.jsp">
  <c:param name="content">
    <c:if test="${flush != null}">

      <div id="flush_success">
        <c:out value="${flush}"></c:out>
      </div>
    </c:if>
    <h2>フォロー対象従業員 一覧</h2>
    <table id="employee_list2">
      <tbody>
        <tr>
          <th>社員番号</th>
          <th>氏名</th>
        </tr>
        <c:forEach var="employee" items="${employees}" varStatus="status">
          <tr class="row${status.count % 2}">
                <td>${employee.code}</td>
            <td>
                  <a href="<c:url value='/follow/show?id=${employee.id}' />">${employee.name}</a>
              </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <div id="pagination">
      （全 ${employees_count} 件）<br />
      <c:forEach var="i" begin="1"
        end="${((employees_count - 1) / 15) + 1}" step="1">
        <c:choose>
          <c:when test="${i == page}">
            <c:out value="${i}" />&nbsp;
                    </c:when>
          <c:otherwise>
            <a href="<c:url value='/employee/indexGeneral?page=${i}' />"><c:out
                value="${i}" /></a>&nbsp;
                    </c:otherwise>
        </c:choose>
      </c:forEach>
    </div>
    <p>
      <a href="<c:url value='/index.html' />">アカウント画面に戻る</a>
    </p>
  </c:param>
</c:import>