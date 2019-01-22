<?php

//load and connect to MySQL database stuff
require("config.inc.php");
include("PDOConnection.php");

if (!empty($_POST)) {
    //gets user's info based off of a username.
    
    if($_POST['val']=="1")
    {
    	 $query = "UPDATE `Request` SET `Accepted`= 1 WHERE `PId`= :pId AND `DId`= :dId";
    	 
    	 $query_params = array(
        ':pId' => $_POST['pId'],
        ':dId' => $_POST['dId']
        
    	);
    	 
    	  try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    	}
    	catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
        
    	}
    	
         $response["success"] = "1";
        	$response["message"] = "Request accepted";
        	die(json_encode($response));
    }
    else if($_POST['val']=="0")
    {
    	$query = " DELETE FROM `Request` WHERE `PId`=:pId AND `DId`= :dId";
    	
    	$query_params = array(
        ':pId' => $_POST['pId'],
        ':dId' => $_POST['dId']
        
    );

    	
    	 try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
   	 }
    	catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
        
    	}
    	
        $response["success"] ="1";
        $response["message"] = "Request rejected";
        die(json_encode($response));

    }    
   
    
    
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));
    
} else {
?>
		<h1>Accept Request</h1> 
		<form action="acceptRequest.php" method="post"> 
		    PId:<br /> 
		    <input type="text" name="pId" placeholder="patient id" /> 
		    <br /><br /> 
		    DId:<br /> 
		    <input type="text" name="dId" placeholder="doctor id" value="" /> 
		    <br /><br /> 
		     Val:<br /> 
		    <input type="text" name="val" placeholder="val" value="" /> 
		    <br /><br />
		    <input type="submit" value="Post" /> 
		</form> 
		<a href="acceptRequest.php">Register</a>
	<?php
}

?> 