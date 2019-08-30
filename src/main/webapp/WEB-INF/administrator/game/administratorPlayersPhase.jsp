<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 01.08.2019
  Time: 14:03
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
            <div class="text-center"><label style="font-size: 27px;"><fmt:message key="creating.game"/></label></div>
            <div class="text-center"><label><fmt:message key="choose.players.text"/></label></div>
            <form method="post">
                <div class="text-center" style="padding: 0px;padding-top: 12px;">
                    <div class="form-row">
                        <div class="col" style="margin-left: 30px;">
                            <select id="configurationId" name="gamePlayers" class="form-control" multiple=""
                                    style="height: 180px;padding-right: 0px;margin-left: 0px;margin-right: 0px;">
                                <c:forEach var="freePlayer" items="${requestScope.freePlayers}">
                                    <option value="${freePlayer.id}">${freePlayer.name} ${freePlayer.surname}
                                        | ${freePlayer.email} | <fmt:message key="online"/></option>
                                </c:forEach>
                            </select></div>
                        <div
                                class="col">
                            <div class="text-left"><label
                                    style="font-size: 25px;background-color: #f4edf3;margin-left: 52px;"><fmt:message key="player.required"/></label></div>
                            <div class="text-left"><label class="text-center"
                                                          style="font-size: 60px;background-color: #f1eaea;width: 92px;margin-left: 106px;">${requestScope.numberOfPlayers}</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="text-center" style="padding-top: 28px;">
                    <button class="btn btn-primary" type="submit" name="choosePlayers" value="choosePlayers"
                            style="width: 170px;height: 78px;font-size: 38px;"><fmt:message key="next"/>
                    </button>
                </div>
            </form>
        </div>
    </section>
</main>
<jsp:include page="../../view/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
