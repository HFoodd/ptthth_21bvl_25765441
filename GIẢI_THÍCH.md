# GIẢI THÍCH CHI TIẾT - LẬP TRÌNH SOCKET TCP VÀ UDP TRONG JAVA

---

## 1. KHÁI NIỆM SOCKET

### Socket là gì?
**Socket** (ổ cắm) là một điểm cuối của kết nối mạng. Nó cho phép hai chương trình giao tiếp với nhau qua mạng.

### Hình ảnh:
```
┌──────────────┐                    ┌──────────────┐
│   Client     │ ◄───────────────► │   Server     │
│   Socket     │   (truyền dữ liệu) │   Socket     │
└──────────────┘                    └──────────────┘
```

### Cần những thành phần gì?
1. **IP Address**: Địa chỉ máy tính (vd: 127.0.0.1 = localhost)
2. **Port**: Cổng để xác định ứng dụng (vd: 5000, 8080)
3. **Giao thức**: TCP hoặc UDP
4. **Dữ liệu**: Thông điệp gửi đi

Ví dụ: `192.168.1.10:5000/TCP`
- IP: 192.168.1.10
- Port: 5000
- Giao thức: TCP

---

## 2. TCP vs UDP

### Bảng so sánh chi tiết

| Tiêu chí | TCP | UDP |
|---------|-----|-----|
| **Tên đầy đủ** | Transmission Control Protocol | User Datagram Protocol |
| **Kết nối** | Có (phải kết nối trước) | Không (gửi trực tiếp) |
| **Tin cậy** | Đảm bảo gửi đến, theo thứ tự | Không đảm bảo |
| **Tốc độ** | Chậm hơn (kiểm soát) | Nhanh hơn |
| **Dữ liệu** | Luồng byte liên tục | Gói dữ liệu (datagram) |
| **Biên thông điệp** | Ứng dụng định nghĩa | Rõ ràng (mỗi gói) |
| **Thứ tự** | Bảo đảm | Không bảo đảm |
| **Trùng lặp** | Không có | Có thể có |
| **Overhead** | Cao (kiểm soát lỗi) | Thấp |

### Ví dụ hình ảnh TCP:
```
CLIENT                              SERVER
│                                    │
├─── SYN (bắt tay 1) ─────────────►│
│                                    │
│◄──── SYN-ACK (bắt tay 2) ─────────┤
│                                    │
├─── ACK (bắt tay 3) ──────────────►│
│                                    │
│  Kết nối đã thiết lập ✓            │
│                                    │
├─── Data: "PING" ─────────────────►│
│                                    ├── Xử lý
│◄── Data: "OK PONG" ───────────────┤
│                                    │
├─── FIN (đóng) ───────────────────►│
│                                    │
│◄─── FIN-ACK (xác nhận) ───────────┤
```

### Ví dụ hình ảnh UDP:
```
CLIENT                              SERVER
│                                    │
├─── Datagram: "xin chào UDP" ────►│
│                                    ├── Xử lý
│◄── Datagram: "ACK XIN CHÀO UDP" ─┤
│
(Không có kết nối, không có xác nhận)
```

### Khi nào dùng TCP?
- **Chat**: Cần đảm bảo tin nhắn gửi đến
- **File transfer**: Không được mất dữ liệu
- **API/HTTP**: Cần độ tin cậy cao
- **Email**: Phải gửi đến chính xác
- **Transaction**: Không được sai lệch

### Khi nào dùng UDP?
- **Live video/streaming**: Mất 1-2 frame không sao
- **Online game**: Tốc độ quan trọng hơn chính xác 100%
- **VoIP**: Độ trễ thấp quan trọng
- **DNS**: Nhanh, có thể retry
- **Multicast**: Gửi tới nhiều máy cùng lúc
- **Telemetry**: Mất vài data point không sao

---

## 3. STREAM vs DATAGRAM

### TCP - Stream (Luồng)
```
Client gửi: "Hello World"
Server nhận không rõ biên:
  - Có thể nhận "Hello"
  - Rồi nhận " World"
  - Hoặc "HelloWorld" một lần

Server phải tự định nghĩa khi nào là hết thông điệp!
```

**Giải pháp**: Sử dụng giao thức dòng (line protocol)
- Mỗi thông điệp kết thúc bằng ký tự `\n`
- Server dùng `BufferedReader.readLine()` tự động tách dòng

```java
// Ví dụ:
out.println("PING");              // Gửi "PING\n"
String response = in.readLine();  // Đọc cho tới \n
```

### UDP - Datagram (Gói)
```
Client gửi datagram 1: "Hello"
Client gửi datagram 2: "World"

Server nhận datagram 1: "Hello" ✓ (rõ ràng)
Server nhận datagram 2: "World" ✓ (rõ ràng)

Mỗi gói là một thông điệp hoàn chỉnh!
```

