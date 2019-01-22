<?php

//load and connect to MySQL database stuff
require("config.inc.php");
define('GOOGLE_API_KEY', 'AIzaSyD0B37mMq9AH41SnJbmZ95fkfAd2kJrgrs');
if (!empty($_POST)) {
    //gets user's info based off of a username.
    $query = " 
            SELECT  
                UName, 
                Type
            FROM UserIDList
            WHERE 
                Uname = :username 
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
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
        
    }
    
    //This will be the variable to determine whether or not the user's information is correct.
    //we initialize it as false.
    $validated_info = false;
    
    //fetching all the rows from the query
    $row = $stmt->fetch();
    if ($row) {
        //if we encrypted the password, we would unencrypt it here, but in our case we just
        //compare the two passwords
        
       $login_type=$row["Type"];
       $login_ok=true;
    	
    	
    }

   
    // If the user logged in successfully, then we send them to the private members-only page 
    // Otherwise, we display a login failed message and show the login form again 
    if ($login_ok) {
    
    		if($login_type=="Patient")
    		{
    		$query = "UPDATE `Patient` SET `N_ID`=:NId WHERE `UName`=:username";
    		$query_params = array(
        	':NId' => $_POST['NId'],
        	':username'=> $_POST['username']);
        	
        	$response["success"] = 1;
        	$response["message"] = "Update Patient cId successful!";
        	//die(json_encode($response));
    		}
    		elseif($login_type=="Doctor")
    		{
    			$query = "UPDATE `Doctor` SET `N_ID`=:NId WHERE `UName`=:username";
    			$query_params = array(
        		':NId' => $_POST['NId'],
        		':username'=> $_POST['username']);
        		
        	$response["success"] = 1;
        	$response["message"] = "Update Doctor cId successful!";
        	//die(json_encode($response));
    		}
    	
    		
    	try {
      	  $stmt   = $db->prepare($query);
      	  $result = $stmt->execute($query_params);
    	}
   	 catch (PDOException $ex) {
        	// For testing, you could use a die and message. 
        	//die("Failed to run query: " . $ex->getMessage());
        
        	//or just use this use this one:
        	$response["success"] = 0;
        	$response["message"] = "Database Error3. Please Try Again!";
        	die(json_encode($response));
    	}
    
        	//$response["success"] = 0;
        	//$response["message"] = "Update failed!";
        	die(json_encode($response));
    	} 		
    	else {
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));;
   	 }
    	
    		
}
 
?>
		<h1>Set C id</h1> 
		<form action="setNId.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    Password:<br /> 
		    <input type="password" name="password" placeholder="password" value="" /> 
		    <br /><br />
		    cId:<br /> 
		     <input type="text" name="NId" placeholder="enter NId" value="" /> 
		    <br /><br /> 
		    <input type="submit" value="Send Request" /> 
		</form> 
	<?php

?> 