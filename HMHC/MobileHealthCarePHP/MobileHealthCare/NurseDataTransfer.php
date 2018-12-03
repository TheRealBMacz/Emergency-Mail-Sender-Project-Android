<?php
$server="localhost";
$user="root";
$password="";
$db="healthproject";
$conn=new mysqli($server, $user, $password, $db);
if($conn->connect_errno){
    die("connection failed");
}
$sql="select token,username,password,gender,mobileno,age,bloodgrp,address,specialization from registration where username='".$_POST["username"]."'";
$result=$conn->query($sql);
if($result->num_rows>0)
{
    while ($row=mysqli_fetch_array($result))
    {
        $token=$row[0];
        $username=$row[1];
        $password=$row[2];
        $gender=$row[3];
        $mobileno=$row[4];
        $age=$row[5];
        $bloodgrp=$row[6];
        $address=$row[7];
        $specialization=$row[8];
        
    
        
    }
    echo $token."@".$username."@".$password."@".$gender."@".$mobileno."@".$age."@".$bloodgrp."@".$address."@".$specialization;
}
$conn->close();
?>
