# HƯỚNG DẪN NỘP BÁO CÁO LAB 04

## 📋 Nội dung báo cáo bắt buộc

### 1. Link GitHub chứa code
```
Đầu file báo cáo Word:

GitHub Repository: https://github.com/[username]/lab04-socket
```

**Hướng dẫn tạo GitHub repository**:

#### Bước 1: Tạo repo trên GitHub
1. Đăng nhập vào https://github.com
2. Click "+" → "New repository"
3. **Repository name**: `lab04-socket`
4. **Description**: "Lab 04 - Lập trình Socket TCP và UDP trong Java"
5. **Visibility**: Public (để giảng viên xem được)
6. Click "Create repository"

#### Bước 2: Push code lên GitHub
```bash
cd d:\IUH\Tich\ Hop\lab04-socket

# Khởi tạo git (nếu chưa có)
git init

# Thêm remote (thay YOUR_USERNAME bằng username GitHub của bạn)
git remote add origin https://github.com/YOUR_USERNAME/lab04-socket.git

# Thêm tất cả file
git add .

# Commit
git commit -m "Lab 04 - Socket TCP/UDP - Initial commit"

# Push lên GitHub
git branch -M main
git push -u origin main
```

**Nếu bị lỗi authentication**:
- Sử dụng Personal Access Token thay vì password
- Hoặc setup SSH key: https://docs.github.com/en/authentication/connecting-to-github-with-ssh

---

### 2. Screenshot VSCode + Kết quả chạy

#### Những gì cần chụp:

**A. Chụp cấu trúc dự án**
```
File → Explorer (hoặc Ctrl+Shift+E)
Hiển thị: src/, bin/, README.md, v.v.
```

**B. Chụp code (mỗi ví dụ/bài tập)**
```
Mỗi file Java:
- TcpCommandServer.java (toàn bộ file)
- TcpCommandClient.java (toàn bộ file)
- UdpEchoServer.java (toàn bộ file)
- UdpEchoClient.java (toàn bộ file)
- Ex1_DigitToText.java (toàn bộ file)
- Ex2_DateTimeService.java (toàn bộ file)
- Ex3_RemoteCalculator.java (toàn bộ file)
```

**C. Chụp kết quả chạy mỗi ví dụ**

1. **Ví dụ 4.1 - HostInspector**:
   - Terminal chạy: `java network.HostInspector localhost`
   - Hiển thị kết quả đầy đủ

2. **Ví dụ 4.2 - TCP Server tuần tự**:
   - Terminal 1: Server đang chạy
   - Terminal 2: Client gửi các lệnh PING, UPPER, TIME, QUIT
   - Hiển thị request/response

3. **Ví dụ 4.3 - Multi-client TCP Server**:
   - Terminal 1: Server
   - Terminal 2, 3, 4: Ba clients
   - Chụp các client gửi lệnh đồng thời

4. **Ví dụ 4.4 - UDP Echo**:
   - Terminal 1: Server
   - Terminal 2: Client gửi thông điệp "xin chào UDP"
   - Chụp kết quả ACK

5. **Bài tập 1 - Digit to Text**:
   - Terminal 1: Server
   - Terminal 2: Client test các trường hợp (0-9, lỗi, QUIT)

6. **Bài tập 2 - DateTime Service**:
   - Terminal 1: TCP Server
   - Terminal 2: TCP Client (DATE, TIME, DATETIME, QUIT)
   - Terminal 3: UDP Server
   - Terminal 4: UDP Client (DATE, TIME, DATETIME)

7. **Bài tập 3 - Remote Calculator**:
   - Terminal 1: Server
   - Terminal 2: Client (CALC + 100 200, CALC / 10 0, v.v.)

---

## 📝 Cấu trúc báo cáo Word

### Trang tiêu đề
```
═════════════════════════════════════════════════════════
        TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP TP.HCM
           BỘ MÔN: CÔNG NGHỆ PHẦN MỀM
          KHÓA: [năm học] - [học kỳ]

             BÁO CÁO THỰC HÀNH
        LAB 04: LẬP TRÌNH SOCKET TCP/UDP TRONG JAVA

Sinh viên: [Họ tên]
MSSV: [Mã số sinh viên]
Lớp: [Tên lớp]
Ngày nộp: [DD/MM/YYYY]

GitHub Repository: https://github.com/[username]/lab04-socket
═════════════════════════════════════════════════════════
```

### Nội dung chính

#### 1. MỤC TIÊU (Trang 1)
```
- Hiểu rõ TCP vs UDP
- Xây dựng TCP client-server
- Xây dựng UDP client-server
- Quản lý nhiều client bằng thread pool
- Xử lý tiếng Việt với UTF-8
```

