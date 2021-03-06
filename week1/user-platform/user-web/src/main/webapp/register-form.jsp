<head>
    <jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>login</title>
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <form class="form-signin" action="/register/signin" method="post">
        <h1 class="h3 mb-3 font-weight-normal">注册</h1>
        <label for="inputName" class="sr-only">用户名</label>
        <input type="text" name="username" id="inputName" class="form-control" placeholder="请输入用户名" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="pwd" id="inputPassword" class="form-control" placeholder="请输入密码" required>
        <label for="inputEmail" class="sr-only">Password</label>
        <input type="email" name="email" id="inputEmail" class="form-control" placeholder="请输入邮箱" required>
        <label for="inputPhone" class="sr-only">Password</label>
        <input type="phone" name="phone" id="inputPhone" class="form-control" placeholder="请输入手机号码" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
    </form>
</div>
</body>