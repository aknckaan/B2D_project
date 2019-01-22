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
    if (empty($_POST['username']) || empty($_POST['password'])) {
        
        
        // Create some data that will be the JSON response 
        $response["success"] = "0";
        $response["message"] = "Please Enter Both a Username and Password.";    
        die(json_encode($response));
    }
    
    
    $query = " SELECT Type FROM UserIDList WHERE UName = :user";
    
    $query_params = array(
        ':user' => $_POST['username']
    );
    
   
    try {
        
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
       
        $response["success"] = "0";
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
    }
    
   
    $row = $stmt->fetch();
    if ($row) {
    
   if( $row['Type'] == "Doctor")
   {
   	$query1 = "UPDATE `Doctor` SET `Password`=:pass,`Age`=:age, `Gender`=:gender ,`Country`=:country,`City`=:city, `Address`=:address, `Phone`=:phone WHERE `UName`=:user";
   	$query_params1 = array(
   	':user' => $_POST['username'],
        ':pass' => $_POST['password'],
        ':age' => $_POST['age'],
        ':gender' => $_POST['gender'],
        ':country' => $_POST['country'],
	':city' => $_POST['city'],
	':address' => $_POST['address'],
	':phone' => $_POST['phone']
	);
        
     try {
        
        $stmt   = $db->prepare($query1);
        $result = $stmt->execute($query_params1);
    }
    catch (PDOException $ex) {
        
        $response["success"] = "0";
        $response["message"] = "Database Error3. Please Try Again!";
        die(json_encode($response));
    }
   }
   	
   else if ( $row['Type'] == "Patient")
   {
   	
  	 $query2 = "UPDATE `Patient` SET `Password`=:pass,`Age`=:age, `Gender`=:gender ,`Country`=:country,`City`=:city, `Address`=:address, `Phone`=:phone  WHERE `UName` = :user ";
  	 $query_params2 = array(
   	':user' => $_POST['username'],
        ':age' => $_POST['age'],
        ':pass' => $_POST['password'],
        ':gender' => $_POST['gender'],
        ':country' => $_POST['country'],
	':city' => $_POST['city'],
	':address' => $_POST['address'],
	':phone' => $_POST['phone']
    	);
    	
    	 try {
        
        $stmt   = $db->prepare($query2);
        $result = $stmt->execute($query_params2);
    }
    catch (PDOException $ex) {
        
        $response["success"] = "0";
        $response["message"] = "Database Error4. Please Try Again!";
        die(json_encode($response));
    }
   }
 }	
   else
   {
   	$response["success"] = "0";
        $response["message"] = "Type Error2.";
        die(json_encode($response));
   }
      
    $response["success"] = "1";
    $response["message"] = "Username Successfully Added!";
    echo json_encode($response);
    
    //for a php webservice you could do a simple redirect and die.
    //header("Location: login.php"); 
    //die("Redirecting to login.php");
    
    
} else {
?>
	<h1>Register</h1> 
	<form action="updateInfo.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 
	    Password:<br /> 
	    <input type="password" name="password" value="" /> 
	    <br /><br /> 
	     Gender:<br /> 
	    <input type="text" name="gender" value="" /> 
	    <br /><br />
	     Country:<br /> 
	    <input type="text" name="country" value="" /> 
	    <br /><br />
	     City:<br /> 
	    <input type="text" name="city" value="" /> 
	    <br /><br />
	     Address:<br /> 
	    <input type="text" name="address" value="" /> 
	    <br /><br />
	     Phone:<br /> 
	    <input type="text" name="phone" value="" /> 
	    <br /><br />
	     Age:<br /> 
	    <input type="integer" name="age" value="" /> 
	    <br /><br />
	     Account:<br /> 
	    <input type="text" name="account" value="" /> 
	    <br /><br />
	    <input type="submit" value="update User" /> 
	</form>
	<?php
}

?>