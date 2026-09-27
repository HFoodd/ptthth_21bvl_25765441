# 🚀 HƯỚNG DẪN BẮTĐẦU - BẮT ĐẦU TỪ ĐÂY

## ⚡ Nhanh chóng trong 5 bước

### 1️⃣ Import vào Eclipse
```
File → Import → General → Existing Projects into Workspace
→ Select root directory: d:\IUH\Tich Hop\lab04-socket
→ Finish
```

### 2️⃣ Biên dịch toàn bộ
Mở Terminal/PowerShell:
```bash
cd "d:\IUH\Tich Hop\lab04-socket"
javac -d bin src/network/*.java src/tcp/*.java src/udp/*.java src/exercise/*.java
```

### 3️⃣ Chạy ví dụ đầu tiên (TCP)

Terminal 1 - Server:
```bash
java -cp bin tcp.TcpCommandServer
```

Terminal 2 - Client:
```bash
java -cp bin tcp.TcpCommandClient localhost 5000
```

Gõ trong client: `PING` → Xem kết quả: `Server: OK PONG`

### 4️⃣ Thực hành tất cả
Xem [RUN_COMMANDS.txt](RUN_COMMANDS.txt) để chạy tất cả ví dụ

### 5️⃣ Nộp báo cáo
Đọc [SUBMIT_REPORT.md](SUBMIT_REPORT.md)

---

## 📑 Các file để đọc theo thứ tự

| Thứ tự | File | Nội dung | Thời gian |
|--------|------|---------|----------|
| 1️⃣ | **START_HERE.md** | File này (quick start) | 5 min |
| 2️⃣ | **INDEX.md** | Chỉ mục chi tiết | 5 min |
| 3️⃣ | **README.md** | Tài liệu chính | 15 min |
| 4️⃣ | **RUN_COMMANDS.txt** | Tất cả lệnh | 10 min |
| 5️⃣ | **GIẢI_THÍCH.md** | Khái niệm sâu | 30 min |
| 6️⃣ | **SUBMIT_REPORT.md** | Hướng dẫn nộp | 15 min |

**Tổng cộng**: ~80 phút để hiểu hết

---

## 💡 Phân loại code

### Ví dụ (Examples) - Học TCP/UDP cơ bản
```
4.1 HostInspector.java        ← Bắt đầu từ đây
    └─ Khảo sát địa chỉ mạng

4.2 TcpCommandServer.java     ← TCP học cơ bản
    TcpCommandClient.java
    └─ Server tuần tự, client gửi lệnh

4.3 MultiClientTcpServer.java ← TCP học nâng cao
    └─ Server phục vụ nhiều client

4.4 UdpEchoServer.java        ← UDP học cơ bản
    UdpEchoClient.java
    └─ Server/client gửi nhận datagram
```

### Bài tập (Exercises) - Áp dụng thực tế
```
Ex1 DigitToText.java    ← Đơn giản, validation
Ex2 DateTimeService.java ← Trung bình, TCP+UDP
Ex3 RemoteCalculator.java ← Phức tạp, xử lý lỗi
```

---

## 📊 Mapa học tập (Learning Path)

```
Level 1 - Cơ bản
├─ 4.1 HostInspector (IP address)
├─ 4.2 TcpCommandServer (TCP đơn giản)
└─ 4.4 UdpEchoServer (UDP đơn giản)

       ↓

Level 2 - Nâng cao
├─ 4.3 MultiClientTcpServer (Thread pool)
├─ Ex1 DigitToText (Validation)
└─ Ex2 DateTimeService (TCP + UDP cùng lúc)

       ↓

Level 3 - Chuyên sâu
└─ Ex3 RemoteCalculator (Protocol thiết kế, error handling)
```

---

## 🎯 Mục tiêu sau mỗi ví dụ/bài tập

### Sau khi hoàn thành 4.1:
- ✅ Hiểu IP address, localhost, loopback
- ✅ Biết cách dùng InetAddress
- ✅ Phân biệt IPv4 và IPv6

### Sau khi hoàn thành 4.2:
- ✅ Hiểu TCP kết nối
- ✅ Biết BufferedReader/PrintWriter
- ✅ Biết try-with-resources
- ✅ Hiểu giao thức dòng (line protocol)

### Sau khi hoàn thành 4.3:
- ✅ Hiểu thread pool
- ✅ Biết ExecutorService
- ✅ Hiểu lambda expression
- ✅ Biết quản lý nhiều client

### Sau khi hoàn thành 4.4:
- ✅ Hiểu UDP không kết nối
- ✅ Biết DatagramSocket/DatagramPacket
- ✅ Hiểu timeout (setSoTimeout)
- ✅ So sánh TCP vs UDP

### Sau khi hoàn thành Ex1-3:
- ✅ Thiết kế giao thức riêng
- ✅ Xử lý lỗi đầy đủ
- ✅ Áp dụng các khái niệm đã học

---

## 🔄 Quy trình học (Study Process)

