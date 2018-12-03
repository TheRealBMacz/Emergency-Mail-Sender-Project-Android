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
$sql="select username from registration where role='user'";
$result=$con->query($sql);
if($result->num_rows>0)
{
    while ($row=mysqli_fetch_array($result)){
        $resultarray[]=$row["username"];
    }
    $name=implode("@", $resultarray);
    echo $name;
}
else {
    echo "no data found";
}

?>