---

## 4. IP ADDRESS VÀ LOCALHOST

### IPv4 vs IPv6

**IPv4**:
- Định dạng: `a.b.c.d` (4 số từ 0-255)
- Ví dụ: `192.168.1.1`, `8.8.8.8`
- Localhost: `127.0.0.1`

**IPv6**:
- Định dạng: `hex:hex:hex:hex:hex:hex:hex:hex`
- Ví dụ: `2001:0db8:85a3:0000:0000:8a2e:0370:7334`
- Localhost: `::1`

### Site Local (Mạng riêng)
```
- 10.0.0.0     - 10.255.255.255
- 172.16.0.0   - 172.31.255.255
- 192.168.0.0  - 192.168.255.255
```

Ví dụ:
- `192.168.1.10`: Mạng gia đình riêng
- `10.0.0.1`: Mạng công ty riêng
- `8.8.8.8`: IP công cộng (Google DNS)

### Loopback Address
- `127.0.0.1` (IPv4) hoặc `::1` (IPv6)
- Dùng để test trên cùng một máy
- Không gửi qua mạng, chỉ nội bộ máy

```java
InetAddress addr = InetAddress.getByName("localhost");
if (addr.isLoopbackAddress()) {
    System.out.println("Đây là localhost");
}
```

---

## 5. PORT - CỔNG

### Port là gì?
Port là số từ 0-65535 dùng để xác định ứng dụng trên máy.

### Phân loại Port:
```
0 - 1023      : System Ports (cổng hệ thống)
               Yêu cầu quyền admin
               Ví dụ: 80 (HTTP), 443 (HTTPS), 22 (SSH)

1024 - 49151  : User Ports (cổng người dùng)
               Có thể dùng tự do
               Ví dụ: 5000, 5001, 8080, 3000

49152 - 65535 : Dynamic Ports (cổng động)
               Dùng cho client tạm thời
```

### Các port phổ biến:
```
21     - FTP (File Transfer Protocol)
22     - SSH (Secure Shell)
80     - HTTP (Web)
443    - HTTPS (Web an toàn)
3306   - MySQL
5432   - PostgreSQL
6379   - Redis
8080   - Application server (alt HTTP)
3000   - Node.js dev server
5000   - Flask dev server
5000   - Lab này sử dụng (TCP)
5001   - Lab này sử dụng (UDP)
```

### Lỗi "Address already in use":
Nếu port đang bị dùng:
- Đợi 1-2 phút (OS cần thời gian release port)
- Hoặc thay port trong code
- Hoặc tìm và kill process dùng port đó

```bash
# Windows - Tìm process dùng port 5000:
netstat -ano | findstr :5000

# Linux - Tìm process dùng port 5000:
lsof -i :5000
```

---

## 6. TRY-WITH-RESOURCES

### Vấn đề cũ - Quên đóng resource:
```java
InputStream in = socket.getInputStream();
BufferedReader reader = new BufferedReader(new InputStreamReader(in));
// Đọc dữ liệu...
// ❌ Quên đóng reader!
```

### Cách cũ - Dùng finally:
```java
BufferedReader reader = null;
try {
    reader = new BufferedReader(new InputStreamReader(...));
    // code
} finally {
    if (reader != null) {
        reader.close();  // Phải đóng thủ công
    }
}
```

### Cách mới - Try-with-resources (Java 7+):
```java
try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()))) {
    // code
}  // Tự động đóng ✓
```

### Lợi ích:
1. **Tự động đóng**: Không cần `finally`
2. **Đơn giản hơn**: Ít code hơn
3. **An toàn hơn**: Không quên đóng
4. **Multiple resources**: Có thể mở nhiều resource cùng lúc

```java
// Mở nhiều resource cùng lúc:
try (Socket socket = new Socket("localhost", 5000);
     BufferedReader in = new BufferedReader(
         new InputStreamReader(socket.getInputStream()));
     PrintWriter out = new PrintWriter(
         new OutputStreamWriter(socket.getOutputStream()))) {
    // Tất cả resource tự động đóng khi try block kết thúc
}
```

**Quy tắc**: Mọi resource mở với constructor phải implements `AutoCloseable`

---

## 7. THREAD POOL VÀ EXECUTORSERVICE

### Vấn đề - Tạo thread không giới hạn:
```java
while (true) {
    Socket socket = server.accept();
    new Thread(() -> serve(socket)).start();  // ❌ Nguy hiểm!
}
```

**Khi có 1000 clients** → Tạo 1000 threads → **OutOfMemoryError** ❌

### Giải pháp - Thread Pool:
```java
ExecutorService pool = Executors.newFixedThreadPool(20);

while (true) {
    Socket socket = server.accept();
    pool.submit(() -> serve(socket));  // ✓ An toàn
}
```