#### 2. KIẾN THỨC NỀN (Trang 1-2)
```
1. TCP (Transmission Control Protocol)
   - Kết nối trước khi gửi dữ liệu
   - Đảm bảo gửi đến, theo thứ tự
   - Phù hợp: chat, file transfer, API

2. UDP (User Datagram Protocol)
   - Không kết nối
   - Nhanh nhưng không đảm bảo
   - Phù hợp: streaming, gaming, DNS

3. Socket, Port, IP Address
4. Try-with-resources
5. Thread Pool (ExecutorService)
```

#### 3. CÁC VÍ DỤ (Trang 2-4)

**Ví dụ 4.1 - Khảo sát địa chỉ mạng (1/2 trang)**
```
Mô tả:
- Sử dụng InetAddress để phân giải hostname
- Xác định loại IP (IPv4/IPv6), loopback, site local

Code:
- Dán toàn bộ HostInspector.java

Kết quả kiểm thử:
- localhost → Loopback = true
- example.com → IP công cộng
- host-invalid → Lỗi (xử lý tốt)

Giải thích:
- InetAddress.getAllByName() trả array IP
- isLoopbackAddress() kiểm tra localhost
- isSiteLocalAddress() kiểm tra mạng riêng
```

**Ví dụ 4.2 - TCP Server tuần tự (1 trang)**
```
Mô tả:
- Server lắng nghe kết nối trên port 5000
- Client gửi lệnh PING, TIME, UPPER, QUIT
- Phục vụ tuần tự (một client tại một lúc)

Giao thức:
- PING → OK PONG
- TIME → OK [ngày giờ]
- UPPER <text> → OK [text in hoa]
- QUIT → OK BYE

Code:
- TcpCommandServer.java (ghi chú quan trọng)
- TcpCommandClient.java

Kết quả kiểm thử:
- [Chụp screenshot terminal]

So sánh với Ví dụ 4.3:
- Server tuần tự: Nếu 2 clients kết nối, client 2 phải chờ client 1
- Server đa client: Cả 2 clients hoạt động cùng lúc

Tại sao lại hạn chế?
- Số lượng threads không bị giới hạn → OutOfMemory nếu 1000 clients
```

**Ví dụ 4.3 - Multi-client TCP Server (1 trang)**
```
Mô tả:
- Sử dụng ExecutorService.newFixedThreadPool(20)
- Có thể phục vụ tối đa 20 clients cùng lúc
- Mỗi client chạy trong một thread riêng

Code:
- MultiClientTcpServer.java (giải thích thread pool)

Kết quả kiểm thử:
- 3 clients kết nối cùng lúc
- Mỗi client gửi lệnh, tất cả phản hồi nhanh
- [Screenshot 3 terminals]

Lợi ích:
- Không bị chặn tuần tự
- Số threads bị giới hạn (an toàn)
- Luồng tái sử dụng (hiệu quả)
```

**Ví dụ 4.4 - UDP Echo (1 trang)**
```
Mô tả:
- Server UDP nhận datagram
- Server gửi lại với tiền tố "ACK"
- Client có timeout 3 giây

Giao thức:
- Client gửi: "xin chào UDP"
- Server trả: "ACK XIN CHÀO UDP"

Code:
- UdpEchoServer.java
- UdpEchoClient.java

Kết quả kiểm thử:
- Test thành công: "xin chào UDP" → "ACK XIN CHÀO UDP"
- Test timeout: Server không chạy → timeout sau 3 giây
- [Screenshots]

Khác biệt UDP vs TCP:
- TCP: Phải kết nối trước, stream liên tục
- UDP: Gửi trực tiếp, mỗi gói độc lập
```

#### 4. CÁC BÀI TẬP (Trang 4-6)

**Bài tập 1 - Digit to Text (1/2 trang)**
```
Yêu cầu:
- Client gửi chữ số (0-9)
- Server trả tên tiếng Việt

Code:
- Ex1_DigitToText.java

Kết quả kiểm thử:
- Input: 0, 5, 9 → Correct outputs
- Input: 10, a, rỗng → ERR INVALID_DIGIT
- Input: " 5 " (có space) → OK Năm
- [Screenshots]

Xử lý lỗi:
- Kiểm tra độ dài chuỗi = 1
- Kiểm tra isDigit()
```

**Bài tập 2 - DateTime Service (1 trang)**
```
Yêu cầu:
- TCP: DATE, TIME, DATETIME, QUIT
- UDP: DATE, TIME, DATETIME (không QUIT)
- Format: dd/MM/yyyy và HH:mm:ss

Code:
- Ex2_DateTimeService.java

Kết quả kiểm thử:
- TCP: DATE → OK 27/09/2026
- TCP: DATETIME → OK 27/09/2026 14:30:45
- UDP: TIME → OK 14:30:45
- [Screenshots TCP]
- [Screenshots UDP]

Nhận xét:
- TCP có QUIT, UDP không
- Format date/time giống nhau
```

