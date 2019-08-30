<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 21.07.2019
  Time: 13:30
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
<jsp:include page="../view/headerAdministrator.jsp"/>
<body style="background-image: url(&quot;${pageContext.request.contextPath}/assets/img/background.png&quot;); background-size: cover;background-repeat: no-repeat;">
<main style="margin-top: 162px;">
    <section>
        <c:if test="${not empty requestScope.wrongParameters}">
            <div class="text-center" style="margin-top: -50px;"><label style="font-size: 27px;color: rgb(136,6,6);font-family: Anton, sans-serif;"><fmt:message key="administrator.configuration.page.wrong.parameters"/></label></div>
        </c:if>
        <div class="row" style="width: 1200px;">
            <div class="col text-right text-sm-right text-md-right text-lg-right text-xl-right"
                 style="margin-left: 9rem;">
                <div style="margin-right: -115px;">
                    <div class="text-center"><label
                            style="font-size: 27px;"><fmt:message key="configuration"/></label></div>
                    <div class="text-center overflow-auto" style="height: 400px;width: 450px;margin-left: 112px;">
                        <p class="text-break text-left" style="background-color: #e1d4d4;">
                            <c:forEach var="configuration" items="${requestScope.configurations}">
                                <fmt:message key="time.for.answer"/>: ${configuration.time} | <fmt:message key="players"/> : ${configuration.numberOfPlayers} | <fmt:message key="max.score"/> : ${configuration.maxScore} | <fmt:message key="max.number.of.hints"/> : ${configuration.maxNumberOfHints} | <fmt:message key="statistics.after.game.format"/> : ${configuration.statisticsFormatFormulation}
                                <br>
                                <br>
                            </c:forEach>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-auto text-left text-sm-left text-md-left text-lg-left text-xl-left"
                 style="margin-right: 0;">
                <div class="container" style="width: 400px;">
                    <div class="text-center"><label style="font-size: 27px;"><fmt:message key="new.configuration"/></label></div>
                    <div class="text-center"><label style="font-family: 'Alegreya Sans', sans-serif;"><fmt:message key="new.configuration.page.text"/></label></div>
                </div>
                <div class="container" style="background-color: #e1d4d4;width: 320px;height: 760px;">
                    <form method="post">
                        <div class="text-center"><label style="font-family: Allerta, sans-serif;"><fmt:message key="time.for.answer"/></label></div>
                        <div class="text-center" style="margin-bottom: 20px;"><input class="form-control" name="time"
                                                                                     type="text"></div>
                        <div class="text-center"><label style="font-family: Allerta, sans-serif;"><fmt:message key="number.of.players"/></label></div>
                        <div class="text-center"><input class="form-control" type="text" name="numberOfPlayers"
                                                        style="margin-bottom: 20px;"></div>
                        <div class="text-center"><label style="font-family: Allerta, sans-serif;"><fmt:message key="max.score"/></label>
                        </div>
                        <div class="text-center"><input class="form-control" type="text" name="maxScore"
                                                        style="margin-bottom: 20px;"></div>
                        <div class="text-center"><label style="font-family: Allerta, sans-serif;"><fmt:message key="max.number.of.hints"/></label></div>
                        <div class="text-center"><input class="form-control" type="text" name="numberOfHints"
                                                        style="margin-bottom: 20px;"></div>
                        <div class="text-center"><label style="font-family: Allerta, sans-serif;"><fmt:message key="statistics.after.game.format"/></label></div>
                        <div class="text-center">
                            <select class="form-control" name="statisticsFormat" style="margin-bottom: 20px;">
                                <c:forEach var="statisticsFormat" items="${requestScope.statisticsFormats}">
                                    <option value="${statisticsFormat}">${statisticsFormat.localizedName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div
                                class="text-center"><label style="font-family: Allerta, sans-serif;"><fmt:message key="type.of.hint.question.without"/></label></div>
                        <div class="text-center">
                            <select class="form-control" name="hintsWithoutChoices" multiple=""
                                    style="margin-bottom: 20px; height: 50px;">
                                <c:forEach var="hint" items="${requestScope.hintsWithoutChoices}">
                                    <option value="${hint.id}">${hint.hintFormulation}</option>
                                </c:forEach>
                            </select></div>
                        <div class="text-center"><label style="font-family: Allerta, sans-serif;"><fmt:message key="type.of.hint.question.with"/></label></div>
                        <div class="text-center" style="margin-bottom: 20px;">
                            <select class="form-control" name="hintsWithChoices" multiple=""
                                    style="margin-bottom: 20px; height: 50px;">
                                <c:forEach var="hint" items="${requestScope.hintsWithChoices}">
                                    <option value="${hint.id}">${hint.hintFormulation}</option>
                                </c:forEach>
                            </select></div>
                        <div class="text-center" style="margin-bottom: 20px;">
                            <button class="btn btn-primary" type="submit" name="newConfiguration"
                                    value="newConfiguration"><fmt:message key="new.configuration"/>
                            </button>
                        </div>
                    </form>
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
