<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 30.07.2019
  Time: 21:43
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
<main style="margin-top: 162px;">
    <section>
        <div class="container">
            <div class="text-center"><label style="font-size: 27px;"><fmt:message key="game.results"/></label></div>
            <div class="text-center"><label style="font-family: 'Alegreya Sans', sans-serif;"><fmt:message key="game.was.finished"/>
                <fmt:message key="long.statistics"/></label></div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col">
                    <div class="text-center" style="background-color: #e1d4d4;"><label
                            style="font-family: Allerta, sans-serif;"><fmt:message key="score"/></label></div>
                    <div class="text-center" style="background-color: #e1d4d4;"><label>
                        <fmt:message key="players"/> ${requestScope.lastGame.playersScore} - ${requestScope.lastGame.spectatorsScore}
                        <fmt:message key="spectators"/></label></div>
                    <div class="text-center" style="background-color: #e1d4d4;"><label
                            style="font-family: Allerta, sans-serif;"><fmt:message key="number.of.given.hints"/></label></div>
                    <div class="text-center" style="background-color: #e1d4d4;">
                        <label>${requestScope.lastGame.numberOfHints}</label></div>
                    <div class="text-center" style="background-color: #e1d4d4;"><label
                            style="font-family: Allerta, sans-serif;"><fmt:message key="number.of.players"/></label></div>
                    <div class="text-center" style="background-color: #e1d4d4;">
                        <label>${requestScope.lastGame.configuration.numberOfPlayers}</label></div>
                    <div class="text-center" style="background-color: #e1d4d4;"><label
                            style="font-family: Allerta, sans-serif;"><fmt:message key="time.spent.on.answer"/></label></div>
                    <div class="text-center" style="background-color: #e1d4d4;">
                        <label>${requestScope.lastGame.configuration.time} <fmt:message key="sec"/></label></div>
                </div>
                <div class="col">
                    <c:forEach var="question" items="${requestScope.questions}">
                        <div class="text-center" style="background-color: #e1d4d4;"><label
                                style="font-family: Allerta, sans-serif;"><fmt:message key="question"/></label></div>
                        <div class="text-center" style="background-color: #e1d4d4;">
                            <label>${question.formulation}</label></div>
                        <div class="text-center" style="background-color: #e1d4d4;"><label
                                style="font-family: Allerta, sans-serif;"><fmt:message key="correct.answer"/></label></div>
                        <div class="text-center" style="background-color: #e1d4d4;"><label>${question.answer}</label>
                        </div>
                        <div class="text-center" style="background-color: #e1d4d4;"><label
                                style="font-family: Allerta, sans-serif;"><fmt:message key="player.answer"/></label></div>
                        <div class="text-center" style="background-color: #e1d4d4;">
                            <label>${question.playerAnswer}</label>
                        </div>
                        <c:if test="${not empty question.hints}">
                            <div class="text-center" style="background-color: #e1d4d4;"><label
                                    style="font-family: Allerta, sans-serif;"><fmt:message key="hints"/></label></div>
                            <c:forEach var="hint" items="${question.hints}">
                                <div class="text-center" style="background-color: #e1d4d4;"><label
                                        style="font-family: Allerta, sans-serif;">${hint.typeOfHint}</label></div>
                            </c:forEach>
                        </c:if>
                        <p></p>
                    </c:forEach>
                </div>
            </div>
        </div>
    </section>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
