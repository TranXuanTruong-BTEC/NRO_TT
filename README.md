# NRO_TT - Server + Client (merged)

Repo này gộp 2 phần:

- `server/` — mã nguồn Java server (từ repo [TranXuanTruong-BTEC/NRO_TT](https://github.com/TranXuanTruong-BTEC/NRO_TT)). Build bằng NetBeans/Ant, kết nối MySQL (`server/nro.sql`).
- `client/` — mã nguồn Unity client (từ repo [cuongle4399/Mod-nro-unity](https://github.com/cuongle4399/Mod-nro-unity)). Yêu cầu **Unity Editor 2022.3.62f1** (đúng version, xem `client/ProjectSettings/ProjectVersion.txt`).

## Đã kiểm tra

- So sánh toàn bộ 286 mã lệnh giao thức (`server/src/consts/Cmd_message.java` ↔ `client/Assets/Scripts/Cmd.cs`) → **khớp 100%** cả tên và giá trị. Đây là dấu hiệu tốt cho thấy 2 bên cùng dòng nguồn / có khả năng tương thích cao.

## Chưa kiểm tra / cần bạn tự làm

- **Chưa build/chạy thử thực tế** — việc này cần Unity Editor (giao diện đồ họa) nên phải làm trên máy bạn, không thể làm trong môi trường dòng lệnh này.
- Địa chỉ IP/domain + port server trong client (thường ở đâu đó trong `client/Assets/Scripts/ServerScr.cs` hoặc `ServerListScreen.cs`) cần được trỏ đúng về server bạn đang chạy.
- Phiên bản game (`UPDATE_VERSION`, version check khi login) giữa 2 bên có thể lệch nếu 1 trong 2 đã được chỉnh sửa thêm tính năng riêng — cần theo dõi log khi test.
- License/nguồn gốc của `client/` là do bên thứ ba (`cuongle4399`) chia sẻ công khai trên GitHub — bạn nên tự soát lại trước khi dùng cho mục đích công khai/thương mại.

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