```
1. ĐỌC
   Đọc mô tả ví dụ trong README.md
         ↓
2. HIỂU
   Đọc giải thích chi tiết trong GIẢI_THÍCH.md
         ↓
3. CHẠY
   Chạy lệnh từ RUN_COMMANDS.txt
         ↓
4. QUAN SÁT
   Nhìn output, hiểu code chạy thế nào
         ↓
5. THỬ
   Thay đổi code, chạy lại, xem kết quả
         ↓
6. VIẾT BÁO CÁO
   Screenshot + giải thích (SUBMIT_REPORT.md)
```

---

## ⚠️ Những điều PHẢI LÀM

✅ **PHẢI**:
- Dùng UTF-8 cho tiếng Việt
- Dùng try-with-resources
- Xử lý exception
- Thiết lập timeout UDP
- Test trên 2 máy (nếu có thể)

❌ **KHÔNG ĐƯỢC**:
- Lấy IP từ code (phải từ args)
- Dùng port < 1024 (cần admin)
- Quên đóng resource
- Chờ vô hạn UDP

---

## 🚨 Khắc phục nhanh

| Vấn đề | Cách sửa |
|-------|---------|
| "Address already in use" | Đợi 2 phút hoặc `netstat -ano \| findstr :5000` rồi kill |
| "Connection refused" | Chắc chắn server đang chạy |
| "Timeout" (UDP) | Check firewall, check port đúng |
| Tiếng Việt bị lỗi | Dùng StandardCharsets.UTF_8 |
| Project không biên dịch | Clean → Build (Eclipse) |

---

## 📝 Checklist hoàn thành

- [ ] Import vào Eclipse thành công
- [ ] Biên dịch không có lỗi
- [ ] Ví dụ 4.1 chạy thành công
- [ ] Ví dụ 4.2 chạy thành công
- [ ] Ví dụ 4.3 chạy (3 clients)
- [ ] Ví dụ 4.4 chạy thành công
- [ ] Bài tập 1 chạy thành công
- [ ] Bài tập 2 (TCP + UDP) chạy
- [ ] Bài tập 3 chạy thành công
- [ ] Lấy screenshots
- [ ] Viết báo cáo
- [ ] Push GitHub
- [ ] Nộp trên LMS

---

## 🔗 Các link quan trọng

- **README.md**: Tài liệu chi tiết → [Click](README.md)
- **RUN_COMMANDS.txt**: Tất cả lệnh → [Click](RUN_COMMANDS.txt)
- **GIẢI_THÍCH.md**: Khái niệm chi tiết → [Click](GIẢI_THÍCH.md)
- **SUBMIT_REPORT.md**: Hướng dẫn nộp → [Click](SUBMIT_REPORT.md)
- **INDEX.md**: Chỉ mục → [Click](INDEX.md)

---

## 💬 Gợi ý khi gặp vấn đề

### Lỗi biên dịch
```
1. Kiểm tra javac version: javac --version
2. Kiểm tra cú pháp: đọc lại ví dụ
3. Xóa bin/ rồi compile lại
```

### Lỗi runtime
```
1. Đọc error message kỹ
2. Xem ví dụ tương tự hoạt động không
3. Kiểm tra port/host có đúng không
```

### Code không chạy như mong đợi
```
1. Thêm System.out.println debug
2. Xem giải thích trong GIẢI_THÍCH.md
3. So sánh code với ví dụ hoàn chỉnh
```

---

## 📚 Tài liệu thêm

**Trong dự án**:
- README.md - Hướng dẫn chi tiết
- GIẢI_THÍCH.md - Khái niệm sâu
- RUN_COMMANDS.txt - Mọi lệnh

**Online**:
- [Java Networking Tutorial](https://docs.oracle.com/javase/tutorial/networking/)
- [Socket Programming in Java](https://www.geeksforgeeks.org/socket-programming-in-java/)
- [TCP vs UDP](https://www.diffen.com/difference/TCP-vs-UDP)

---

## ✨ Bạn sẽ học được

**Kiến thức**:
- TCP kết nối, UDP không kết nối
- Cách phân biệt giữa stream vs datagram
- Thiết kế giao thức riêng

**Kỹ năng**:
- Viết TCP server/client
- Viết UDP server/client
- Quản lý nhiều client với thread pool
- Xử lý error properly

**Ứng dụng thực tế**:
- Chat app
- File transfer
- Remote command execution
- Các ứng dụng mạng khác

---

## 🎊 Bắt đầu ngay!

### Step 1: Import vào Eclipse
```
Mở Eclipse → File → Import → Existing Projects into Workspace
→ d:\IUH\Tich Hop\lab04-socket → Finish
```

### Step 2: Biên dịch
```bash
javac -d bin src/network/*.java src/tcp/*.java src/udp/*.java src/exercise/*.java
```

### Step 3: Chạy ví dụ 4.1
```bash
java -cp bin network.HostInspector localhost
```

✅ Nếu thấy kết quả → Bạn đã thành công!

---

**Bây giờ, hãy đọc [INDEX.md](INDEX.md) hoặc [README.md](README.md) để bắt đầu thực hành! 🚀**

---

Cập nhật: 27/09/2026
