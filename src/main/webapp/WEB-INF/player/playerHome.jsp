<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 14.07.2019
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>HomePage</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Acme">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Alegreya+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Alfa+Slab+One">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Allerta">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Anaheim">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Anton">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Fjalla+One">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="message"/>
</head>
<body style="background-image: url(&quot;${pageContext.request.contextPath}/assets/img/background.png&quot;); background-size: cover;background-repeat: no-repeat;">
<jsp:include page="../view/headerPlayer.jsp"/>
<main>
    <section>
        <div class="container">
            <div style="margin: 0px;margin-top: 13rem;margin-bottom: 53px;">
                <div class="text-center" style="margin-bottom: 32px;margin-top: -44px;"><label style="font-size: 27px;"><fmt:message key="statistics.history"/></label></div>
                <div class="table-responsive border-light" style="background-color: #f4dfdf;">
                    <table class="table table-striped">
                        <thead class="border-dark">
                        <tr>
                            <th><fmt:message key="index.page.table.score"/></th>
                            <th><fmt:message key="index.page.table.numberofplayers"/></th>
                            <th><fmt:message key="index.page.table.administrator"/></th>
                            <th><fmt:message key="index.page.table.time"/></th>
                            <th><fmt:message key="index.page.table.number.of.hints"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="statistics" items="${requestScope.statisticsHistory}">
                            <tr>
                                <td>${statistics.playersScore}-${statistics.spectatorsScore}</td>
                                <td>${statistics.configuration.numberOfPlayers}</td>
                                <td>${statistics.administrator.localizedUsers.get(sessionScope.locale).name} ${statistics.administrator.localizedUsers.get(sessionScope.locale).surname}</td>
                                <td>${statistics.configuration.time}</td>
                                <td>${statistics.numberOfHints}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <br>
                <nav aria-label="Navigation for countries">
                    <ul class="pagination">
                        <%--@elvariable id="currentPage" type=""--%>
                        <c:if test="${currentPage != 1}">
                            <li class="page-item"><a class="page-link"
                                                     href="${pageContext.request.contextPath}/app/playerHome?currentPage=${currentPage-1}"><fmt:message key="previous"/></a>
                            </li>
                        </c:if>

                        <%--@elvariable id="noOfPages" type=""--%>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item active"><a class="page-link">
                                            ${i} <span class="sr-only">(current)</span></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/app/playerHome?currentPage=${i}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${currentPage lt noOfPages}">
                            <li class="page-item"><a class="page-link"
                                                     href="${pageContext.request.contextPath}/app/playerHome?currentPage=${currentPage+1}"><fmt:message key="next"/></a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </section>
</main>
<jsp:include page="../view/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
