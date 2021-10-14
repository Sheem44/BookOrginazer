<?php
require "init.php";
$book_name= $_GET["book_name"];
$number_of_pages= $_GET["number_of_pages"];
 $book_link=$_GET["book_link"];
 $sql="select * from book where book_name='$book_name'";
 $result = mysqli_query($conn,$sql);
 if(!mysqli_num_rows($result)> 0)
    {
	    $status="noBook";
	  
    } 
else{
 if (!empty($number_of_pages))
{
 $sql="UPDATE `book` SET `number_of_pages` = '$number_of_pages' WHERE `book`.`book_name` = '$book_name'";
       if (mysqli_query($conn,$sql))
   {
   	$status ="ok";
   }
   else
   {
	$status ="numerror";
   }
}
 if (!empty( $book_link))
{

 $sql="UPDATE `book` SET  `book_link` = '$book_link' WHERE `book`.`book_name` = '$book_name'";
       if (mysqli_query($conn,$sql))
   {
   	$status ="ok";
   }
   else
   {
	$status ="linkerror";
   }
}
}
 if (empty( $book_link) && empty($number_of_pages))
{
  $status ="NoValues";
}
echo json_encode(array("response"=>$status));
 mysqli_close($conn);
?>