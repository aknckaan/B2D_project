<?php
include("PDOConnection.php");
//load and connect to MySQL database stuff

if (!empty($_POST)) {
    //gets user's info based off of a username.
    $dId=$_POST["DId"];
    
    $query = " 
    Select ID, Name, Surname, Age, Gender
    From Patient NATURAL JOIN (SELECT PId
	FROM Request
	WHERE DId='$dId' AND Accepted = 0) AS T
	WHERE T.PId = ID
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
   
}else{
?>
   <h1>get New Patients List</h1> 
		<form action="getNewPatients.php" method="post"> 
		    DId:<br /> 
		    <input type="text" name="DId" placeholder="Doctor Id" /> 
		    <br /><br /> 
		   
		    <input type="submit" value="Start Query" /> 
		</form> 
	<?php
	}
?> 