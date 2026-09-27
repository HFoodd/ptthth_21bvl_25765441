# 📑 CHỈ MỤC DỰ ÁN LAB 04

## 🎯 Nhanh chóng bắt đầu

1. **Import vào Eclipse**: [README.md](README.md) → "Import vào Eclipse"
2. **Biên dịch**: 
   ```bash
   javac -d bin src/network/*.java src/tcp/*.java src/udp/*.java src/exercise/*.java
   ```
3. **Chạy lệnh**: Xem [RUN_COMMANDS.txt](RUN_COMMANDS.txt)

---

## 📂 Cấu trúc file

```
lab04-socket/
├── src/
│   ├── network/
│   │   └── HostInspector.java
│   ├── tcp/
│   │   ├── TcpCommandServer.java
│   │   ├── TcpCommandClient.java
│   │   └── MultiClientTcpServer.java
│   ├── udp/
│   │   ├── UdpEchoServer.java
│   │   └── UdpEchoClient.java
│   └── exercise/
│       ├── Ex1_DigitToText.java
│       ├── Ex2_DateTimeService.java
│       └── Ex3_RemoteCalculator.java
├── bin/ (output biên dịch)
├── evidence/ (kết quả test)
├── .classpath (Eclipse config)
├── .project (Eclipse config)
├── .gitignore (Git ignore)
├── README.md (Tài liệu chính)
├── RUN_COMMANDS.txt (Các lệnh chạy)
├── GIẢI_THÍCH.md (Giải thích chi tiết)
├── SUBMIT_REPORT.md (Hướng dẫn nộp báo cáo)
└── INDEX.md (File này)
```

---

## 📚 Tài liệu

| File | Nội dung | Bạn nên đọc |
|------|---------|-----------|
| **README.md** | Giới thiệu, cách import Eclipse, hướng dẫn chi tiết | Trước tiên |
| **RUN_COMMANDS.txt** | Tất cả lệnh biên dịch và chạy | Khi muốn chạy |
| **GIẢI_THÍCH.md** | Giải thích khái niệm TCP, UDP, Socket, v.v. | Để hiểu sâu hơn |
| **SUBMIT_REPORT.md** | Hướng dẫn nộp báo cáo chi tiết | Khi chuẩn bị nộp |
| **INDEX.md** | Chỉ mục này | Để tìm file |

---

## 🔍 Các ví dụ (Examples)

### Ví dụ 4.1: Khảo sát địa chỉ mạng
- **File**: `src/network/HostInspector.java`
- **Port**: Không dùng port
- **Mô tả**: Phân giải hostname, xác định loại IP
- **Lệnh**:
  ```bash
  java -cp bin network.HostInspector localhost
  ```
- **Bạn sẽ học**: `InetAddress`, loopback, site local

---

### Ví dụ 4.2: TCP Server tuần tự
- **Files**: 
  - `src/tcp/TcpCommandServer.java`
  - `src/tcp/TcpCommandClient.java`
- **Port**: 5000
- **Mô tả**: Server lắng nghe lệnh, phục vụ tuần tự
- **Lệnh**:
  ```bash
  # Terminal 1
  java -cp bin tcp.TcpCommandServer
  
  # Terminal 2
  java -cp bin tcp.TcpCommandClient localhost 5000
  ```
- **Bạn sẽ học**: TCP, ServerSocket, BufferedReader/PrintWriter, try-with-resources

---

### Ví dụ 4.3: TCP Server đa client
- **File**: `src/tcp/MultiClientTcpServer.java`
- **Port**: 5000
- **Mô tả**: Server phục vụ nhiều client đồng thời bằng thread pool
- **Lệnh**:
  ```bash
  # Terminal 1
  java -cp bin tcp.MultiClientTcpServer
  
  # Terminal 2, 3, 4 (chạy đồng thời)
  java -cp bin tcp.TcpCommandClient localhost 5000
  ```
- **Bạn sẽ học**: ExecutorService, thread pool, lambda expression

