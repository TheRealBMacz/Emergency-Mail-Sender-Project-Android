<?php
$server="localhost";
$name="root";
$pwd="";
$db="healthproject";
$con=new mysqli($server,$name,$pwd,$db);
if($con->connect_errno)
{
    die("connectiom failed");
}
$sql="select *from registration where username='".$_POST["username"]."'and role='".$_POST["role"]."' and password='".$_POST["password"]."'";
$result=$con->query($sql);
if($result->num_rows>0)
{
    echo "login Successfull";
}
else {
    
    echo "login failed";
}
?>