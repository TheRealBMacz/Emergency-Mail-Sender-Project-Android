<?php
$server="localhost";
$user="root";
$password="";
$db="healthproject";
$conn=new mysqli($server, $user, $password, $db);
if($conn->connect_errno){
    die("connection failed");
}
$sql="insert into registration(role,token,username,password,gender,age,mobileno,bloodgrp,address) values('".$_POST["role"]."','".$_POST["token"]."','".$_POST["username"]."','".$_POST["password"]."','".$_POST["gender"]."','".$_POST["age"]."','".$_POST["mobileno"]."','".$_POST["bloodgrp"]."','".$_POST["address"]."')";
if($conn->query($sql)){
    echo "data inserted succssfully";
}
else {
    echo "data insertion failed";
}

$conn->close();
?>
