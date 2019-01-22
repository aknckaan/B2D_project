<?php


require("config.inc.php");

if (!empty($_POST)) {
   
    if (empty($_POST['dId']) || empty($_POST['pId'])) {
        
        $response["success"] = 0;
        $response["message"] = "Please Enter Both a dId and pId.";
        
       
        die(json_encode($response));
    }
    
    
    $query = " SELECT 1 FROM UserIDList WHERE ID = :pId AND Type=:type";
    
    $query_params = array(
        ':type' => "Patient",
        ':pId' => $_POST['pId']
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
    if($row)
    {
    	 $query = " SELECT 1 FROM UserIDList WHERE ID = :dId AND Type=:type";
    
   	 $query_params = array(
        	':type' => "Doctor",
        	':dId' => $_POST['dId']
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
    	if(!$row)
    	{
    		$response["success"] = 0;
        	$response["message"] = "Database Error2. This doctor does not exits!";
        	die(json_encode($response));
    	}
    }
    else
    {
    	$response["success"] = 0;
        $response["message"] = "Database Error3. This patient does not exits!";
        die(json_encode($response));
    }
    
    $query = " SELECT 1 FROM Request WHERE PId = :pId AND DId= :dId";
    
    $query_params = array(
        ':dId' => $_POST['dId'],
        ':pId' => $_POST['pId']
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
        $response["message"] = "I'm sorry, this request has already made";
        die(json_encode($response));
    }
    
    $query = "INSERT INTO Request (DId, PId) VALUES (:dId, :pId) ";
    
    $query_params = array(
    	':dId' => $_POST['dId'],
        ':pId' => $_POST['pId']
        
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
  
    $response["success"] = 1;
    $response["message"] = "Request Successfully Added!";
    echo json_encode($response);
    
    //for a php webservice you could do a simple redirect and die.
    //header("Location: login.php"); 
    //die("Redirecting to login.php");
    
    
} else {
?>
	<h1>Set Request</h1> 
	<form action="setRequest.php" method="post"> 
	    DId:<br /> 
	    <input type="text" name="dId" value="" /> 
	    <br /><br /> 
	    PId:<br /> 
	    <input type="text" name="pId" value="" /> 
	    <br /><br />
	    <input type="submit" value="Set Request" /> 
	</form>
	<?php
}

?>