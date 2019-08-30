<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 01.08.2019
  Time: 13:32
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
<jsp:include page="../../view/headerAdministrator.jsp"/>
<body style="background-image: url(&quot;${pageContext.request.contextPath}/assets/img/background.png&quot;); background-size: cover;background-repeat: no-repeat;">
<main style="margin-top: 162px;">
    <section>
        <div class="container" style="width: 596px;">
            <div class="text-center"><label style="font-size: 27px;"><fmt:message key="creating.game"/></label></div>
            <div class="text-center"><label><fmt:message key="game.creating.text"/></label></div>
            <div>
                <form method="post">
                    <div class="text-center" style="padding: 0px;padding-top: 12px;">
                        <select id="configurationId" name="configuration" class="form-control">
                            <c:forEach var="configuration" items="${requestScope.configurations}">
                                <option value="${configuration.id}"><fmt:message key="time.for.answer"/>: ${configuration.time} | <fmt:message key="players"/> : ${configuration.numberOfPlayers} | <fmt:message key="max.score"/> : ${configuration.maxScore} | <fmt:message key="max.number.of.hints"/> : ${configuration.maxNumberOfHints} | <fmt:message key="statistics.after.game.format"/> : ${configuration.statisticsFormatFormulation}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="text-center" style="padding-top: 28px;">
                        <button class="btn btn-primary" type="submit"
                                name="chooseConfiguration" value="chooseConfiguration"
                                style="width: 170px;height: 78px;font-size: 38px;"><fmt:message key="next"/>
                        </button>
                    </div>
                </form>
            </div>
            <div class="text-center" style="padding-top: 22px;">
                <form action="${pageContext.request.contextPath}/app/administratorConfiguration" method="post">
                    <button class="btn btn-primary" type="submit" name="goToNewConfiguration" value="goToNewConfiguration"><fmt:message key="new.configuration"/></button>
                </form>
            </div>
        </div>
    </section>
</main>
<jsp:include page="../../view/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
