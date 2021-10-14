<?php
require "init.php";
$user_name= $_GET["user_name"];
$sql = "select book_name, number_of_pages, days, read_pages from schedual where user_name = '$user_name'";
$result = $conn->query($sql);
$arr = array();

 if($result->num_rows > 0)
    {
	    while($row = $result->fetch_assoc())

		{
		array_push($arr, $row);

		}
		
		$conn = null;
		echo json_encode($arr, JSON_UNESCAPED_UNICODE);
    }else
	{
		$conn = null;
		echo json_encode("no items", JSON_UNESCAPED_UNICODE);
	}
	
?>