---

### Ví dụ 4.4: UDP Echo Server
- **Files**:
  - `src/udp/UdpEchoServer.java`
  - `src/udp/UdpEchoClient.java`
- **Port**: 5001
- **Mô tả**: Server UDP gửi lại thông điệp với "ACK"
- **Lệnh**:
  ```bash
  # Terminal 1
  java -cp bin udp.UdpEchoServer
  
  # Terminal 2
  java -cp bin udp.UdpEchoClient localhost 5001 "xin chào UDP"
  ```
- **Bạn sẽ học**: UDP, DatagramSocket, DatagramPacket, timeout

---

## 💻 Các bài tập (Exercises)

### Bài tập 1: Chuyển chữ số thành chữ
- **File**: `src/exercise/Ex1_DigitToText.java`
- **Port**: 5010
- **Yêu cầu**: Client gửi 0-9, server trả tên tiếng Việt
- **Lệnh**:
  ```bash
  # Terminal 1
  java -cp bin exercise.Ex1_DigitToText server
  
  # Terminal 2
  java -cp bin exercise.Ex1_DigitToText client localhost 5010
  ```
- **Test**: Gửi 0, 5, 9, 10, a, ""
- **Bạn sẽ học**: Validation, xử lý lỗi

---

### Bài tập 2: Dịch vụ ngày giờ TCP/UDP
- **File**: `src/exercise/Ex2_DateTimeService.java`
- **Port**: TCP 5011, UDP 5012
- **Yêu cầu**: Trả về DATE, TIME, DATETIME theo yêu cầu
- **Lệnh**:
  ```bash
  # TCP Server
  java -cp bin exercise.Ex2_DateTimeService tcp-server
  
  # TCP Client
  java -cp bin exercise.Ex2_DateTimeService tcp-client localhost 5011
  
  # UDP Server (terminal khác)
  java -cp bin exercise.Ex2_DateTimeService udp-server
  
  # UDP Client
  java -cp bin exercise.Ex2_DateTimeService udp-client localhost 5012 DATE
  ```
- **Test**: DATE, TIME, DATETIME
- **Bạn sẽ học**: DateTimeFormatter, tương tự protocol cả TCP/UDP

---

### Bài tập 3: Máy tính từ xa
- **File**: `src/exercise/Ex3_RemoteCalculator.java`
- **Port**: 5013
- **Yêu cầu**: CALC <op> <num1> <num2>, hỗ trợ +, -, *, /
- **Lệnh**:
  ```bash
  # Terminal 1
  java -cp bin exercise.Ex3_RemoteCalculator server
  
  # Terminal 2
  java -cp bin exercise.Ex3_RemoteCalculator client localhost 5013
  ```
- **Test**: 
  - `CALC + 100 200` → OK 300
  - `CALC / 10 0` → ERR DIVIDE_BY_ZERO
  - `CALC + a 2` → ERR INVALID_NUMBER
- **Bạn sẽ học**: Thiết kế protocol, xử lý lỗi chi tiết

---

## 🎓 Khái niệm chính (Concepts)

### TCP (Transmission Control Protocol)
- ✅ Kết nối (phải bắt tay 3 lần trước khi gửi)
- ✅ Đảm bảo gửi đến, theo thứ tự
- ❌ Chậm hơn UDP
- **Dùng khi**: Chat, file transfer, API, email
- **File ví dụ**: TcpCommandServer, MultiClientTcpServer

### UDP (User Datagram Protocol)
- ❌ Không kết nối (gửi trực tiếp)
- ❌ Không đảm bảo, có thể mất gói
- ✅ Nhanh hơn TCP
- **Dùng khi**: Streaming, gaming, DNS, multicast
- **File ví dụ**: UdpEchoServer

### Stream vs Datagram
- **TCP (Stream)**: Luồng byte liên tục, cần giao thức để tách ranh giới
- **UDP (Datagram)**: Mỗi gói độc lập, ranh giới rõ ràng
- **Giải pháp TCP**: Dùng line protocol (`\n` kết thúc mỗi thông điệp)

