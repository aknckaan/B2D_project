<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
    //gets user's info based off of a username.
    $query = " 
            SELECT  
                UName, 
                Type
            FROM UserIDList 
            WHERE 
                UName = :username
        ";
    
    $query_params = array(
        ':username' => $_POST['username']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = "0";
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
        
    }
    
    //This will be the variable to determine whether or not the user's information is correct.
    //we initialize it as false.
    $validated_info = false;

    $row = $stmt->fetch();
    
    if ($row) {
    
    	if($row['Type']=="Doctor")
    	{
    	 	$query1 = " 
           	 SELECT  *
           	 FROM UserIDList NATURAL JOIN Doctor
           	 WHERE 
                UName = :username AND Password = :password
        	";
    	
   		 $query_params1 = array(
        	':username' => $_POST['username'],
        	':password' => $_POST['password']
        	
    		);
    
    		try {
      	 	$stmt1   = $db->prepare($query1);
      	 	$result1 = $stmt1->execute($query_params1);
   		}
   	 	catch (PDOException $ex) {
       
        	$response["success"] = "0";
        	$response["message"] = "Database Error2. Please Try Again!";
        	die(json_encode($response));
	    }
	    
	    $row1 = $stmt1->fetch();
    	    if ($row1) {
           	 $login_ok = true;  
           	 $response["success"] =  $row1['ID'];
        	$response["message"] = "Doctor Login successful!";
        	die(json_encode($response));
   	   }
	}
    	else if($row['Type']=="Patient")
    	{
    		$query2 = " 
           	 SELECT  
             	   *
           	 FROM UserIDList NATURAL JOIN Patient
           	 WHERE 
                UName = :username AND Password = :password
        	";
    	
   		 $query_params2 = array(
        	':username' => $_POST['username'],
        	':password' => $_POST['password']
    		);
    
    		try {
      	 	$stmt2   = $db->prepare($query2);
      	 	$result2 = $stmt2->execute($query_params2);
   		}
   	 	catch (PDOException $ex) {
       
       
        	$response["success"] = "0";
        	$response["message"] = "Database Error3. Please Try Again!";
        	die(json_encode($response));
	    }	
	    	$row2 = $stmt2->fetch();
         	if ($row2) {
           	 $login_ok = true;    
           	 $response["success"] = $row2['ID'];
        	$response["message"] = "Patient Login successful!";
        	die(json_encode($response));
   	 	}
    	}
          
    }
    
    // If the user logged in successfully, then we send them to the private members-only page 
    // Otherwise, we display a login failed message and show the login form again 
    if ($login_ok) {
        $response["success"] = "1";
        $response["message"] = "Login successful!";
        die(json_encode($response));
    } else {
        $response["success"] = "0";
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));
    }
} else {
?>
		<h1>Login</h1> 
		<form action="login.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    Password:<br /> 
		    <input type="password" name="password" placeholder="password" value="" /> 
		    <br /><br /> 
		    <input type="submit" value="Login" /> 
		</form> 
		<a href="register.php">Register</a>
	<?php
}

?> 