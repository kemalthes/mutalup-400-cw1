<html lang="en">
<#include "base.ftl">
<#macro title>Users</#macro>
<#macro content>
    <#if users?has_content>
        Таблица рекордов:
        <br>
        Имя очки
        <br>
        <#list users as u>
            ${u.name()?string} ${u.score()?c}
            <br>
        </#list>
    </#if>
    <br>
    <form method="post" action="/users">
        <input type="submit" value="Не нажимать!">
    </form>
</#macro>
</html>