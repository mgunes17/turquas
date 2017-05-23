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
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <h2>TURQUAS</h2>
                <h4>Türkçe İçerikleri İçin Soru Cevaplama Sistemi</h4>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <form method="POST" action="/findinganswers">
                    <div class="form-group">
                        <label for="question">Sor bakalım..</label>
                        <input type="text" id="question" class="form-control" name="question">
                    </div>
                    <input type="submit" class="btn btn-primary" value="Cevapla"/>
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
                    <div class="col-md-4">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Soruların W2V Benzerliği</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${similarityList}">
                                <tr>
                                    <td>${item.questionForCompare.answer}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-4">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Soru-Cevap Çiftleri(Derin Öğrenme)</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${learningList}">
                                <tr>
                                    <td>${item.questionForCompare.answer}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-4">
                        <h4>Named Entity(Derin Öğrenme)</h4>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </div>
    </body>
</html>