**Lợi ích**:
1. **Giới hạn luồng**: Tối đa 20 luồng, không có 1000
2. **Tái sử dụng**: Luồng dùng xong đưa lại pool, không cần tạo mới
3. **Hiệu quả hơn**: Tạo luồng mất thời gian, tái sử dụng nhanh hơn
4. **Quản lý tốt**: Có thể shutdown() khi server dừng

### Cách dùng:
```java
// Tạo pool
ExecutorService pool = Executors.newFixedThreadPool(20);

// Giao task
pool.submit(() -> {
    System.out.println("Task 1");
});

// Dừng (chờ tất cả task hoàn thành)
pool.shutdown();
```

---

## 8. CHARSET - MÃ HÓA KÝ TỰ

### Vấn đề - Tiếng Việt bị lỗi:
```
Gửi: "xin chào"
Nhận: "xin ch??o" hoặc "xxx"
```

**Nguyên nhân**: Charset mặc định sai (không phải UTF-8)

### Giải pháp - Luôn dùng UTF-8:
```java
// ❌ KHÔNG dùng:
BufferedReader in = new BufferedReader(new InputStreamReader(stream));

// ✓ LUÔN DÙNG:
BufferedReader in = new BufferedReader(
    new InputStreamReader(stream, StandardCharsets.UTF_8));
```

### Các charset phổ biến:
```
UTF-8       - Unicode 8 bit (toàn cầu) ← DÙNG CÁI NÀY
ISO-8859-1  - Latin 1 (châu Âu cổ)
US-ASCII    - Chỉ ký tự tiếng Anh (0-127)
GB2312      - Tiếng Trung
KOI8-R      - Tiếng Nga
```

### UTF-8 là gì?
```
'A' → 1 byte:  01000001
'á' → 2 bytes: 11000011 10100001
'你' → 3 bytes: 11100100 10111101 10010000
```

UTF-8 **tự động thích ứng** với ký tự - thông minh!

---

## 9. DATAGRAM vs STREAM

### Với TCP (Stream):
```
Client gửi 1 lần: "HELLO WORLD"

Server có thể nhận:
  Lần 1: "HE"
  Lần 2: "LLO WO"
  Lần 3: "RLD"
  
Server không biết "HELLO WORLD" là 1 thông điệp!
```

**Cần định nghĩa biên thông điệp**: 
- Cách 1: Kết thúc bằng ký tự đặc biệt (vd: `\n`)
- Cách 2: Gửi độ dài trước (vd: `[12]HELLO WORLD`)

Ví dụ giao thức dòng:
```
Client: "PING\n"
Server: "OK PONG\n"

Client: "UPPER xin chào\n"
Server: "OK XIN CHÀO\n"
```

### Với UDP (Datagram):
```
Client gửi datagram 1: "HELLO"
Server nhận datagram 1: "HELLO" ✓

Client gửi datagram 2: "WORLD"
Server nhận datagram 2: "WORLD" ✓

Mỗi datagram là hoàn chỉnh!
```

### So sánh:
```java
// TCP - Phải tự tách dòng:
PrintWriter out = new PrintWriter(..., true);
BufferedReader in = new BufferedReader(...);

out.println("PING");               // Thêm \n tự động
String response = in.readLine();   // Đọc cho tới \n

// UDP - Mỗi gói tự động:
byte[] data = "PING".getBytes();
DatagramPacket packet = new DatagramPacket(data, data.length, ...);
socket.send(packet);

DatagramPacket response = new DatagramPacket(buffer, buffer.length);
socket.receive(response);  // Một gói hoàn chỉnh
```

---

## 10. GIAO THỨC ỨNG DỤNG (Application Protocol)

### Giao thức là quy tắc giao tiếp:
Ví dụ giao thức dòng trong Lab:

```
Lệnh        Tham số         Phản hồi
────────────────────────────────────────────────
PING        (không)         OK PONG
TIME        (không)         OK [thời gian]
UPPER       <text>          OK [text in hoa]
QUIT        (không)         OK BYE
(khác)      (khác)          ERR UNKNOWN_COMMAND
```

### Ví dụ với Bài tập 3 (Máy tính):
```
Yêu cầu:        Phản hồi:
─────────────────────────────────────
CALC + 5 3      OK 8
CALC - 10 3     OK 7
CALC * 4 2      OK 8
CALC / 8 2      OK 4
CALC / 8 0      ERR DIVIDE_BY_ZERO
CALC % 5 2      ERR UNSUPPORTED_OPERATOR
CALC + a 2      ERR INVALID_NUMBER
CALC +          ERR INVALID_FORMAT
```

### Tại sao cần giao thức?
Vì không có giao thức → không biết:
- Server mong đợi format gì?
- Làm sao biết khi nào là kết thúc thông điệp?
- Làm sao xử lý lỗi?

