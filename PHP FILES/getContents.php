<?php
include("PDOConnection.php");
//load and connect to MySQL database stuff

if (!empty($_POST)) {
    //gets user's info based off of a username.
    $pId=$_POST["PId"];
    $query = " 
            SELECT File
	FROM  `Patient` 
	WHERE ID = '$pId'
	";
 
         $rows = array();
    try {
        $result = $cnn->query($query);
	while($row = $result->fetch(PDO::FETCH_ASSOC))
	{
		 $rows[] = $row ;
		  die(json_encode(scandir($row['File'])));
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
   <h1>get Content List</h1> 
		<form action="getContents.php" method="post"> 
		    PId:<br /> 
		    <input type="text" name="PId" placeholder="Patient Id" /> 
		    <br /><br /> 
		   
		    <input type="submit" value="Start Query" /> 
		</form> 
	<?php
	}
?> 