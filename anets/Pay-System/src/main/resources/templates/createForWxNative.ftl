<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.5.1/jquery.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<title>Insert title here</title>
</head>
<body>
    <h1>Pay</h1>
    <a href="#">${code_url}</a>
    <div id="myQrCode"></div>

</body>
<script type="text/javaScript">
    jQuery('#myQrCode').qrcode({
       text:"${code_url}"
    });
</script>
</html>