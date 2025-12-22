# Hướng dẫn Test API với Postman

## 1. Import Collection và Environment

### Bước 1: Import Collection
1. Mở Postman
2. Click **Import** (góc trên bên trái)
3. Chọn file `TW_THAINGUYEN_API.postman_collection.json`
4. Click **Import**

### Bước 2: Import Environment
1. Click **Import** trong Postman
2. Chọn file `TW_THAINGUYEN_API.postman_environment.json`
3. Click **Import**
4. Chọn environment **TW_THAINGUYEN - Local** ở góc trên bên phải

## 2. Cấu hình Environment Variables

Các biến môi trường có sẵn:
- `base_url`: URL của API server (mặc định: `http://localhost:8080`)
- `jwt_token`: JWT token để authenticate (cần set sau khi login)
- `user_id`: ID của user để test (mặc định: `1`)

### Cách cập nhật:
1. Click vào environment **TW_THAINGUYEN - Local**
2. Click **Edit**
3. Cập nhật các giá trị cần thiết
4. Click **Save**

## 3. Các API Endpoints

### Users Management

#### 1. **Get All Users**
- **Method**: `GET`
- **URL**: `{{base_url}}/api/v1/users`
- **Headers**: 
  - `Authorization: Bearer {{jwt_token}}`
- **Description**: Lấy danh sách tất cả users

#### 2. **Get User By ID**
- **Method**: `GET`
- **URL**: `{{base_url}}/api/v1/users/{{user_id}}`
- **Headers**: 
  - `Authorization: Bearer {{jwt_token}}`
- **Description**: Lấy thông tin user theo ID

#### 3. **Create User**
- **Method**: `POST`
- **URL**: `{{base_url}}/api/v1/users`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer {{jwt_token}}`
- **Body** (JSON):
```json
{
    "username": "testuser123",
    "password": "password123",
    "email": "testuser@example.com",
    "phone": "0123456789",
    "fullname": "Test User",
    "avatar": "https://example.com/avatar.jpg"
}
```
- **Validation Rules**:
  - `username`: 6-100 ký tự, chỉ chữ cái và số
  - `password`: 6-100 ký tự
  - `email`: Đúng định dạng email
  - `phone`: 10-15 số
  - `fullname`: 6-100 ký tự

#### 4. **Update User**
- **Method**: `PUT`
- **URL**: `{{base_url}}/api/v1/users/{{user_id}}`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer {{jwt_token}}`
- **Body** (JSON):
```json
{
    "fullname": "Updated Full Name",
    "email": "updated@example.com",
    "phone": "0987654321",
    "avatar": "https://example.com/new-avatar.jpg"
}
```
- **Note**: Tất cả các field đều optional, chỉ cập nhật các field được gửi lên

#### 5. **Delete User**
- **Method**: `DELETE`
- **URL**: `{{base_url}}/api/v1/users/{{user_id}}`
- **Headers**: 
  - `Authorization: Bearer {{jwt_token}}`
- **Description**: Xóa user vĩnh viễn

#### 6. **Lock User**
- **Method**: `PUT`
- **URL**: `{{base_url}}/api/v1/users/{{user_id}}/lock`
- **Headers**: 
  - `Authorization: Bearer {{jwt_token}}`
- **Description**: Khóa tài khoản user (user không thể đăng nhập)

#### 7. **Unlock User**
- **Method**: `PUT`
- **URL**: `{{base_url}}/api/v1/users/{{user_id}}/unlock`
- **Headers**: 
  - `Authorization: Bearer {{jwt_token}}`
- **Description**: Mở khóa tài khoản user

## 4. Response Format

Tất cả API responses đều có format:

### Success Response:
```json
{
    "success": true,
    "message": "Operation successful",
    "data": {
    },
    "timestamp": "2024-01-01T00:00:00",
    "error": null
}
```

### Error Response:
```json
{
    "success": false,
    "message": "Error message",
    "data": null,
    "timestamp": "2024-01-01T00:00:00",
    "error": "Detailed error message"
}
```

### Validation Error Response:
```json
{
    "success": false,
    "message": "Validation failed",
    "data": {
        "username": "Tên người dùng không được để trống",
        "email": "Email không đúng định dạng"
    },
    "timestamp": "2024-01-01T00:00:00",
    "error": null
}
```

## 5. HTTP Status Codes

- `200 OK`: Request thành công
- `201 Created`: Tạo mới thành công
- `400 Bad Request`: Request không hợp lệ (validation errors)
- `401 Unauthorized`: Chưa authenticate hoặc token không hợp lệ
- `404 Not Found`: Không tìm thấy resource
- `409 Conflict`: Resource đã tồn tại (username, email, phone trùng)
- `500 Internal Server Error`: Lỗi server

## 6. Testing Flow

### Flow 1: CRUD User
1. **Create User** → Lưu `user_id` từ response
2. **Get User By ID** → Kiểm tra user vừa tạo
3. **Update User** → Cập nhật thông tin
4. **Get All Users** → Xem danh sách users
5. **Delete User** → Xóa user

### Flow 2: Lock/Unlock User
1. **Get User By ID** → Lấy thông tin user
2. **Lock User** → Khóa tài khoản
3. **Get User By ID** → Kiểm tra `isLocked = true`
4. **Unlock User** → Mở khóa tài khoản
5. **Get User By ID** → Kiểm tra `isLocked = false`

## 7. Tips

1. **Lưu JWT Token**: Sau khi login, copy JWT token và paste vào biến `jwt_token` trong environment
2. **Lưu User ID**: Sau khi tạo user, copy `id` từ response và paste vào biến `user_id`
3. **Test Validation**: Thử gửi request với dữ liệu không hợp lệ để kiểm tra validation
4. **Test Error Cases**: Thử các trường hợp lỗi như:
   - Tạo user với username đã tồn tại
   - Update user với email đã tồn tại
   - Xóa user không tồn tại
   - Lock/Unlock user không tồn tại

## 8. Troubleshooting

### Lỗi 401 Unauthorized
- Kiểm tra JWT token có hợp lệ không
- Kiểm tra token đã được set trong environment variable `jwt_token`
- Kiểm tra header `Authorization` có đúng format: `Bearer {token}`

### Lỗi 404 Not Found
- Kiểm tra `base_url` có đúng không
- Kiểm tra server đã chạy chưa
- Kiểm tra endpoint path có đúng không

### Lỗi 400 Bad Request
- Kiểm tra request body có đúng format JSON không
- Kiểm tra các validation rules
- Xem chi tiết lỗi trong response body

