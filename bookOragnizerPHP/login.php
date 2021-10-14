<?php
require "init.php";
$user_name=$_GET["user_name"];
$user_password=$_GET["user_password"];
 if (($user_name=="")&& ($user_password==""))
 {
     $status="empty";
        echo json_encode(array("response"=>$status));
 }
 else{
$sql="select * from admin where admin_name='$user_name' and admin_password='$user_password'";
$result = mysqli_query($conn,$sql);
if (mysqli_num_rows($result)> 0)
{
    $status="admin";
        echo json_encode(array("response"=>$status));
}
else
{
$sql="select * from user where user_name='$user_name' and user_password='$user_password'";
$result = mysqli_query($conn,$sql);
 if(!mysqli_num_rows($result)> 0)
    {
	    $status="failed";
	    echo json_encode(array("response"=>$status));
    } 
    else
    {
    	$row = mysqli_fetch_assoc($result);
    	$name= $row['name'];
    	$status="ok";
    	echo json_encode(array("response"=>$status,"name"=>$name,"user_name"=>$user_name));
    }
}
}
    mysqli_close($conn);
?>