# LAB 04 - LẬP TRÌNH SOCKET TCP VÀ UDP TRONG JAVA

## Giới thiệu

Dự án này chứa các ví dụ và bài tập thực hành về lập trình socket TCP và UDP trong Java. Mục tiêu là giúp sinh viên:
- Hiểu rõ về giao thức TCP (kết nối, tin cậy) và UDP (không kết nối, nhanh)
- Xây dựng ứng dụng client-server
- Xử lý dữ liệu mạng với charset UTF-8
- Quản lý nhiều client bằng thread pool

---

## Cấu trúc dự án

```
lab04-socket/
├── src/
│   ├── network/
│   │   └── HostInspector.java          (Ví dụ 4.1 - Khảo sát địa chỉ mạng)
│   ├── tcp/
│   │   ├── TcpCommandServer.java       (Ví dụ 4.2 - TCP Server tuần tự)
│   │   ├── TcpCommandClient.java       (Ví dụ 4.2 - TCP Client)
│   │   └── MultiClientTcpServer.java   (Ví dụ 4.3 - TCP Server đa client)
│   ├── udp/
│   │   ├── UdpEchoServer.java          (Ví dụ 4.4 - UDP Echo Server)
│   │   └── UdpEchoClient.java          (Ví dụ 4.4 - UDP Echo Client)
│   └── exercise/
│       ├── Ex1_DigitToText.java        (Bài tập 1 - Chuyển chữ số thành chữ)
│       ├── Ex2_DateTimeService.java    (Bài tập 2 - Dịch vụ ngày giờ TCP/UDP)
│       └── Ex3_RemoteCalculator.java   (Bài tập 3 - Máy tính từ xa)
├── bin/                                (Thư mục output biên dịch)
├── evidence/                           (Thư mục lưu kết quả kiểm thử)
├── .classpath                          (File cấu hình Eclipse)
├── .project                            (File cấu hình dự án Eclipse)
└── README.md                           (File này)
```

---

## Import vào Eclipse

### Cách 1: Import từ existing project
1. Mở Eclipse
2. **File** → **Import** → **General** → **Existing Projects into Workspace**
3. **Select root directory**: Chọn thư mục `lab04-socket`
4. Tick **Copy projects into workspace** (tùy chọn)
5. Click **Finish**

### Cách 2: Tạo thủ công
Nếu Eclipse không nhận diện được:
1. **File** → **New** → **Java Project**
2. **Project name**: `lab04-socket`
3. **Location**: Chỉ tới thư mục `lab04-socket`
4. **JRE**: Chọn **JavaSE-17** trở lên
5. Click **Finish**

---

## Biên dịch và chạy từ Command Line

### Biên dịch tất cả file
```bash
javac -d bin src/network/*.java src/tcp/*.java src/udp/*.java src/exercise/*.java
```

### Hoặc biên dịch riêng từng bộ

**VÍ DỤ 4.1 - Khảo sát địa chỉ mạng**
```bash
javac -d bin src/network/HostInspector.java
java -cp bin network.HostInspector localhost
java -cp bin network.HostInspector example.com
```

**VÍ DỤ 4.2 - TCP Client-Server tuần tự**

Terminal 1 (Server):
```bash
javac -d bin src/tcp/TcpCommandServer.java src/tcp/TcpCommandClient.java
java -cp bin tcp.TcpCommandServer
```

Terminal 2 (Client):
```bash
java -cp bin tcp.TcpCommandClient localhost 5000
```

Các lệnh để thử:
```
PING
UPPER xin chào
TIME
ABC
QUIT
```

**VÍ DỤ 4.3 - TCP Server đa client**

Terminal 1 (Server):
```bash
javac -d bin src/tcp/MultiClientTcpServer.java src/tcp/TcpCommandServer.java
java -cp bin tcp.MultiClientTcpServer
```

Terminal 2, 3, 4 (Các client):
```bash
java -cp bin tcp.TcpCommandClient localhost 5000
```

**VÍ DỤ 4.4 - UDP Echo**

Terminal 1 (Server):
```bash
javac -d bin src/udp/UdpEchoServer.java src/udp/UdpEchoClient.java
java -cp bin udp.UdpEchoServer
```

Terminal 2 (Client):
```bash
java -cp bin udp.UdpEchoClient localhost 5001 "xin chào UDP"
java -cp bin udp.UdpEchoClient localhost 5001 "hello"
```

**BÀI TẬP 1 - Chuyển chữ số thành chữ**

Terminal 1 (Server):
```bash
javac -d bin src/exercise/Ex1_DigitToText.java
java -cp bin exercise.Ex1_DigitToText server
```

Terminal 2 (Client):
```bash
java -cp bin exercise.Ex1_DigitToText client localhost 5010
```

Các lệnh để thử:
```
0
5
9
ABC
10
QUIT
```

**BÀI TẬP 2 - Dịch vụ ngày giờ**

