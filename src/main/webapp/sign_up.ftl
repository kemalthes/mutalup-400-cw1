<html lang="ru">
<#include "base.ftl">
<#macro title>Sign Up</#macro>
<#macro content>
    <div id="sign_up_form">
        <form method="post" action="/sign_up">
            Sign Up:
            <label>
                <input type="text" name="login" placeholder="login">
            </label>
            <br>
            Password:
            <label>
                <input type="password" name="password" placeholder="password">
            </label>
            <br>
            Name:
            <label>
                <input type="text" name="name" placeholder="name">
            </label>
            <br>
            LastName:
            <label>
                <input type="text" name="lastname" placeholder="last name">
            </label>
            <br>
            <input type="submit" value="Sign up">
            Уже зарегестрирован? <a href="/login">Войти</a>
        </form>
    </div>
</#macro>
</html>