<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 21.07.2019
  Time: 12:14
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
<jsp:include page="WEB-INF/view/headerVisitor.jsp"/>
<main class="page login-page" style="margin-top: 215px;">
    <c:if test="${not empty requestScope.cannotLogin}">
        <div class="text-center" style="margin-top: -50px;"><label style="font-size: 27px;color: rgb(136,6,6);font-family: Anton, sans-serif;"><fmt:message key="login.page.cannot.login"/></label></div>
    </c:if>
    <section class="clean-block clean-form dark" style="margin-top: 80px">
        <div class="container" style="width: 409px;background-color: #ebdada;height: 400px;">
            <div class="block-heading" style="margin-top: -50px">
                <h2 class="text-info"><fmt:message key="login"/></h2>
                <p><fmt:message key="login.page.login.text"/></p>
            </div>
            <form action="${pageContext.request.contextPath}/app/login" method="post" style="margin-bottom: 0px;">
                <br>
                <div class="form-group"><label for="email"><fmt:message key="email"/></label><input class="form-control item" type="email" name="email"
                                                                               id="email"></div>
                <div class="form-group"><label for="password"><fmt:message key="password"/> </label><input class="form-control" name="password"
                                                                                     type="password" id="password">
                </div>
                <br>
                <button class="btn btn-primary btn-block" name="loginUser" value="loginUser" type="submit"><fmt:message key="login"/></button>
            </form>
        </div>
    </section>
</main>
<br>
<br>
<jsp:include page="WEB-INF/view/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
