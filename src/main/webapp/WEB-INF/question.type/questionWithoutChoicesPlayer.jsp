<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 04.08.2019
  Time: 14:50
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
<c:if test="${not empty requestScope.activeQuestion}">
    <div class="col" style="background-color: #e3d8d8;">
        <div class="text-center"><label style="font-size: 20px;font-family: 'Alfa Slab One', cursive;"><fmt:message key="active.question"/></label></div>
        <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="formulation"/></label></div>
        <div><label>${requestScope.activeQuestion.formulation}</label></div>
        <c:if test="${not empty requestScope.showHint}">
            <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="current.hint"/></label></div>
            <div><label>${requestScope.activeQuestion.hintFormulation}</label></div>
        </c:if>
        <c:if test="${not empty requestScope.activeQuestion.playerAnswer}">
            <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="correct.answer"/></label></div>
            <div><label>${requestScope.activeQuestion.answer}</label></div>
            <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="player.answer"/></label></div>
            <div><label>${requestScope.activeQuestion.playerAnswer}</label></div>
        </c:if>
        <c:if test="${empty requestScope.activeQuestion.playerAnswer}">
            <div>
                <form method="post" style="width: 289px;"><input class="form-control" name="answer" type="text"
                                                                 style="width: 352px;">
                    <button class="btn btn-primary btn-sm" name="answerQuestion" value="answerQuestion" type="submit"
                            style="width: 352px;margin-top: 6px;"><fmt:message key="give.answer"/>
                    </button>
                </form>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.activeQuestion.playerAnswer}">
            <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="time.spent.on.answer"/></label></div>
            <div><label>${requestScope.timeSpent}</label></div>
        </c:if>
        <c:if test="${not empty requestScope.questionHints}">
            <div style="font-family: Allerta, sans-serif;margin-top: 10px;"><label><fmt:message key="hints"/></label></div>
            <c:forEach var="hint" items="${requestScope.questionHints}">
                <div><label>${hint.hintFormulation}</label></div>
            </c:forEach>
        </c:if>
    </div>
</c:if>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
