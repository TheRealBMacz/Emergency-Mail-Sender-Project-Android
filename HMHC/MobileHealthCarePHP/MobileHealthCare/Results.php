<?php
$server="localhost";
$user="root";
$password="";
$db="healthproject";
$conn=new mysqli($server, $user, $password, $db);
if($conn->connect_errno){
    die("connection failed");
}
$sql="select problem,medicines from registration where username='".$_POST["username"]."'";
$result=$conn->query($sql);
if($result->num_rows>0)
{
    while ($row=mysqli_fetch_array($result))
    {
        $problem=$row[0];
        $medicines=$row[1];
       
    }
    echo $problem."@".$medicines;
}
$conn->close();
?>