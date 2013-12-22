<?php
require_once 'core/init.php';

DB::getInstance()->query("SELECT username FROM users");