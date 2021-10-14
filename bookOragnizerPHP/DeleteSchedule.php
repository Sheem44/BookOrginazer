<?php
require "init.php";
$user_name= $_GET["user_name"];
$book_name= $_GET["book_name"];

$sql="select * from schedual where user_name='$user_name' and book_name = '$book_name'";
$result = $conn->query($sql);

 if($result->num_rows > 0)
    {
		$sql="delete from schedual where user_name='$user_name' and book_name = '$book_name'";
		$result = $conn->query($sql);
		if($result)
		{
			$status="Schedule Deleted Successfully!";
		} else
		{
			$status="Not Deleted!";
		}
    } else
	{
	    $status="Schedule Not Found";			
	}
 
	$conn = null;
  echo json_encode($status);
?>