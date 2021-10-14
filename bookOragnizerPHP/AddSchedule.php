<?php
require "init.php";
$user_name= $_GET["user_name"];
$book_name= $_GET["book_name"];
$number_of_pages= $_GET["number_of_pages"];
$days= $_GET["days"];
$read_pages = 0;

$sql="select * from schedual where user_name='$user_name' and book_name = '$book_name'";
$result = $conn->query($sql);

 if($result->num_rows <= 0)
    {
			$sql="insert into schedual (user_name, book_name, number_of_pages, days, read_pages) values ('$user_name','$book_name', '$number_of_pages', '$days', '$read_pages')";

		$result = $conn->query($sql);
		if($result)
		{
			$status="Schedule Added Successfully!";
		} else
		{
			$status="Schedule Not Added!";
		}
    } else
	{
	    $status="Schedule Is Already Exist";			
	}
 
	$conn = null;
  echo json_encode($status);
?>
