<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andy1
  Date: 21.07.2019
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>HomePage</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="message"/>
</head>

<body>
<div class="fixed-top">
    <nav class="navbar navbar-light navbar-expand-md" style="background-color: #f2e6e6;">
        <div class="container-fluid"><a class="navbar-brand" href="#"><img
                src="${pageContext.request.contextPath}/assets/img/2.png" style="width: 229px;"></a>
            <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span
                    class="navbar-toggler-icon"></span></button>
            <div
                    class="collapse navbar-collapse" id="navcol-1">
                <form class="form-inline" style="position: fixed;right: 3rem;">
                    <ul class="nav navbar-nav text-right text-sm-right text-md-right text-lg-right text-xl-right ml-auto">
                        <li class="nav-item" role="presentation">
                            <div class="form-check" style="margin-right: 20px;"><input class="form-check-input"
                                                                                       type="radio" id="formCheck-1"
                                                                                       name="language" value="english"
                                                                                       onclick="this.form.submit()"
                                                                                       checked=""
                                                                                       style="opacity: 0;"><label
                                    class="form-check-label" for="formCheck-1" style="cursor: pointer;">EN</label></div>
                        </li>
                        <li class="nav-item" role="presentation">
                            <p>|</p>
                        </li>
                        <li class="nav-item" role="presentation">
                            <div class="form-check"><input class="form-check-input" type="radio" id="formCheck-2"
                                                           name="language" value="ukrainian"
                                                           onclick="this.form.submit()" style="opacity: 0;"><label
                                    class="form-check-label" for="formCheck-2" style="cursor: pointer;">UA</label></div>
                        </li>
                    </ul>
                </form>
                <form class="form-inline" action="${pageContext.request.contextPath}/app/logout" method="post"
                      style="position: fixed;right: 9rem;margin-top: -5px;">
                    <ul class="nav navbar-nav text-right text-sm-right text-md-right text-lg-right text-xl-right ml-auto">
                        <li class="nav-item" role="presentation" style="margin-right: 32px;">
                            <div>
                                <div class="col"><label class="col-form-label">${sessionScope.email}</label></div>
                                <div class="col text-center">
                                    <button class="btn btn-primary" type="submit"><fmt:message
                                            key="logout"/></button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </nav>
    <nav class="navbar navbar-light navbar-expand-md" style="background-color: #bfb4b4;background-repeat: no-repeat;">
        <div class="container-fluid">
            <div class="collapse navbar-collapse text-center" id="navcol-2">
                <ul class="nav navbar-nav text-center mx-auto">
                    <li class="nav-item text-center" role="presentation"><a class="nav-link active"
                                                                            href="${pageContext.request.contextPath}/app/administratorHome"><fmt:message
                            key="header.home"/></a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link"
                                                                href="${pageContext.request.contextPath}/app/administratorProfile"><fmt:message
                            key="header.profile"/></a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link"
                                                                href="${pageContext.request.contextPath}/app/administratorGame"><fmt:message
                            key="header.game"/></a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link"
                                                                href="${pageContext.request.contextPath}/app/administratorConfiguration"><fmt:message
                            key="header.configuration"/></a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>

</html>
