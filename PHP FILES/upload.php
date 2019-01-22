<?php
require("config.inc.php");
if (!empty($_POST)) {

$target_dir = "PatientInfo/";

  $query = "SELECT `File` FROM `Patient` WHERE `UName` = :user";
    
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
       $target_dir=$row['File'].'/';
        
    }
    else
    {
   	$response["success"] = 0;
        $response["message"] = "I'm sorry, this username does not exist.";
        die(json_encode($response));
    }
   
$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$uploadOk = 1;
$fileType = pathinfo($target_file,PATHINFO_EXTENSION);
// Check if image file is a actual image or fake image
/*if(isset($_POST["submit"])) {
    //$check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
    $check=true;
    if($check !== false) {
        echo "File is an image - " . $check["mime"] . ".";
        $uploadOk = 1;
    } else {
        echo "File is not an image.";
        $uploadOk = 0;
    }
} */

// Check if file already exists
if (file_exists($target_file)) {
    echo "Sorry, file name already exists.";
    $uploadOk = 0;
}
// Check file size
if ($_FILES["fileToUpload"]["size"] > 2500000) {
    echo "Sorry, your file is too large.";
    $uploadOk = 0;
}
// Allow certain file formats
/*if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
&& $imageFileType != "gif" )*/ 

if($fileType != "txt" ){
    echo "Sorry, only txt files are allowed.";
    $uploadOk = 0;
}
// Check if $uploadOk is set to 0 by an error
if ($uploadOk == 0) {
    echo "Sorry, your file was not uploaded.";
// if everything is ok, try to upload file
} else {
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
        echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";
    } else {
        echo "Sorry, there was an error uploading your file.";
    }
}
}else{
?>
<!DOCTYPE html>
<html>
<body>
<form action="upload.php" method="post"  enctype="multipart/form-data">
Username:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 

    Select image to upload:
    <input type="file" name="fileToUpload" id="fileToUpload">
    <input type="submit" value="Upload Txt" name="submit">
</form>

</body>
</html>
<?php
}
?>