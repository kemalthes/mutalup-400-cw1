<html lang="en">
<#include "base.ftl">
<#macro title>Users</#macro>
<#macro scripts></#macro>
<#macro content>
    <table>
        <caption>Users</caption>
        <tr>
            <th>Login</th>
            <th>Name</th>
            <th>LastName</th>
        </tr>
        <#if users?has_content>
            <#list users as u>
                <tr>
                    <td>${u.login()}</td>
                    <td>${u.name()}</td>
                    <td>${u.lastName()}</td>
                </tr>
            </#list>
        </#if>
        <br>
    </table>

    <form method="post" action="/users">
        <input type="submit" value="Не нажимать!">
    </form>
</#macro>
</html>