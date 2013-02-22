<#ftl>
<#import "/spring.ftl" as spring />
<!DOCTYPE HTML>
<html>
<head>
<title>Booksearch</title>
</head>
<body>
<h1><@spring.message code="book.title" /></h1>

<p>

    <form method="POST">
        <fieldset>
            <legend><@spring.message code="book.searchcriteria" /></legend>
            <table>
                <tr><td><@spring.message code="book.title" /></td><td><@spring.formInput "searchCriteria.title" /></td></tr>
                <tr><td><@spring.message code="book.category" /></td><td><@spring.formSingleSelect "searchCriteria.category", categories, "" /></td></tr>
            </table>
        </fieldset>
        <button id="search"><@spring.message code="book.search" /></button>
    </form>

    <#if bookList?has_content>
        <table>
            <tr><th><@spring.message code="book.title"/></th><th><@spring.message code="book.description"/></th><th><@spring.message code="book.price" /></th></tr>
            <#list bookList as book>
                <tr><td>${book.title}</td><td>${book.description}</td><td>${book.price}</td><td><a href="<@spring.url "/cart/add/${book.id}"/>"><@spring.message code="book.addtocart"/></a></tr>
            </#list>
        </table>
    </#if>
</p>

</body>
</html>