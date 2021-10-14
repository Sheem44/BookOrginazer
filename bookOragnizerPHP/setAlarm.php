<?php
require "init.php";
$user_name= $_GET["user_name"];
$alarm_Time=$_GET["alarm_Time"];
$sql="UPDATE user SET alarm_Time = '$alarm_Time' WHERE user.user_name= '$user_name'";
if (mysqli_query($conn, $sql))
   {
   	$status ="Alarmset";
   }
   else
   {
	$status ="error";
   }
   echo json_encode(array("response"=>$status));   
 mysqli_close($conn);
?>
