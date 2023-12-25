
### Writing swagger burnt me down. It requires dedicated time to have your project setup in a way to actually gain benefit of it. I hope this doesn’t bite me back 😔


Deprecated: [https://alertly.kshitizagrawal.in/docs](https://alertly.kshitizagrawal.in/docs)


### Bearer Token


Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDI5MDQyMDU3Nzg3MzI1MTUzMzMiLCJlbWFpbCI6Im5pcmFqcGF0aWRhcjgxQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NJNDFfNXhjNllaNW9SNmlrZ3Y2bDhYVzd6Z2NxRkRwYW9aaU9uUy1KVUFabTlHPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik5pcmFqIiwiZmFtaWx5X25hbWUiOiJQYXRpZGFyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlhdCI6MTcwMjgxMjk3NywiZXhwIjoxNzAzNDE3Nzc3fQ.5GXbBA99JPjZD9i_qbI0S3jV-ldtRSS5VDB_3raoMwI


## Base URL - https://alertly.kshitizagrawal.in


## API Prefix - /api


## Unprotected Routes


### POST /auth/google/login


Called after google sign in is successful and an id_token is recieved.


Request Body:


```json
{
  "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjBhZDFmZWM3ODUwNGY0NDdiYWU2NWJjZjVhZmFlZGI2NWVlYzllODEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyODIzMTI2MDQwMzEtbDQyNzQzMWltMjJnM3Q0YmJuM2VvaGFxdHMzZDRobmQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyODIzMTI2MDQwMzEtaWlpcTI0aTQ0a2RyZjJyZmM2dWg1dWI5YWJqNjBpbGwuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDI5MDQyMDU3Nzg3MzI1MTUzMzMiLCJlbWFpbCI6Im5pcmFqcGF0aWRhcjgxQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiTmlyYWogUGF0aWRhciIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NJNDFfNXhjNllaNW9SNmlrZ3Y2bDhYVzd6Z2NxRkRwYW9aaU9uUy1KVUFabTlHPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik5pcmFqIiwiZmFtaWx5X25hbWUiOiJQYXRpZGFyIiwibG9jYWxlIjoiZW4tR0IiLCJpYXQiOjE3MDI3OTE2MzQsImV4cCI6MTcwMjc5NTIzNH0.mMvcsmU3dqf7ywv6ij0ghQCwvLDxQApxsSpbPVaElQzQdIAmDYQMDf3wDXyHtcexYB3yeSFR9ObTw-YFYwTU2HyFULcoFYvWV35PUhHFZ98X8a_5zfiB5ivn_Wb8do_eO_1z64v7H8icHYbBs1aejLrWWnBgARzRz-aU4aMorOtPYxwPxgU_2913qK30oKOCckUXUTwqZgr0rrj-DQZBYXQWV05ZRhvYXNhYhkpHv_r41kROLnJUtbitkO1xaDWnM13L_GKHuLMbwTd2TTKT4d6inweYQc1262lF_Q78Lc-qJVVWicSajFxDLf369jNdJH0vMCADK3VFRvuj9Nn5mA"
}
```


Response:


```json
{
  "success": true,
  "data": {
    "sub": "102904205778732520000",
    "email": "nirajpatidar81@gmail.com",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDI5MDQyMDU3Nzg3MzI1MTUzMzMiLCJlbWFpbCI6Im5pcmFqcGF0aWRhcjgxQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NJNDFfNXhjNllaNW9SNmlrZ3Y2bDhYVzd6Z2NxRkRwYW9aaU9uUy1KVUFabTlHPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik5pcmFqIiwiZmFtaWx5X25hbWUiOiJQYXRpZGFyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlhdCI6MTcwMjc5NDc2MSwiZXhwIjoxNzAzMzk5NTYxfQ.Tz8XtQNRl3guwsZcgpTilnYWKWAS_suBc2lnLeAaBzk"
  }
}
```


## Protected Routes (Use with token)


### POST /groups/create


When user creates a group with group_name and description. The user is given admin privileges to the group.


Request Body- 


 


```json
{
  "group_name": "test-group",
  "description": "yoyo"
}
```


Response - 


```json
{
  "success": true,
  "data": {
    "groupID": 7,
    "group_name": "test-group",
    "description": "yoyo"
  }
}
```


### GET /groups


Get all groups an user is associated with


Response - 


```json
{
  "success": true,
  "data": [
    {
      "group_id": 1,
      "pinned": false,
      "group_name": "Society Group",
      "description": "An alert group for people in the Narmada Society",
      "is_admin": true,
      "created_at": "2023-12-22T04:19:56.978Z",
      "group_image_uri": "https://i.ibb.co/0cW8Bsv/group.png"
    },
    {
      "group_id": 2,
      "pinned": false,
      "group_name": "Bhopal Group",
      "description": "An alert group for people in the Bhopal Region",
      "is_admin": true,
      "created_at": "2023-12-22T04:51:31.047Z",
      "group_image_uri": "https://i.ibb.co/0cW8Bsv/group.png"
    },
    {
      "group_id": 3,
      "pinned": false,
      "group_name": "MAT1002 Group",
      "description": "An alert group for student's in class MAT1002",
      "is_admin": true,
      "created_at": "2023-12-22T04:52:17.158Z",
      "group_image_uri": "https://i.ibb.co/0cW8Bsv/group.png"
    },
    {
      "group_id": 4,
      "pinned": false,
      "group_name": "CSE1002",
      "description": "An alert group for students in class CSE1002",
      "is_admin": true,
      "created_at": "2023-12-22T13:20:14.186Z",
      "group_image_uri": "https://i.ibb.co/0cW8Bsv/group.png"
    }
  ]
}
```


### Get /groups/{group_id}?pageNumber=1&pageSize=20


page number is 1 based indexed


Example - https://alertly.kshitizagrawal.in/api/groups/123?pageNumber=1&pageSize=20


Response - 


Success: 200


```json
{
  "success": true,
  "data": [
    {
      "alert_id": 2,
      "group_id": 3,
      "message_sender_id": "102904205778732515333",
      "title": "alert 2",
      "description": "some desc",
      "severity": "danger",
      "sent_at": "2023-12-24T18:47:37.693Z",
      "sender_name": "Niraj",
      "sender_image_uri": "https://lh3.googleusercontent.com/a/ACg8ocI41_5xc6YZ5oR6ikgv6l8XW7zgcqFDpaoZiOnS-JUAZm9G=s96-c"
    },
    {
      "alert_id": 1,
      "group_id": 3,
      "message_sender_id": "102904205778732515333",
      "title": "alert 1",
      "description": "some desc",
      "severity": "danger",
      "sent_at": "2023-12-24T18:47:22.551Z",
      "sender_name": "Niraj",
      "sender_image_uri": "https://lh3.googleusercontent.com/a/ACg8ocI41_5xc6YZ5oR6ikgv6l8XW7zgcqFDpaoZiOnS-JUAZm9G=s96-c"
    }
  ]
}
```


Unauthorized: 401


```json
{
    "success": false,
    "data": "you are not authorized to access this resource"
}
```


### POST /groups/{group_id}/pin


Response:


Success: 200


```json
{
    "success": true,
}
```


Unauthorized: 401


```json
{
    "success": false,
    "data": "you are not authorized to access this resource"
}
```


### POST /groups/{group_id}/unpin


Response:


Success: 200


```json
{
    "success": true,
}
```


Unauthorized: 401


```json
{
    "success": false,
    "data": "you are not authorized to access this resource"
}
```


### GET /groups/{group_id}/access_token


Gets the access token for a group for users to join in. (Expires every 30 seconds). (TOTP based)


Response:


Success: 200


```json
{
    "success": true,
    "data": "1234"
}
```


Unauthorized: 401


```json
{
    "success": false,
    "data": "you are not authorized to access this resource"
}
```


### POST /groups/join


For joining a group with a given access token


Request Body- 


```json
{
  "access_token": "123456",
}
```


Response:


Success: 200


```json
{
    "success": true,
    "data": {
        "group_id": 7
    }
}
```


Unauthorized: 401


```json
{
    "success": false,
    "data": "the token doesn't exist or is expired"
}
```


### POST /groups/{group_id}/alert


For creating an alert in a group


Request Body- 


```json
{
    "title":"alert 1",
    "description":"some desc",
    "severity":"danger"
}
```


Response:


Success: 200


```json
{
  "success": true,
  "data": {
    "alert_id": 5,
    "sent_at": "2023-12-20T11:55:12.077Z",
    "given_name": "Niraj",
    "family_name": "Patidar",
    "user_id": "102904205778732515333",
    "severity": "danger",
    "title": "alert 1",
    "description": "some desc"
  }
}
```


Unauthorized: 401


```json
{
    "success": false,
    "data": "you are not authorized to access this resource"
}
```


Server Error: 500


```json
{
    "success": false,
    "data": "error creating alert"
}
```


### **POST /uploads/media**


For uploading any media and getting a static URL for it. Restricted to only photos for now.


Content-Type: **multipart/form-data**


Request Form Data:


```text
key: "photos"
value: <File> (restricted to only 1 for now
```


Response: 


Success: 200


```json
{
    "success": true,
    "data": {
        "photos": [
            {
                "upload_id": 43,
                "user_id": "102904205778732515333",
                "metadata": "{\"userId\":\"102904205778732515333\",\"original_name\":\"CICD flow.png\"}",
                "path": "CICD flow-1703464714974.png",
                "created_at": "2023-12-25T00:38:34.980Z",
                "uri": "https://alertly.kshitizagrawal.in/static/CICD%20flow-1703464714974.png"
            }
        ]
    }
}
```


Unauthorized: 401


```json
{
    "success": false,
    "data": "This is an authenticated resource, you must be logged in to access it."
}
```


### **POST /groups/{group_id}/avatar**


Request Body:


```json
{
    "upload_id":30
}
```


Response:


Success 200:


```json
{
    "success": true,
    "data": {
        "group_id": 7,
        "group_name": "test-group",
        "description": "yoyo",
        "group_image_uri": "https://alertly.kshitizagrawal.in/static/CICD%20flow-1703463927699.png"
    }
}
```


Invalid Upload Id 400:


```json
{
    "success": false,
    "data": "the requested image is not found"
}
```

