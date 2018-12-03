<?php
$server="localhost";
$user="root";
$password="";
$db="healthproject";
$conn=new mysqli($server, $user, $password, $db);
if($conn->connect_errno){
    die("connection failed");
}
$sql="select token,username,gender,mobileno,age,address,bloodgrp,problem from registration where username='".$_POST["username"]."'";
$result=$conn->query($sql);
if($result->num_rows>0)
{
    while ($row=mysqli_fetch_array($result))
    {
        
        $token=$row[0];
        $username=$row[1];
        $gender=$row[2];
        $mobileno=$row[3];
        $age=$row[4];
        $address=$row[5];
        $bloodgrp=$row[6];
        $problem=$row[7];
        
    }
    echo $token."@".$username."@".$gender."@".$mobileno."@".$age."@".$address."@".$bloodgrp."@".$problem;
}
$conn->close();
?>

