<?php
include("PDOConnection.php");
define('GOOGLE_API_KEY', 'AAAAECQEWYM:APA91bGSzz3YMocK4p33qosHfH04TcW6A03SelNtvwveIthL7ESSU-AoR_YeDH3_K1vu-9-Y7ejKm35iwmZIgPYAGEUWxhjNkgLeCoBbh2mLW1Ph8jqNrdSXCHrIhxwXUA-BmOKsNfTqQI837LMxLNBIe0y1B3oTvw');//Replace with your Key

$pushStatus = '0';

if(!empty($_POST)) {
	$gcmRegIds = array();
	 $pId=$_POST["PId"];
    $sql="SELECT N_ID
	FROM Doctor
	NATURAL JOIN (
	SELECT DId
	FROM Request
	WHERE PId ='$pId' AND Accepted = 1
	) AS T
	WHERE ID = T.DId AND Active = 1";
    
    $result = $cnn->query($sql);
    $result = $cnn->query($sql);
	while($row = $result->fetch(PDO::FETCH_ASSOC))
	{
		array_push($gcmRegIds, $row["N_ID"]);
		
	}
    $pushMessage = $_POST['message'];
    
    
    if(isset($gcmRegIds) && isset($pushMessage)) {
        //$message = array('message' => $pushMessage);
        //$pushStatus = send_notification($gcmRegIds, $message);
        $pushStatus = send_notification($gcmRegIds, $pushMessage);
    
        
    }else
    { die('error');}
}

function send_notification ($registration_ids, $message)
{
		
		$headers=array('Authorization:key='. GOOGLE_API_KEY,'Content-Type:application/json');
		
		$ids = '[';
			
		for($i =0;$i<sizeof($registration_ids);$i++)
		{
			$ids= $ids.$registration_ids[$i];
			$ids=$ids.',';
		}
		
		$ids=rtrim($ids, ",");
		$ids=$ids.']';
		
			
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $registration_ids,
			 //'notification'=>array('title'=>'Epilepsy Detected!','body'=>$message),
			'data' => array('Epilepsy'=>'1','File'=>$_POST['File'],'PId'=>$_POST['PId'],'title'=>'Epilepsy Detected!','body'=>$message)
			);
			
		$payload = json_encode($fields);
		
	//die($payload);
	$ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS,$payload);
       $result = curl_exec($ch);           
       if ($result === FALSE) {
           die(json_encode('Curl failed: ' . curl_error($ch)));
       }
       curl_close($ch);
       die (($result));
}

?>
<html>
    <head>
        <title>GCM Server</title>
    </head>
    <body style="text-align:center;color:blue">
    <h1>Google Cloud Messaging (GCM) Server</h1>
    
    <form action="sendNotification.php" method="post"> 
    
    	 PId:<br /> 
		    <input type="text" name="PId" placeholder="Patient Id" /> 
		    <br /><br /> 
	File:<br /> 
		    <input type="text" name="File" placeholder="File name" /> 
		    <br /><br /> 
    	
        <div>
            <textarea rows = 6 name = "message" cols = 50 placeholder = 'Messages send to all device in database via GCM'></textarea>
        </div>
        
        <div style="margin-top:10px">
            <input type = 'submit' name="submit" value = 'Send Notification'>
        </div>
        <p>
			<h3>
			<?php
				if('0' != $pushStatus)
				{
					$obj = json_decode($pushStatus);
					if($obj != null)
					{
						echo("<div style='color:green'>");
						echo("Success:".$obj->success);
						echo("<br/>Failure:".$obj->failure);
						echo("</div>");
						die($pushStatus);
					}
					else
					{
						echo("<div style='color:red'>".$pushStatus."</div>");
					}
				}
			?>
			</h3>
		</p>
    </form>
    </body>
</html>