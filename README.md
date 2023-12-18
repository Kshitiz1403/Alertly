
### Writing swagger burnt me down. It requires dedicated time to have your project setup in a way to actually gain benefit of it. I hope this doesn’t bite me back 😔


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
            "group_id": 7,
            "pinned": false,
            "group_name": "test-group",
            "description": "yoyo",
            "is_admin": true,
            "created_at": "2023-12-17T06:39:02.108Z"
        }
    ]
}
```


### Get /groups/{group_id}?pageNumber=1&pageSize=20


Example - https://alertly.kshitizagrawal.in/api/groups/123?pageNumber=1&pageSize=20


Response - 


TODO: update with dummy data


Success: 200


```json
{
    "success": true,
    "data": []
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

