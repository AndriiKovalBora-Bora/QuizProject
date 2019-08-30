<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 21.07.2019
  Time: 13:12
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
<main style="margin-top: 162px;">
    <section>
        <div class="container">
            <div class="text-center"><label style="font-size: 27px;"><fmt:message key="personal.profile"/></label></div>
            <div>
                <div class="row">
                    <div class="col">
                        <div class="text-center"><label style="font-size: 20px;font-family: 'Alfa Slab One', cursive;"><fmt:message key="personal.data"/></label></div>
                        <div style="margin: 0px; margin-top: 2rem; margin-bottom: 53px;width: 273px;background-color: #e1d4d4;margin-left: 140px;">
                            <div><label style="font-family: Allerta, sans-serif;margin-left: 15px;"><fmt:message key="name"/></label></div>
                            <div><label style="margin-left: 20px;">${requestScope.player.name}</label></div>
                            <div><label style="font-family: Allerta, sans-serif;margin-left: 15px;"><fmt:message key="surname"/></label>
                            </div>
                            <div><label style="margin-left: 20px;">${requestScope.player.surname}</label></div>
                            <div><label style="font-family: Allerta, sans-serif;margin-left: 15px;"><fmt:message key="email"/></label>
                            </div>
                            <div><label style="margin-left: 20px;">${requestScope.player.email}</label></div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="text-center"><label style="font-size: 20px;font-family: 'Alfa Slab One', cursive;"><fmt:message key="statistics.history"/></label></div>
                        <div class="table-responsive" style="background-color: #f4dfdf; margin-top: 2rem;">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th><fmt:message key="index.page.table.score"/></th>
                                    <th><fmt:message key="index.page.table.numberofplayers"/></th>
                                    <th><fmt:message key="index.page.table.administrator"/></th>
                                    <th><fmt:message key="index.page.table.number.of.hints"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="statistics" items="${requestScope.statisticsHistory}">
                                    <tr>
                                        <td>${statistics.playersScore}-${statistics.spectatorsScore}</td>
                                        <td>${statistics.configuration.numberOfPlayers}</td>
                                        <td>${statistics.administrator.localizedUsers.get(sessionScope.locale).name} ${statistics.administrator.localizedUsers.get(sessionScope.locale).surname}</td>
                                        <td>${statistics.numberOfHints}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<br>
<br>
<jsp:include page="../view/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>

</html>
