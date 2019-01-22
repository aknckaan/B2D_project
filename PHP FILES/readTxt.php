<?php
include("PDOConnection.php");
//load and connect to MySQL database stuff

if (!empty($_POST)) {
    //gets user's info based off of a username.
    $pId=$_POST["PId"];
    
    $query = "SELECT File
	FROM Patient
	WHERE ID='$pId'
	";
  
         $rows = array();
    try {
        $result = $cnn->query($query);
	while($row = $result->fetch(PDO::FETCH_ASSOC))
	{
	 die(json_encode(file_get_contents( $row['File'].'/'.$_POST["FName"])));
		 $rows[] = $row ;
	}
	
	
	
        
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
        
    }
   
}else{
?>
   <h1>get New Patients List</h1> 
		<form action="readTxt.php" method="post"> 
		    PId:<br /> 
		    <input type="text" name="PId" placeholder="patient Id" /> 
		    <br /><br /> 
		     FName:<br /> 
		    <input type="text" name="FName" placeholder="File Name" /> 
		    <br /><br /> 
		   
		    <input type="submit" value="Start Query" /> 
		</form> 
	<?php
	}
?> 