TCP Server:
```bash
javac -d bin src/exercise/Ex2_DateTimeService.java
java -cp bin exercise.Ex2_DateTimeService tcp-server
```

TCP Client:
```bash
java -cp bin exercise.Ex2_DateTimeService tcp-client localhost 5011
```

UDP Server (terminal khác):
```bash
java -cp bin exercise.Ex2_DateTimeService udp-server
```

UDP Client (test nhanh):
```bash
java -cp bin exercise.Ex2_DateTimeService udp-client localhost 5012 DATE
java -cp bin exercise.Ex2_DateTimeService udp-client localhost 5012 TIME
java -cp bin exercise.Ex2_DateTimeService udp-client localhost 5012 DATETIME
```

**BÀI TẬP 3 - Máy tính từ xa**

Terminal 1 (Server):
```bash
javac -d bin src/exercise/Ex3_RemoteCalculator.java
java -cp bin exercise.Ex3_RemoteCalculator server
```

Terminal 2 (Client):
```bash
java -cp bin exercise.Ex3_RemoteCalculator client localhost 5013
```

Các lệnh để thử:
```
CALC + 100 200
CALC - 50 10
CALC * 5 6
CALC / 10 2
CALC / 10 0
CALC % 10 3
CALC + a 2
QUIT
```

---

## Giải thích chi tiết

### 1. TCP vs UDP

| Tiêu chí | TCP | UDP |
|---------|-----|-----|
| **Loại** | Oriented connection | Connectionless |
| **Độ tin cậy** | Đảm bảo gửi đến, theo thứ tự | Không đảm bảo |
| **Luồng dữ liệu** | Liên tục | Datagram (gói) |
| **Biên thông điệp** | Ứng dụng định nghĩa | Mỗi gói là một thông điệp |
| **Hiệu suất** | Chậm hơn (có kiểm soát lỗi) | Nhanh hơn |
| **Sử dụng** | Chat, file transfer, API | Khám phá dịch vụ, live video |

### 2. Thành phần chính

#### IP Address (Địa chỉ IP)
- Xác định máy trên mạng
- IPv4: 127.0.0.1 (localhost)
- IPv6: ::1 (localhost)
- Site Local: 192.168.x.x, 10.x.x.x

#### Port (Cổng)
- Xác định tiến trình trên máy
- Ports 0-1023: System (yêu cầu quyền admin)
- Ports 1024-49151: User ports
- Ports 49152-65535: Dynamic ports

#### Socket
- **ServerSocket** (TCP): Lắng nghe kết nối
- **Socket** (TCP): Kết nối client-server
- **DatagramSocket** (UDP): Gửi/nhận datagram

### 3. Try-with-resources

Cách cũ:
```java
Socket socket = new Socket("localhost", 5000);
try {
    // code
} finally {
    socket.close();
}
```

Cách mới (try-with-resources):
```java
try (Socket socket = new Socket("localhost", 5000)) {
    // code
} // tự động close
```

**Lợi ích:**
- Tự động đóng tài nguyên
- Xử lý exception tốt hơn
- Code sạch sẽ hơn

### 4. Thread Pool

Thay vì tạo Thread không giới hạn:
```java
new Thread(() -> serve(socket)).start();  // ❌ Rủi ro OutOfMemory
```

Dùng ExecutorService:
```java
ExecutorService pool = Executors.newFixedThreadPool(20);
pool.submit(() -> serve(socket));  // ✓ Toàn bộ hơn
```

**Lợi ích:**
- Giới hạn số luồng
- Tái sử dụng luồng (hiệu quả hơn)
- Dễ quản lý

### 5. Giao thức Dòng (Line Protocol)

Mỗi thông điệp là một dòng UTF-8 kết thúc bằng `\n`:

```
Client: PING\n
Server: OK PONG\n

Client: UPPER xin chào\n
Server: OK XIN CHÀO\n

Client: TIME\n
Server: OK 2026-09-13T10:30:00\n
```

### 6. UDP Timeout

```java
DatagramSocket socket = new DatagramSocket();
socket.setSoTimeout(3000);  // 3 giây timeout

try {
    socket.receive(packet);
} catch (SocketTimeoutException e) {
    System.out.println("Hết thời gian chờ");
}
```

**Tại sao cần timeout?**
- UDP không có kết nối → không biết khi nào server sẽ phản hồi
- Nếu server chưa chạy hoặc gói bị mất → client chờ vô hạn

---

## Kiểm thử

### Ví dụ 4.1 - HostInspector

| Test | Lệnh | Kết quả mong đợi |
|------|------|------------------|
| Localhost | `java network.HostInspector localhost` | Ít nhất 1 IP, Loopback=true |
| Hostname hợp lệ | `java network.HostInspector example.com` | 1+ IP, Loopback=false |
| Hostname sai | `java network.HostInspector host-invalid.invalid` | Thông báo lỗi rõ ràng |

