<?php
require_once 'core/init.php';

                $user = new User();


?>


<html>

	<head>
		<link rel="stylesheet" href="css/index.css" type="text/css" />
<!--
		<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
-->
        
        <script src="jquery-2.0.3.js"></script>
        <script>
            <?php
            if($user->isLoggedIn()){echo "var username = \"" . escape($user->data()->username) . "\";";}
        ?>
        </script>
        
	</head>
	<body>
        <div id="php_Flash">
            <?php
                if(Session::exists('home')){
	               echo '<p>' . Session::flash('home') . '</p>';
                }
            ?>
        </div>
        <div id="php_user_info" >
            <?php
                if($user->isLoggedIn()){
                    ?>
                    <p>Hello <a href='#'> <?php echo escape($user->data()->username); ?></a></p>
                    <ul>
                        <li><a href="logout.php">log out</a></li>
                        <li><a href="changepassword.php">Change Password</a></li>
                        <?php if($user->isAdmin()){ ?>
                        <li><a href="archive.php">Archive</a></li>
                        <?php } ?>
                    </ul>
                    <?php
                }else{
                    ?>
                    <p>You need to <a href="login.php">login</a> or <a href="register.php">register</a></p>
                    <?php
                }
            ?>
        </div>

		<?php
            if($user->isLoggedIn())
                {
                    include 'chat.html'; 
            }
        ?>
	</body>

</html>
