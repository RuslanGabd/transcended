# Frontend API Documentation

Base URL for local development:

```text
http://localhost:8080
```

All endpoints except register and login require a JWT access token.

```http
Authorization: Bearer <accessToken>
Content-Type: application/json
```

The backend reads the current user from JWT claim `userId`. Do not send `userId` for actions that belong to the logged-in user unless the endpoint explicitly asks for it.

## Auth

### Register

```http
POST /api/auth/register
```

Request:

```json
{
  "nickname": "user",
  "password": "password",
  "email": "user@example.com",
  "name": "User",
  "lastName": "Example",
  "phone": "+10000000000"
}
```

Response `201`:

```json
{
  "accessToken": "jwt-token",
  "tokenType": "Bearer",
  "expiresIn": 300
}
```

### Login

```http
POST /api/auth/login
```

Request:

```json
{
  "nickname": "user",
  "password": "password"
}
```

Response `200`:

```json
{
  "accessToken": "jwt-token",
  "tokenType": "Bearer",
  "expiresIn": 300
}
```

## Users

### Get Current User

```http
GET /api/v1/users/me
```

Response:

```json
{
  "id": 1,
  "nickname": "user",
  "name": "User",
  "lastName": "Example",
  "email": "123@example.com",
  "phone": "+10000000000",
  "role": "USER",
  "postIds": [10, 11],
  "channelIds": [3]
}
```

### Get User By Id

```http
GET /api/v1/users/{id}
```

Response shape is the same as `GET /api/v1/users/me`.

### Mark Current User Online

```http
POST /api/v1/users/online
```

Updates `lastSeenAt` for the current JWT user.

Response:

```http
204 No Content
```

## Profiles

Profiles contain display/profile-page data. Users contain account/security data.

### Create Profile For User

```http
POST /api/v1/profiles/user/{userId}
```

Request:

```json
{
  "displayName": "Name",
  "bio": "Backend enjoyer",
  "avatarUrl": "https://example.com/avatar.jpg",
  "location": "Paris",
  "websiteUrl": "https://example.com"
}
```

Response `201`:

```json
{
  "id": 1,
  "userId": 1,
  "displayName": "Name",
  "bio": "Backend enjoyer",
  "avatarUrl": "https://example.com/avatar.jpg",
  "location": "Paris",
  "websiteUrl": "https://example.com",
  "createdAt": "2026-06-28T15:20:00",
  "updatedAt": "2026-06-28T15:20:00"
}
```

### Get Current User Profile

```http
GET /api/v1/profiles/me
```

Uses JWT `userId`.

### Get Profile By Profile Id

```http
GET /api/v1/profiles/{id}
```

### Get Profile By User Id

```http
GET /api/v1/profiles/user/{userId}
```

### Update Profile By Profile Id

```http
PUT /api/v1/profiles/{id}
```

Uses JWT `userId` to verify ownership.

Request shape is the same as profile create.

### Update Current User Profile

```http
PUT /api/v1/profiles/me
```

Uses JWT `userId`. Request shape is the same as profile create.

### Delete Profile

```http
DELETE /api/v1/profiles/{id}
```

Response:

```http
204 No Content
```

## Posts

### Create Post

```http
POST /api/v1/posts/new
```

Uses JWT `userId`; frontend should not send a user id.

Request:

```json
{
  "title": "Hello",
  "content": "My first post"
}
```

Response `201`:

```json
123
```

The response body is the new post id.

### Get Post By Id

```http
GET /api/v1/posts/{id}
```

Response:

```json
{
  "userId": 1,
  "title": "Hello",
  "content": "My first post",
  "likes": null,
  "dislikes": null,
  "idComments": [5, 6],
  "dataCreated": "2026-06-28T15:20:00",
  "dataEdited": null,
  "dataDeleted": null
}
```

### Get All Posts

```http
GET /api/v1/posts/all
```

Response:

```json
[
  {
    "userId": 1,
    "title": "Hello",
    "content": "My first post",
    "likes": null,
    "dislikes": null,
    "idComments": [],
    "dataCreated": "2026-06-28T15:20:00",
    "dataEdited": null,
    "dataDeleted": null
  }
]
```

### Get Posts By User

```http
GET /api/v1/posts/postByUser/{userId}
```

### Get Posts By Channel

```http
GET /api/v1/posts/channel/{channelId}
```

### Delete Post

```http
DELETE /api/v1/posts/{id}
```

Uses JWT `userId` to check whether the current user can delete the post.

Response:

```http
204 No Content
```

## Comments

### Create Comment

```http
POST /api/v1/comments/new
```

Request:

```json
{
  "postId": 123,
  "userId": 1,
  "content": "Nice post"
}
```

Response:

```json
{
  "id": 5,
  "postId": 123,
  "userId": 1,
  "content": "Nice post",
  "dataCreated": "2026-06-28T15:20:00",
  "dataEdited": null,
  "dataDeleted": null
}
```

Current implementation accepts `userId` in the comment request. For consistency with posts, this should eventually use JWT `userId` instead.

## Frontend Integration Notes

- Store `accessToken` after login/register.
- Send `Authorization: Bearer <accessToken>` on all `/api/v1/**` requests.
- Token lifetime is returned as `expiresIn` in seconds.
- On `401`, redirect to login or refresh auth state.
- On `403`, show an access-denied message.
- Use `/api/v1/users/me` for account data.
- Use `/api/v1/profiles/me` for profile page data.
- Use `/api/v1/users/online` periodically while the app is active if online status is needed.

## Known Backend Caveats

- `POST /api/v1/comments/new` still accepts `userId` in the body.
- Some response fields such as `likes` and `dislikes` may currently be `null`.
- Validation annotations exist on some DTOs, but not every controller uses `@Valid` yet.
