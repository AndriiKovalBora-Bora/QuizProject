<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 21.07.2019
  Time: 12:27
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
<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container" style="width: 526px;background-color: #c1c1c1;height: 677px;">
            <div class="block-heading" style="margin-top: 200px">
                <h2 class="text-info"><fmt:message key="registration"/></h2>
                <p><fmt:message key="registration.page.text"/></p>
            </div>
            <form action="${pageContext.request.contextPath}/app/registration" method="post">
                <div class="form-group"><label for="nameEN"><fmt:message key="registration.page.name.en"/></label><input class="form-control item" type="text" name="nameEN"
                                                                               id="nameEN"></div>
                <div class="form-group"><label for="nameUA"><fmt:message key="registration.page.name.ua"/></label><input class="form-control item" type="text" name="nameUA"
                                                                               id="nameUA"></div>
                <div class="form-group"><label for="surnameEN"><fmt:message key="registration.page.surname.en"/></label><input class="form-control item" type="text" name="surnameEN"
                                                                                  id="surnameEN"></div>
                <div class="form-group"><label for="surnameUA"><fmt:message key="registration.page.surname.ua"/></label><input class="form-control item" type="text" name="surnameUA"
                                                                                  id="surnameUA"></div>
                <div class="form-group"><label for="email"><fmt:message key="email"/></label><input class="form-control item" type="email" name="email"
                                                                               id="email"></div>
                <div class="form-group"><label for="password"><fmt:message key="password"/></label><input class="form-control item" name="password"
                                                                                     type="password" id="password">
                </div>
                <button class="btn btn-primary btn-block" name="register" value="register" type="submit"><fmt:message key="registration"/></button>
            </form>
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
