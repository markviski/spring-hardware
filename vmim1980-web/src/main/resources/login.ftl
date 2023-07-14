<html>
	<head>
		<title>Login</title>
		<link href="./styles.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="centerdiv">
		    <h1>
        		Login
        	</h1>
        	<div class="errordiv"> ${Error} </div>
            <form class="loginform" action="login" method="post">
                Name:<input type="text" name="user"><br>
                Password:<input type="password" name="pass"><br>
                <input type="submit" value="Login">
            </form>
		</div>
	</body>
</html>