---

## 11. SOCKET TIMEOUT

### Vấn đề UDP - Chờ vô hạn:
```java
socket.receive(packet);  // Chờ mãi nếu không có gói!
// Nếu server crash hoặc port sai → vô thời hạn
```

### Giải pháp - Thiết lập timeout:
```java
socket.setSoTimeout(3000);  // 3 giây timeout

try {
    socket.receive(packet);
} catch (SocketTimeoutException e) {
    System.out.println("Hết thời gian chờ");
}
```

### Lợi ích:
1. **Không chờ vô hạn**: Client không bị treo
2. **Phát hiện lỗi**: Biết khi server không phản hồi
3. **Retry**: Có thể gửi lại nếu timeout

### TCP có tự động timeout không?
- TCP có timeout mặc định (~15 phút)
- Nhưng vẫn nên kiểm tra lỗi:

```java
try {
    String response = in.readLine();
    if (response == null) {
        System.out.println("Server đóng kết nối");
    }
} catch (IOException e) {
    System.out.println("Lỗi: " + e.getMessage());
}
```

---

## 12. MULTICAST (Nâng cao)

### Multicast là gửi tới nhiều máy cùng lúc:
```
Sender A
    │
    ├─► Receiver B (đã join group)
    ├─► Receiver C (đã join group)
    ├─► Receiver D (đã join group)
    └─► Receiver E (chưa join - không nhận)
```

### Port multicast:
- Từ 224.0.0.0 đến 239.255.255.255
- Ví dụ: `239.255.0.1` (local quản trị)

### Lợi ích:
- Gửi một thông điệp → tất cả nhận
- Tiết kiệm bandwidth
- Phù hợp: live streaming, thông báo hệ thống

### Ví dụ (đơn giản):
```java
MulticastSocket socket = new MulticastSocket(5005);
InetAddress group = InetAddress.getByName("239.255.0.1");
socket.joinGroup(group);

// Nhận dữ liệu...
```

---

## 13. FIREWALL - TỰA LỬA

### Firewall là gì?
Firewall chặn kết nối mạng để bảo vệ máy.

### Vấn đề khi test trên 2 máy:
```
Client máy A ──────────────► Server máy B
                   ❌ Firewall chặn!
```

### Giải pháp:

**Windows Firewall**:
```bash
netsh advfirewall firewall add rule \
  name="Java Socket Lab" \
  dir=in \
  action=allow \
  program="C:\Program Files\Java\jdk-17\bin\java.exe" \
  protocol=tcp \
  localport=5000
```

**Linux (ufw)**:
```bash
sudo ufw allow 5000/tcp
sudo ufw allow 5001/udp
```

**Lưu ý bảo mật**:
- Chỉ mở port cần thiết
- Xóa rule sau khi hoàn tất thực hành
- Không tắt toàn bộ firewall!

---

## 14. DEBUGGING TIPS

### 1. In thông tin debug:
```java
System.out.println("✓ Server khởi động trên port " + PORT);
System.out.println("✓ Client kết nối từ: " + socket.getRemoteSocketAddress());
System.err.println("❌ Lỗi: " + e.getMessage());
```

### 2. Test localhost trước:
```
java.TcpCommandClient localhost 5000  // ✓ test cơ bản
java.TcpCommandClient 192.168.1.10 5000  // ✓ test 2 máy
```

### 3. Kiểm tra port:
```bash
# Windows:
netstat -ano | findstr :5000

# Linux:
lsof -i :5000
ss -ltn | grep 5000
```

### 4. Kiểm tra kết nối:
```bash
# Test TCP:
telnet localhost 5000

# Test UDP:
ncat -u localhost 5001
```

### 5. Xem charset:
```java
System.out.println("Charset: " + Charset.defaultCharset());
// In ra: UTF-8 hoặc Cp1252 (Windows)
```

---

## TỔNG KẾT

```
┌─────────────────────────────────────────────────────┐
│ Socket TCP/UDP - Khái niệm chính                    │
├─────────────────────────────────────────────────────┤
│ IP:Port        → Xác định ứng dụng trên mạng       │
│ TCP            → Tin cậy, kết nối, chậm           │
│ UDP            → Nhanh, không kết nối, không bảo đảm│
│ Stream         → TCP: luồng byte liên tục         │
│ Datagram       → UDP: mỗi gói độc lập             │
│ Protocol       → Quy tắc giao tiếp                │
│ Charset UTF-8  → Bảo toàn tiếng Việt             │
│ Thread Pool    → Phục vụ nhiều client an toàn     │
│ Timeout        → Không chờ vô hạn (UDP)          │
│ Try-with       → Tự động đóng resource            │
└─────────────────────────────────────────────────────┘
```

**Học hỏi thêm**: https://docs.oracle.com/javase/tutorial/networking/
