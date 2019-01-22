<?php
include("PDOConnection.php");
//load and connect to MySQL database stuff

if (1) {
    //gets user's info based off of a username.
    $query = " 
            SELECT  
                Name, 
                Surname,
                ID
             
            FROM Doctor
            WHERE Active = 1
       ";
  
         $rows = array();
    try {
        $result = $cnn->query($query);
	while($row = $result->fetch(PDO::FETCH_ASSOC))
	{
		 $rows[] = $row ;
	}
         die(json_encode($rows));
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
        
    }
    
    
        
    
  
    // If the user logged in successfully, then we send them to the private members-only page 
    // Otherwise, we display a login failed message and show the login form again 
   
}
?> 