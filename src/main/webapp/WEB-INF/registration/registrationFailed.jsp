<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 18.08.2019
  Time: 18:55
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
<jsp:include page="../view/headerVisitor.jsp"/>
<main style="margin-top: 162px;">
    <section>
        <div class="container">
            <div class="text-center"><label style="font-size: 27px;color: rgb(136,6,6);font-family: Anton, sans-serif;"><fmt:message key="registration.page.registration.failed"/></label></div>
            <div>
                <form action="${pageContext.request.contextPath}/app/login" method="post" class="text-center"><button class="btn btn-primary" type="submit"><fmt:message key="login"/></button></form>
            </div>
            <div style="margin-top: 16px;">
                <form action="${pageContext.request.contextPath}/app/registration" method="post" class="text-center"><button class="btn btn-primary" type="submit"><fmt:message key="registration"/></button></form>
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