### Thread Pool
- **Vấn đề**: Tạo thread không giới hạn → OutOfMemory
- **Giải pháp**: `ExecutorService.newFixedThreadPool(20)` → Tối đa 20 threads
- **Lợi ích**: An toàn, tái sử dụng, hiệu quả

### UTF-8
- **Vấn đề**: Tiếng Việt bị lỗi nếu charset sai
- **Giải pháp**: `StandardCharsets.UTF_8` luôn luôn
- **Áp dụng**: Đọc/ghi dữ liệu

### Try-with-resources
- **Cũ**: `try-finally` rồi đóng thủ công
- **Mới**: `try (resource) { code }` → tự động đóng
- **Lợi ích**: Code sạch, không quên đóng

---

## 🔧 Troubleshooting

| Vấn đề | Nguyên nhân | Giải pháp |
|-------|-----------|---------|
| Port already in use | Port đang bị dùng | Đợi 1-2 phút hoặc đổi port |
| Connection refused | Server không chạy | Chạy server trước |
| Socket timeout | UDP server không phản hồi | Kiểm tra server đang chạy? |
| Tiếng Việt bị lỗi | Charset sai | Dùng UTF-8 |
| Firewall chặn | 2 máy test, port bị chặn | Mở port qua firewall |
| Client chờ vô hạn | UDP không có timeout | Gọi `setSoTimeout(3000)` |

---

## 📊 Tóm tắt học phần

| Chủ đề | TCP | UDP |
|-------|-----|-----|
| Kết nối | Có | Không |
| Tin cậy | Đảm bảo | Không |
| Tốc độ | Chậm | Nhanh |
| Luồng | Stream | Datagram |
| Ví dụ | ChatServer | EchoServer |
| Port | 5000 | 5001 |

---

## 📝 Next Steps

1. ✅ **Import vào Eclipse**: Đọc README.md
2. ✅ **Biên dịch**: Chạy javac command
3. ✅ **Chạy ví dụ 4.1-4.4**: Xem RUN_COMMANDS.txt
4. ✅ **Chạy bài tập 1-3**: Xem RUN_COMMANDS.txt
5. ✅ **Chụp screenshots**: Lưu lại kết quả
6. ✅ **Nộp báo cáo**: Đọc SUBMIT_REPORT.md

---

## 🎓 Tài liệu tham khảo

- [Java Networking Documentation](https://docs.oracle.com/javase/tutorial/networking/)
- [RFC 793 - TCP Specification](https://tools.ietf.org/html/rfc793)
- [RFC 768 - UDP Specification](https://tools.ietf.org/html/rfc768)
- [UTF-8 Encoding](https://en.wikipedia.org/wiki/UTF-8)

---

## ❓ FAQ

**Q: Tôi không có Java 17?**
A: Tải Java 17+ từ https://www.oracle.com/java/technologies/downloads/

**Q: Eclipse không nhận diện project?**
A: Xem mục "Import vào Eclipse" ở README.md

**Q: Tôi muốn test trên 2 máy?**
A: Đọc mục "Khi chạy trên hai máy" ở README.md

**Q: Làm sao để push lên GitHub?**
A: Xem SUBMIT_REPORT.md → "Bước 2: Push code lên GitHub"

**Q: Báo cáo Word cần bao nhiêu trang?**
A: Khoảng 8-12 trang (tùy số hình ảnh)

**Q: Cần viết bao nhiêu comment trong code?**
A: Nên viết comment cho mỗi phương thức và logic phức tạp

---

## 📞 Liên hệ

Nếu có thắc mắc:
1. Xem mục tương ứng trong README.md
2. Xem GIẢI_THÍCH.md
3. Xem RUN_COMMANDS.txt
4. Liên hệ giảng viên

---

**Chúc bạn hoàn thành bài tập tốt! 🚀**

Cập nhật lần cuối: 27/09/2026
