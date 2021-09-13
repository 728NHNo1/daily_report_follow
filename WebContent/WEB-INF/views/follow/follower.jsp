<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../layout/app.jsp">
  <c:if test="${flush != null}">
    <div id="flush_sucsses">
      <c:out value="${flush }"></c:out>
    </div>
  </c:if>
  <h2>${employee.name}フォロー一覧</h2>
  <table id="employee_list2">
    <tbody>
      <tr>
        <th>氏名</th>
        <th>フォロー関係</th>
      </tr>
      <c:forEach var="employee" items="${employees}" varStatus="status">
        <tr class="row${status.count % 2 }">
          <td><a href="<c:url value='/follow/show?id=${employee.id}' />">${employee.name}</a>
          </td>
          <td><c:choose>
              <c:when test="条件">
                <p>フォロー中</p>
              </c:when>
              <c:otherwise>
                <p>未フォロー</p>
              </c:otherwise>
            </c:choose></td>
      </c:forEach>
    </tbody>
  </table>

  <div id="pagination">
    （全 ${employees_count} 件）<br />
    <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15) + 1}"
      step="1">
      <c:choose>
        <c:when test="${i == page}">
          <c:out value="${i}" />&nbsp;
                    </c:when>
        <c:otherwise>
          <a href="<c:url value='/employees/index?page=${i}' />"><c:out
              value="${i}" /></a>&nbsp;
                    </c:otherwise>
      </c:choose>
    </c:forEach>
  </div>
  <p>
    <a href="<c:url value='/report/new' />">新規日報の登録</a>
  </p>
</c:import>