### Ví dụ 4.2 - TCP Command Server

| Test | Lệnh | Kết quả |
|------|------|---------|
| PING | `PING` | `OK PONG` |
| TIME | `TIME` | `OK [ngày giờ]` |
| UPPER | `UPPER xin chào` | `OK XIN CHÀO` |
| Lệnh sai | `ABC` | `ERR UNKNOWN_COMMAND` |

### Bài tập 1 - Digit to Text

| Input | Output |
|-------|--------|
| 0 | OK Không |
| 5 | OK Năm |
| 9 | OK Chín |
| 10 | ERR INVALID_DIGIT |
| a | ERR INVALID_DIGIT |
| (rỗng) | ERR INVALID_DIGIT |

### Bài tập 2 - Date/Time Service

| TCP Input | TCP Output | UDP Input | UDP Output |
|-----------|------------|-----------|-----------|
| DATE | OK 27/09/2026 | DATE | OK 27/09/2026 |
| TIME | OK 14:30:45 | TIME | OK 14:30:45 |
| DATETIME | OK 27/09/2026 14:30:45 | DATETIME | OK 27/09/2026 14:30:45 |
| QUIT | OK BYE | - | - |

### Bài tập 3 - Remote Calculator

| Input | Output |
|-------|--------|
| CALC + 100 200 | OK 300 |
| CALC - 50 10 | OK 40 |
| CALC * 5 6 | OK 30 |
| CALC / 10 2 | OK 5 |
| CALC / 10 0 | ERR DIVIDE_BY_ZERO |
| CALC % 10 3 | ERR UNSUPPORTED_OPERATOR |
| CALC + a 2 | ERR INVALID_NUMBER |
| CALC + | ERR INVALID_FORMAT |

---

## Lưu ý quan trọng

### 1. Charset UTF-8
Luôn sử dụng UTF-8 để bảo toàn tiếng Việt:
```java
new InputStreamReader(stream, StandardCharsets.UTF_8)
new OutputStreamWriter(stream, StandardCharsets.UTF_8)
```

### 2. Đọc đúng số byte
Với UDP, luôn lấy `getLength()`:
```java
String message = new String(packet.getData(),
    packet.getOffset(),
    packet.getLength(),      // ← QUAN TRỌNG
    StandardCharsets.UTF_8);
```

### 3. Port > 1024
Để tránh yêu cầu quyền admin:
```java
new ServerSocket(5000);  // ✓ OK
new ServerSocket(80);    // ❌ Cần admin
```

### 4. Firewall
Khi test trên 2 máy, cho phép port qua firewall:
```bash
# Windows Firewall
netsh advfirewall firewall add rule name="Java Socket Lab" dir=in action=allow program="java.exe" protocol=tcp localport=5000

# Linux
sudo ufw allow 5000/tcp
```

### 5. Đóng properly
Luôn sử dụng try-with-resources hoặc đóng trong `finally`:
```java
try (Socket socket = new Socket(...)) {
    // code
}  // tự động close ✓
```

---

## Vấn đề thường gặp

| Vấn đề | Nguyên nhân | Giải pháp |
|-------|-----------|----------|
| Connection refused | Server không chạy | Chạy server trước, kiểm tra port |
| Socket timeout | UDP không nhận được gói | Kiểm tra port UDP, firewall |
| Tiếng Việt bị lỗi | Charset sai | Dùng StandardCharsets.UTF_8 |
| "Address already in use" | Port bị chiếm | Đợi 1-2 phút hoặc thay port |
| Client chờ vô hạn | Không có timeout | Gọi `setSoTimeout()` |

---

## Tài liệu tham khảo

- [Java Networking Documentation](https://docs.oracle.com/javase/tutorial/networking/)
- [Socket vs DatagramSocket](https://stackoverflow.com/questions/1529689/java-socket-vs-datagram-socket)
- [UTF-8 in Java](https://docs.oracle.com/javase/tutorial/i18n/resbundle/propfile.html)

---

## Hướng dẫn làm báo cáo

1. **Phần 1: Mã nguồn**
   - Dán link GitHub chứa code
   - Hoặc nộp file ZIP

2. **Phần 2: Kết quả chạy**
   - Chụp màn hình VSCode/Eclipse + terminal output
   - Thực hiện các test case cho mỗi ví dụ
   - Ghi chú kết quả mong đợi vs kết quả thực tế

3. **Phần 3: Giải thích**
   - Giải thích các lớp Socket, ServerSocket, DatagramSocket
   - So sánh TCP vs UDP cho từng ví dụ
   - Nêu lý do chọn TCP hoặc UDP

4. **Phần 4: Kết luận**
   - Những điều đã học được
   - Ứng dụng trong thực tế

---

**Tác giả**: IT.FIT.IUH v2026  
**Cập nhật**: 2026-09-27  
**Phiên bản**: 1.0
