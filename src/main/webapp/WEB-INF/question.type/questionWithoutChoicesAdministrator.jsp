<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 03.08.2019
  Time: 14:31
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
<div class="col" style="background-color: #e3d8d8;">
    <c:if test="${not empty requestScope.activeQuestion}">
        <c:if test="${requestScope.activeGame.numberOfHints < requestScope.activeGame.configuration.maxNumberOfHints}">
            <c:if test="${empty requestScope.activeQuestion.playerAnswer}">
                <div>
                    <form method="post">
                        <div class="text-center" style="margin-top: 12px;"><select id="hintId" name="hint"
                                                                                   class="form-control">
                            <c:forEach var="hint" items="${requestScope.hints}">
                                <option value="${hint.id}">${hint.hintFormulation}</option>
                            </c:forEach>
                        </select></div>
                        <div class="text-center">
                            <button class="btn btn-primary btn-sm" type="submit"
                                    name="sendHint" value="sendHint"
                                    style="height: 29px;width: 344px;margin-top: 2px;"><fmt:message key="send.hint"/>
                            </button>
                        </div>
                    </form>
                </div>
            </c:if>
        </c:if>
        <c:if test="${requestScope.activeGame.numberOfHints >= requestScope.activeGame.configuration.maxNumberOfHints}">
            <div><label><fmt:message key="you.reached.max.number.of.hints"/></label></div>
        </c:if>
        <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="formulation"/></label></div>
        <div><label>${requestScope.activeQuestion.formulation}</label></div>
        <c:if test="${not empty requestScope.showHint}">
            <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="current.hint"/></label></div>
            <div><label>${requestScope.activeQuestion.hintFormulation}</label></div>
        </c:if>
        <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="correct.answer"/></label></div>
        <div><label>${requestScope.activeQuestion.answer}</label></div>
        <c:if test="${not empty requestScope.questionHints}">
            <div style="font-family: Allerta, sans-serif;"><label><fmt:message key="hints"/></label></div>
            <c:forEach var="hint" items="${requestScope.questionHints}">
                <div><label>${hint.hintFormulation}</label></div>
            </c:forEach>
        </c:if>
        <c:if test="${not empty requestScope.activeQuestion.playerAnswer}">
            <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="player.answer"/></label></div>
            <div><label>${requestScope.activeQuestion.playerAnswer}</label></div>
            <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="time.spent.on.answer"/></label></div>
            <div><label>${requestScope.timeSpent}</label></div>
            <c:if test="${empty sessionScope.assessed}">
                <form method="post">
                    <c:if test="${requestScope.timeSpent <= requestScope.activeGame.configuration.time}">
                        <div>
                            <div class="text-center">
                                <button class="btn btn-primary btn-sm" type="submit"
                                        name="assessAnswer"
                                        value="correctAnswer"
                                        style="height: 29px;width: 135px;"><fmt:message key="correct.answer"/>
                                </button>
                                <button class="btn btn-primary btn-sm" type="submit"
                                        name="assessAnswer"
                                        value="incorrectAnswer"
                                        style="height: 29px;margin-left: 25px;width: 135px;"><fmt:message key="incorrect.answer"/>
                                </button>
                            </div>

                        </div>
                    </c:if>
                    <c:if test="${requestScope.timeSpent > requestScope.activeGame.configuration.time}">
                        <div>
                            <div class="text-center">
                                <button class="btn btn-primary btn-sm" type="submit"
                                        name="assessAnswer"
                                        value="incorrectAnswer"
                                        style="height: 29px;margin-left: 25px;width: 135px;"><fmt:message key="incorrect.answer"/>
                                </button>
                            </div>
                        </div>
                    </c:if>
                </form>
            </c:if>
        </c:if>
    </c:if>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
