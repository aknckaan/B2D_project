<?php

/*
Our "config.inc.php" file connects ypo database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");

//if posted data is not empty
if (!empty($_POST)) {
    //If the username or password is empty when the user submits
    //the form, the page will die.
    //Using die isn't a very good practice, you may want to look into
    //displaying an error message within the form instead.  
    //We could also do front-end form validation from within our Android App,
    //but it is good to have a have the back-end code do a double check.
    if (empty($_POST['PId']) ) {
        
        
        // Create some data that will be the JSON response 
        $response["success"] = 0;
        $response["message"] = "Please Enter Both a Username and Password.";    
        die(json_encode($response));
    }
    
    
    $query = " SELECT Type FROM UserIDList WHERE ID = :user";
    
    $query_params = array(
        ':user' => $_POST['PId']
    );
    
   
    try {
        
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
       
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
    }
    
   
    $row = $stmt->fetch();
    if ($row) {
    
   if( $row['Type'] == "Doctor")
   {
   	$query1 = "SELECT * FROM Doctor WHERE `ID`=:user";
   	$query_params1 = array(
   	':user' => $_POST['PId']
       	);
        
     try {
        
        $stmt   = $db->prepare($query1);
        $result = $stmt->execute($query_params1);
    }
    catch (PDOException $ex) {
        
        $response["success"] = 0;
        $response["message"] = "Database Error3. Please Try Again!";
        die(json_encode($response));
    }
     $row = $stmt->fetch();
    die(json_encode($row));
   }
   	
   else if ( $row['Type'] == "Patient")
   {
   	
  	 $query2 = "SELECT * FROM `Patient` WHERE `ID` = :user";
  	 $query_params2 = array(
   	':user' => $_POST['PId']
       
    	);
    	
    	 try {
        
        $stmt   = $db->prepare($query2);
        $result = $stmt->execute($query_params2);
    }
    catch (PDOException $ex) {
        
        $response["success"] = 0;
        $response["message"] = "Database Error4. Please Try Again!";
        die(json_encode($response));
    
    }
     $row = $stmt->fetch();
    die(json_encode($row));
   }
 }	
   else
   {
   	$response["success"] = 0;
        $response["message"] = "Type Error2.";
        die(json_encode($response));
   }
      
    $response["success"] = 1;
    $response["message"] = "Username Successfully Added!";
    echo json_encode($response);
    
    //for a php webservice you could do a simple redirect and die.
    //header("Location: login.php"); 
    //die("Redirecting to login.php");
    
    
} else {
?>
	<h1>Retreive Info</h1> 
	<form action="retreiveInfo.php" method="post"> 
	    PId:<br /> 
	    <input type="text" name="PId" value="" /> 
	    <br /><br /> 
	    
	    <input type="submit" value="Start Query" /> 
	</form>
	<?php
}

?>