<?php
require "init.php";
$book_name= $_GET["book_name"];
$number_of_pages= $_GET["number_of_pages"];
if(isset($_GET["book_link"]))
$book_link=$_GET["book_link"];
else
$book_link="";
$sql="select * from book where book_name='$book_name'";
$result = mysqli_query($conn,$sql);
 if(mysqli_num_rows($result)> 0)
    {
	    $status="exist";
    } 
else if (($book_name=="")|| ($number_of_pages==0))
{
  $status ="NoValues";
}
else
 {
  $sql= "insert into book(book_name,number_of_pages, book_link) values ('$book_name','$number_of_pages','$book_link')";
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