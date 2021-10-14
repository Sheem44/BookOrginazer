<?php
require "init.php";
$user_name= $_GET["user_name"];
$sql="select * from user where user_name='$user_name'";
$result = mysqli_query($conn,$sql);
$row = mysqli_fetch_assoc($result);
    	$alarm_Time= $row['alarm_Time'];
    	echo json_encode($alarm_Time, JSON_UNESCAPED_UNICODE);
		$conn = null;
?>