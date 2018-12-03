<?php
$server="localhost";
$user="root";
$password="";
$db="healthproject";
$conn=new mysqli($server, $user, $password, $db);
if($conn->connect_errno){
    die("connection failed");
}
$sql="update registration set age='".$_POST["age"]."',password='".$_POST["password"]."',gender='".$_POST["gender"]."',mobileno='".$_POST["mobileno"]."',username='".$_POST["username"]."',bloodgrp='".$_POST["bloodgrp"]."',address='".$_POST["address"]."' where token='".$_POST["token"]."'";
if($conn->query($sql)){
    echo "data updated succssfully";
}
else {
    echo "data updated failed";
}

$conn->close();
?>