**Bài tập 3 - Remote Calculator (1 trang)**
```
Yêu cầu:
- Lệnh: CALC <operator> <num1> <num2>
- Hỗ trợ: +, -, *, /
- Xử lý lỗi: DIVIDE_BY_ZERO, INVALID_NUMBER, INVALID_FORMAT

Các phép toán:
- CALC + 100 200 → OK 300
- CALC / 10 0 → ERR DIVIDE_BY_ZERO
- CALC % 10 3 → ERR UNSUPPORTED_OPERATOR

Code:
- Ex3_RemoteCalculator.java (giải thích process())

Kết quả kiểm thử:
- [Screenshots thành công]
- [Screenshots lỗi]

Xử lý lỗi:
- Kiểm tra định dạng
- Try-catch NumberFormatException
- Kiểm tra chia cho 0
```

#### 5. KIỂM THỬ TRÊN 2 MÁY (1/2 trang - Optional)
```
Nếu bạn test trên 2 máy:

Cách làm:
1. Tìm IPv4 của server: ipconfig (Windows) hoặc ip addr (Linux)
2. Mở port trên firewall
3. Client kết nối tới IP server

Ví dụ:
- Server: 192.168.1.100:5000
- Client: java tcp.TcpCommandClient 192.168.1.100 5000

Kết quả:
- [Screenshots chạy trên 2 máy]

Lưu ý:
- Phải cùng mạng LAN
- Firewall phải cho phép port
```

#### 6. NỘI DUNG ĐÃ HỌC (1/2 trang)
```
1. TCP kết nối, UDP không kết nối
   - TCP: Bắt tay 3 lần, đảm bảo, chậm hơn
   - UDP: Gửi trực tiếp, nhanh, không đảm bảo

2. Stream vs Datagram
   - TCP stream: Cần giao thức (dấu kết thúc)
   - UDP datagram: Mỗi gói tự hoàn chỉnh

3. Thread Pool
   - Giới hạn luồng, tái sử dụng, an toàn hơn

4. UTF-8
   - Bảo toàn tiếng Việt
   - Phải chỉ định StandardCharsets.UTF_8

5. Try-with-resources
   - Tự động đóng resource
   - Code sạch sẽ hơn

6. Socket timeout
   - UDP cần timeout (3 giây)
   - Không chờ vô hạn
```

#### 7. KẾT LUẬN (1/2 trang)
```
Những điểm chính:
1. TCP phù hợp các ứng dụng cần độ tin cậy cao
2. UDP phù hợp các ứng dụng cần tốc độ cao
3. Thread pool giúp quản lý nhiều client hiệu quả
4. Charset UTF-8 bảo toàn dữ liệu tiếng Việt
5. Timeout quan trọng trong UDP để không chờ vô hạn

Khó khăn gặp phải:
- [Nếu có]

Đề xuất cải tiến:
- Thêm heartbeat trong TCP để phát hiện kết nối chết
- Thêm retry trong UDP nếu timeout
- Thêm encryption cho dữ liệu nhạy cảm
```

---

## 📸 Gợi ý chụp screenshot

### Tool:
- **VSCode**: Built-in screenshot (Ctrl+Shift+P → "Take Screenshot")
- **Eclipse**: Sử dụng Windows Snipping Tool
- **Terminal**: Chỉ cần copy terminal output paste vào Word

### Layout tốt:
```
┌─────────────────────────────────────┐
│  VSCode (phần code)                 │
│                                     │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  Terminal output                    │
│  (Server: ...)                      │
│  (Client: ...)                      │
└─────────────────────────────────────┘
```

### Tối ưu:
- Font size >= 10pt để dễ đọc
- Crop image để chỉ hiển thị phần quan trọng
- Thêm caption giải thích dưới mỗi hình

---

## ✅ Checklist trước khi nộp

- [ ] GitHub repository được tạo và public
- [ ] Code được push lên GitHub
- [ ] Link GitHub có trong báo cáo
- [ ] Tất cả 7 ví dụ/bài tập được chạy thành công
- [ ] Screenshots rõ ràng, không mờ
- [ ] Code có comment giải thích
- [ ] Báo cáo Word có đầy đủ:
  - [ ] Mục tiêu
  - [ ] Kiến thức nền
  - [ ] 4 ví dụ + kết quả
  - [ ] 3 bài tập + kết quả
  - [ ] Nội dung đã học
  - [ ] Kết luận
- [ ] Định dạng Word đúng chuẩn
- [ ] Không có lỗi chính tả (kiểm tra lại)

---

## 🚀 Nộp báo cáo

**Nơi nộp**: [LMS link từ giảng viên]

**Định dạng file**:
- Báo cáo: `Lab04_[MSSV]_[HọTên].docx`
- Ví dụ: `Lab04_21110123_NguyễnVănA.docx`

**Hạn chót**: [Theo thông báo giảng viên]

---

## 📞 Liên hệ

Nếu có thắc mắc:
1. Xem README.md trong project
2. Xem GIẢI_THÍCH.md để hiểu chi tiết
3. Chạy RUN_COMMANDS.txt để test lại
4. Liên hệ giảng viên

---

**Chúc bạn hoàn thành bài tập tốt! 🎉**
