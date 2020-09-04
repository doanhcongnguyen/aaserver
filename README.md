Authentication & Authorization with JwtOauth2:

Api: http://{host}:{port}//aaserver/oauth/token
+ Authorization: username/password: simpleclient/simplepassword (see on application.dev/prod.properties)
+ Header: Content-Type: 'application/json'
+ Body: {username: '', password: '', grant_type: 'password'}

curl --request POST \
  --url http://localhost:8666/aaserver/oauth/token \
  --header 'Content-Type: application/json' \
  --header 'cache-control: no-cache' \
  --header 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  --form username=doanhnc \
  --form password=1 \
  --form grant_type=password

====== jQuery AJAX ================
var form = new FormData();
form.append("username", "doanhnc");
form.append("password", "1");
form.append("grant_type", "password");

var settings = {
  "async": true,
  "crossDomain": true,
  "url": "http://localhost:9989/aaserver/oauth/token",
  "method": "POST",
  "headers": {
    "Content-Type": "application/json",
    "Authorization": "Basic c2ltcGxlY2xpZW50OnNpbXBsZXBhc3N3b3Jk",
    "cache-control": "no-cache",
    "Postman-Token": "ded1257b-b055-47d5-aaaa-98032ecd47e6"
  },
  "processData": false,
  "contentType": false,
  "mimeType": "multipart/form-data",
  "data": form
}

$.ajax(settings).done(function (response) {
  console.log(response);
});
