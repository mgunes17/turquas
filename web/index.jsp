<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mustafa
  Date: 22.05.2017
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Turquas</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
    <div class="jumbotron container-fluid">
        <div class="row">
            <div class="col-md-4" align="left">
                <img src="bird.png" width="200px" height="200px"/>
            </div>
            <div class="col-md-4">
                <h2 class="text-center">TURQUAS</h2>
                <h4 class="text-center">Türkçe İçerikleri İçin Soru Cevaplama Sistemi</h4>
            </div>
            <div class="col-md-4" align="right">
                <img src="bird.png" width="200px" height="200px"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
            </div>
            <div class="col-md-4">
                <form method="POST" action="/findinganswers">
                    <div class="form-group">
                        <label for="question">Sor bakalım..</label>
                        <input type="text" id="question" class="form-control" name="question" value="${question}">
                        <input type="submit" class="btn btn-primary center-block" value="Cevapla"/>
                    </div>
                </form>
            </div>
        </div>
        <c:choose>
            <c:when test="${state eq 2}">
                <div class="alert alert-danger">
                    Lütfen geçerli bir soru giriniz
                </div>
            </c:when>
            <c:when test="${state eq 1}">
                <div class="row">
                    <div class="col-md-6">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Benzerlik Oranı</th>
                                <th>Soruların W2V Benzerliği</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${similarityList}">
                                <tr>
                                    <td>${item.value}</td>
                                    <td>${item.questionForCompare.answer}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Benzerlik Oranı</th>
                                <th>Soru-Cevap Çiftleri(Derin Öğrenme)</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${learningList}">
                                <tr>
                                    <td>${item.value}</td>
                                    <td>${item.questionForCompare.answer}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </div>
    </body>
</html>
