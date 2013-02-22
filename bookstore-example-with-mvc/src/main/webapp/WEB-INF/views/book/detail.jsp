<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="/resources/images/books/${book.isbn}/book_front_cover.png" var="bookImage"/>
<img src="${bookImage}" align="left" alt="${book.title}" width="250"/>

<table>
    <tr><td>Title</td><td>${book.title}</td></tr>    
    <tr><td>Description</td><td>${book.description}</td></tr>    
    <tr><td>Author</td><td>${book.author}</td></tr>    
    <tr><td>Year</td><td>${book.year}</td></tr>    
    <tr><td>ISBN</td><td>${book.isbn}</td></tr>    
    <tr><td>Price</td><td>${book.price}</td></tr>    
</table>