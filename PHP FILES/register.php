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
        $response["success"] = 0;
        $response["message"] = "Please Enter Both a Username and Password.";
        
        //die will kill the page and not execute any code below, it will also
        //display the parameter... in this case the JSON data our Android
        //app will parse
        die(json_encode($response));
    }
    
    
    $query = " SELECT 1 FROM UserIDList WHERE UName = :user";
    
    $query_params = array(
        ':user' => $_POST['username']
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
       
        $response["success"] = 0;
        $response["message"] = "I'm sorry, this username is already in use";
        die(json_encode($response));
    }
    
    //If we have made it here without dying, then we are in the clear to 
    //create a new user.  Let's setup our new query to create a user.  
    //Again, to protect against sql injects, user tokens such as :user and :pass
    
    if($_POST['account']!="Doctor" && $_POST['account']!="Patient")
    {
    	$response["success"] = 0;
        $response["message"] = "Type Error1.";
        die(json_encode($response));
    }
    
    $query = "INSERT INTO UserIDList (UName, Type) VALUES (:user, :type) ";
    
    //Again, we need to update our tokens with the actual data:
    $query_params = array(
    	':type' => $_POST['account'],
        ':user' => $_POST['username']
        //':pass' => $_POST['password'],
        //':name' => $_POST['name'],
        //':surname' => $_POST['surname'],
        
    );
    
    //time to run our query, and create the user
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error2. Please Try Again!";
        die(json_encode($response));
    }
   if( $_POST['account'] == "Doctor")
   {
   	$query1 = "UPDATE `Doctor` SET `Surname`=:surname, `Name`=:name, `Password`=:pass,`Age`=:age, `Gender`=:gender ,`Country`=:country,`City`=:city, `Address`=:address, `Phone`=:phone WHERE `UName`=:user";
   	$query_params1 = array(
   	':user' => $_POST['username'],
        ':name' => $_POST['name'],
    	':surname' => $_POST['surname'],
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
        
        $response["success"] = 0;
        $response["message"] = "Database Error3. Please Try Again!";
        die(json_encode($response));
    }
   }
   	
   else if ( $_POST['account'] == "Patient")
   {
   	$dir='PatientInfo/'.$_POST['username'].'_'.$_POST['name'].'_'.$_POST['surname'];
   	if( is_dir($dir) === false )
	{
	    mkdir($dir);
	}
   
  	 $query2 = "UPDATE `Patient` SET `Surname`=:surname, `Name`=:name, `Password`=:pass,`Age`=:age, `Gender`=:gender ,`Country`=:country,`City`=:city, `Address`=:address, `Phone`=:phone ,`File`='$dir' WHERE `UName` = :user ";
  	 $query_params2 = array(
   	':user' => $_POST['username'],
        ':name' => $_POST['name'],
  	 ':surname' => $_POST['surname'],    
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
        
        $response["success"] = 0;
        $response["message"] = "Database Error4. Please Try Again!";
        die(json_encode($response));
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
	<h1>Register</h1> 
	<form action="register.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 
	    Password:<br /> 
	    <input type="password" name="password" value="" /> 
	    <br /><br /> 
	     Name:<br /> 
	    <input type="text" name="name" value="" /> 
	    <br /><br />
	     Surname:<br /> 
	    <input type="text" name="surname" value="" /> 
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
	    <input type="submit" value="Register New User" /> 
	</form>
	<?php
}

?>