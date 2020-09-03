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
