<?php
require_once("http://localhost:8080/JavaBridge/java/Java.inc");
java_autoload("/web/sites/madfrog/domain.com/cron/bin/html2image.jar");
$world1 = new Java('defualtPackage/HelloWorld');
echo $world1->getName();