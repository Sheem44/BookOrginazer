<?php
require "init.php";
$user_name= $_GET["user_name"];
$book_name= $_GET["book_name"];
$days=$_GET["days"];
$sql="select * from schedual where user_name='$user_name' and book_name='$book_name'";
$result = mysqli_query($conn,$sql);
 if(!mysqli_num_rows($result)> 0)
    {
	    $status="NoScheduale";
	    echo json_encode(array("response"=>$status));
    } 
 else
    {
    $query="UPDATE schedual SET days = '$days' WHERE schedual.user_name= '$user_name' AND schedual.book_name= '$book_name'";
    if (mysqli_query($conn, $query))
   {
   	$status ="daysAdded";
   }
   else
   {
	$status ="error";
   }
  echo json_encode(array("response"=>$status));
      }
 mysqli_close($conn);
?>
