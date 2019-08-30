<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 01.08.2019
  Time: 15:01
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
        <div class="container">
            <div class="text-center"><label style="font-size: 27px;"><fmt:message key="game"/></label></div>
            <div class="text-center"><label style="font-size: 25px;"><fmt:message key="players"/>&nbsp;</label><label
                    style="font-size: 25px;letter-spacing: 0px;font-family: 'Alfa Slab One', cursive;">${requestScope.activeGame.playersScore}</label><label
                    style="font-size: 30px;font-family: 'Alfa Slab One', cursive;">&nbsp;-&nbsp;</label>
                <label
                        style="font-size: 25px;font-family: 'Alfa Slab One', cursive;">${requestScope.activeGame.spectatorsScore}</label><label
                        style="font-size: 25px;">&nbsp;<fmt:message key="spectators"/></label></div>
            <c:if test="${not empty requestScope.time}">
                <div class="text-center"><label
                        style="font-size: 28px;font-family: 'Fjalla One', sans-serif;"><fmt:message key="time.left"/> :&nbsp;</label>
                    <label style="font-family: 'Fjalla One', sans-serif;font-size: 28px;color: rgb(136,6,6);"
                           id="timer"></label>
                    <label style="font-family: 'Fjalla One', sans-serif;font-size: 28px;color: rgb(136,6,6);"><fmt:message
                            key="sec"/></label>
                </div>
            </c:if>
            <div
                    class="text-center">
                <form method="post"><button class="btn btn-primary" name="refresh" value="refresh" type="submit" style="width: 252px;background-color: rgb(16,115,20);font-size: 24px;"><fmt:message key="refresh"/></button></form>
            </div>
            <div>
                <div class="row">
                    <div class="col" style="background-color: #e3d8d8;">
                        <c:if test="${not empty requestScope.activeQuestion}">
                            <div class="text-center"><label
                                    style="font-size: 20px;font-family: 'Alfa Slab One', cursive;"><fmt:message
                                    key="active.question"/></label></div>
                            <c:if test="${not empty requestScope.activeQuestion.playerAnswer}">
                                <c:if test="${not empty sessionScope.assessed}">
                                    <div>
                                        <form method="post">
                                            <div class="text-center"><select id="questionIdd" name="question"
                                                                             class="form-control">
                                                <c:forEach var="question" items="${requestScope.freeQuestions}">
                                                    <option value="${question.getId()}">${question.formulation}</option>
                                                </c:forEach>
                                            </select></div>
                                            <div class="text-center">
                                                <button class="btn btn-primary btn-sm text-center" type="submit"
                                                        name="askQuestion" value="Ask Question"
                                                        style="height: 29px;width: 348px;margin-top: 2px;"><fmt:message
                                                        key="send.question"/>
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </c:if>
                            </c:if>
                        </c:if>
                        <c:if test="${empty requestScope.activeQuestion}">
                            <div class="text-center"><label
                                    style="font-size: 20px;font-family: 'Alfa Slab One', cursive;"><fmt:message
                                    key="no.active.question"/></label></div>
                            <div>
                                <form method="post">
                                    <div class="text-center"><select name="question" class="form-control">
                                        <c:forEach var="question" items="${requestScope.freeQuestions}">
                                            <option value="${question.getId()}">${question.formulation}</option>
                                        </c:forEach>
                                    </select></div>
                                    <div class="text-center">
                                        <button class="btn btn-primary btn-sm text-center" type="submit"
                                                name="askQuestion" value="Ask Question"
                                                style="height: 29px;width: 348px;margin-top: 2px;"><fmt:message
                                                key="send.question"/>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </c:if>
                        <jsp:include page="${requestScope.typeOfQuestionPage}"/>
                    </div>
                    <div class="col" style="background-color: #c6b8b8;">
                        <div class="text-center"><label
                                style="font-size: 20px;font-family: 'Alfa Slab One', cursive;"><fmt:message
                                key="chat"/></label>
                        </div>
                        <div class="text-center overflow-auto" style="height: 300px;">
                            <p class="text-break text-left">
                                <c:forEach var="message" items="${requestScope.chat}">
                                    ${message}
                                    <br>
                                </c:forEach></p>
                        </div>
                        <div>
                            <form method="post">
                                <div>
                                    <div class="form-row" style="margin-top: 12px;">
                                        <div class="col"><input class="form-control" type="text" name="message"
                                                                style="width: 276px;" autofocus=""></div>
                                        <div class="col">
                                            <button class="btn btn-primary" type="submit" name="sendMessage"
                                                    value="sendMessage"><fmt:message key="send"/></button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col" style="background-color: #e3d8d8;">
                        <div class="text-center"><label
                                style="font-size: 20px;font-family: 'Alfa Slab One', cursive;"><fmt:message
                                key="configuration"/></label>
                        </div>
                        <div><label style="font-family: Allerta, sans-serif;font-size: 16px;"><fmt:message
                                key="time.for.answer"/></label>
                        </div>
                        <div><label>${requestScope.configuration.time} <fmt:message key="sec"/></label></div>
                        <div><label style="font-family: Allerta, sans-serif;"><fmt:message
                                key="number.of.players"/></label></div>
                        <div><label>${requestScope.configuration.numberOfPlayers}</label></div>
                        <div><label style="font-family: Allerta, sans-serif;"><fmt:message key="max.score"/></label>
                        </div>
                        <div><label>${requestScope.configuration.maxScore}</label></div>
                        <div><label style="font-family: Allerta, sans-serif;"><fmt:message
                                key="max.number.of.hints"/></label></div>
                        <div><label>${requestScope.configuration.maxNumberOfHints}</label></div>
                        <div><label style="font-family: Allerta, sans-serif;"><fmt:message
                                key="statistics.after.game.format"/></label></div>
                        <div><label>${requestScope.configuration.statisticsFormatFormulation}</label></div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<br>
<br>
<jsp:include page="../../view/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script>
    var deadline = new Date().getTime() + ${requestScope.time};
    var x = setInterval(function () {
        var now = new Date().getTime();
        var t = deadline - now;
        var seconds = Math.floor(t / 1000);
        document.getElementById("timer").innerHTML = seconds + " ";
        if (t < 0) {
            clearInterval(x);
            document.getElementById("timer").innerHTML = "0 ";
        }
    }, 1000);
</script>
</body>
</html>
