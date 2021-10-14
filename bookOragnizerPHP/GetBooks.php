<?php
require "init.php";
$sql="select * from book";
$result = $conn->query($sql);
$arr = array();

 if($result->num_rows > 0)
    {
	    while($row = $result->fetch_assoc())

		{
		array_push($arr, $row);

		}
		echo json_encode($arr, JSON_UNESCAPED_UNICODE);
		$conn = null;
    }else
	{
		echo json_encode("no items", JSON_UNESCAPED_UNICODE);
		$conn = null;
	}
?>