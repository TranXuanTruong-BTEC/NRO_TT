# NRO_TT - Server + Client
## Cách build & chạy thử

### Server
```
cd server
# Build bằng Ant (NetBeans project) hoặc mở nbproject/ bằng NetBeans
ant clean jar   # hoặc dùng nút Build trong NetBeans
# Cấu hình DB trong Config.properties, import server/nro.sql vào MySQL
# Chạy: run.bat (Windows) hoặc file jar build ra
```

### Client
```
1. Cài Unity Hub, cài Unity Editor bản 2022.3.62f1
2. Mở Unity Hub -> Add project -> chọn thư mục client/
3. Đợi Unity import xong (lần đầu sẽ khá lâu)
4. Sửa địa chỉ IP/port server trong script kết nối (ServerScr.cs / ServerListScreen.cs)
5. Bấm Play để test trong Editor, hoặc File > Build Settings để build ra .exe/.apk
```
