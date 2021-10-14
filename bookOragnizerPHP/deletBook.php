<?php
require "init.php";
$book_name= $_GET["book_name"];
$sql="select * from book where book_name='$book_name'";
$result = mysqli_query($conn,$sql);
 if(!mysqli_num_rows($result)> 0)
    {
	    $status="notexist";
    } 
else
 {
  $sql= "DELETE FROM `book` WHERE `book`.`book_name` = '$book_name'";
   if (mysqli_query($conn,$sql))
   {
   	$status ="ok";
   }
   else
   {
	$status ="error";
   }
 }
 echo json_encode(array("response"=>$status));
 mysqli_close($conn);
?>
 