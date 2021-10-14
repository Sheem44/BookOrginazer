<?php
require "init.php";
$name= $_GET["name"];
$user_name= $_GET["user_name"];
$book_name= $_GET["book_name"];
$read_pages= $_GET["read_pages"];

$sql="select * from schedual where user_name='$user_name' and book_name = '$book_name'";
$result = $conn->query($sql);

 if(mysqli_num_rows($result)> 0)
    {
		$sql="update schedual set read_pages = '$read_pages' where user_name='$user_name' and book_name = '$book_name'";
		$result = $conn->query($sql);
		if($result)
		{
			$status="Pages Read Successfully!";
		} else
		{
			$status="Schedual Not Edit!";
		}
    } else
	{
	    $status="Schedual Not Found";			
	}
 
	$conn = null;
  echo json_encode($status);
?>