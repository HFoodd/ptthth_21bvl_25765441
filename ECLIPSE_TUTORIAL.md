# 🎯 HƯỚNG DẪN CHẠY TRÊN ECLIPSE - CHI TIẾT TỪNG BƯỚC

## ⚡ NHANH CHÓNG (5 PHÚT)

### **Bước 1: Import dự án vào Eclipse**
```
File → Import → General → Existing Projects into Workspace
→ Select root directory: d:\IUH\Tich Hop\lab04-socket
→ Finish
```

### **Bước 2: Build Project**
```
Project → Build Project (hoặc Ctrl+B)
```

### **Bước 3: Chạy bất kỳ lớp nào**
```
Right-click file .java → Run As → Java Application
```

---

## 📚 HƯỚNG DẪN CHI TIẾT

### **VÍ DỤ 4.1 - HostInspector (Khảo sát IP)**

**Bước 1**: Mở file `src/network/HostInspector.java`

**Bước 2**: Right-click → **Run As** → **Run Configurations...**

**Bước 3**: Tab **Arguments** (phía dưới cửa sổ)

**Bước 4**: Trong **Program arguments** gõ:
```
localhost
```

**Bước 5**: Click **Run**

**Kết quả Console**:
```
=== KHẢO SÁT ĐỊA CHỈ MẠNG ===
Host: localhost
Số lượng địa chỉ: 2

Địa chỉ #1:
  - IP: 127.0.0.1
  - Loại: IPv4
  - Tên Canonical: 127.0.0.1
  - Loopback: true
  ...

Địa chỉ #2:
  - IP: 0:0:0:0:0:0:0:1
  - Loại: IPv6
  ...
```

✅ **Thành công!**

---

### **VÍ DỤ 4.2 - TCP Server Tuần Tự**

#### **Step A: Chạy TcpCommandServer (Server)**

1. Mở file `src/tcp/TcpCommandServer.java`
2. Right-click → **Run As** → **Java Application**

**Console hiển thị**:
```
=== TCP COMMAND SERVER ===
Server đang lắng nghe trên cổng 5000
Đợi kết nối từ client...
```

✅ **Server đang chạy** - Đừng đóng console này!

#### **Step B: Chạy TcpCommandClient (Client)**

1. Mở file `src/tcp/TcpCommandClient.java`
2. Right-click → **Run As** → **Run Configurations...**
3. **Arguments** tab → **Program arguments** gõ:
   ```
   localhost 5000
   ```
4. Click **Run** (hoặc **Apply** rồi **Run**)

**Console Client hiển thị**:
```
=== TCP COMMAND CLIENT ===
✓ Kết nối tới localhost:5000

Các lệnh có thể sử dụng:
  PING              - Kiểm tra server còn hoạt động
  TIME              - Lấy thời gian từ server
  UPPER <text>     - Chuyển <text> thành chữ hoa
  QUIT              - Thoát khỏi chương trình

Nhập lệnh (gõ QUIT để thoát):

>
```

#### **Step C: Gõ lệnh kiểm thử**

Trong **Client Console**, gõ lần lượt:

**Test 1: PING**
```
> PING
```
Kết quả:
```
Server: OK PONG
>
```

**Test 2: UPPER**
```
> UPPER xin chào
```
Kết quả:
```
Server: OK XIN CHÀO
>
```

**Test 3: TIME**
```
> TIME
```
Kết quả:
```
Server: OK 2026-09-27T14:30:45.123456789
>
```

**Test 4: QUIT**
```
> QUIT
```
Kết quả:
```
Server: OK BYE

✓ Kết nối đã đóng
```

**Server Console** (lúc này hiển thị):
```
✓ Client kết nối từ: /127.0.0.1:54321
✓ Client ngắt kết nối
```

✅ **Hoàn thành Ví dụ 4.2!**

---

### **VÍ DỤ 4.3 - TCP Multi-Client Server**

#### **Step A: Chạy MultiClientTcpServer**

1. Mở `src/tcp/MultiClientTcpServer.java`
2. Right-click → **Run As** → **Java Application**

**Console**:
```
=== MULTI-CLIENT TCP SERVER ===
Server đang lắng nghe trên cổng 5000
Có thể phục vụ tối đa 20 client đồng thời
```

#### **Step B: Chạy 3 Clients (cùng lúc)**

**Client 1**:
1. Mở `src/tcp/TcpCommandClient.java`
2. **Run Configurations** → **Arguments**: `localhost 5000`
3. Click **Run**

**Client 2** (tạo config mới):
1. Mở `src/tcp/TcpCommandClient.java` (hoặc tùy chọn)
2. **Run Configurations** → **New** (để tạo config mới, đặt tên "Client2")
3. **Arguments**: `localhost 5000`
4. Click **Run**

**Client 3** (tạo config mới):
1. Tương tự Client 2
2. Đặt tên "Client3"
3. Click **Run**

#### **Step C: Kiểm thử**

Bây giờ bạn có **3 client** chạy cùng lúc (mỗi cái một Console tab)

**Client 1**:
```
> PING
Server: OK PONG

> UPPER hello
Server: OK HELLO
```

**Client 2** (đồng thời):
```
> TIME
Server: OK 2026-09-27T14:30:45.123456789

> UPPER world
Server: OK WORLD
```

**Client 3** (đồng thời):
```
> PING
Server: OK PONG

> QUIT
Server: OK BYE
```

**Server Console**:
```
✓ Client kết nối từ: /127.0.0.1:54321
✓ Client kết nối từ: /127.0.0.1:54322
✓ Client kết nối từ: /127.0.0.1:54323
✓ Client ngắt kết nối: /127.0.0.1:54323
✓ Client ngắt kết nối: /127.0.0.1:54321
✓ Client ngắt kết nối: /127.0.0.1:54322
```

✅ **Hoàn thành Ví dụ 4.3!**

---

### **VÍ DỤ 4.4 - UDP Echo**

#### **Step A: Chạy UdpEchoServer**

1. Mở `src/udp/UdpEchoServer.java`
2. Right-click → **Run As** → **Java Application**

**Console**:
```
=== UDP ECHO SERVER ===
Server UDP đang lắng nghe trên cổng 5001
Kích thước buffer: 4096 byte
```

#### **Step B: Chạy UdpEchoClient (Test 1)**

1. Mở `src/udp/UdpEchoClient.java`
2. **Run Configurations** → **Arguments**:
   ```
   localhost 5001 "xin chào UDP"
   ```
3. Click **Run**

**Client Console**:
```
=== UDP ECHO CLIENT ===
Máy chủ: localhost:5001
Thông điệp: xin chào UDP
Timeout: 3000ms

Đang gửi...
Chờ phản hồi (timeout sau 3000ms)...

✓ Nhận được phản hồi từ 127.0.0.1:5001
Nội dung: ACK XIN CHÀO UDP
```

**Server Console** (đồng thời):
```
Nhận từ 127.0.0.1:54321 > xin chào UDP
Gửi phản hồi: ACK XIN CHÀO UDP
```

#### **Step C: Test 2 - Timeout (Server không chạy)**

1. **Dừng server** (click stop button trong Console)
2. **Run UdpEchoClient** lại với Arguments:
   ```
   localhost 5001 "test"
   ```

**Client Console** (sau 3 giây):
```
=== UDP ECHO CLIENT ===
Máy chủ: localhost:5001
Thông điệp: test
Timeout: 3000ms

Đang gửi...
Chờ phản hồi (timeout sau 3000ms)...

❌ Lỗi: Hết thời gian chờ (3000ms)
   Kiểm tra:
   - Server có đang chạy không?
   - Host và port có đúng không?
   - Firewall có chặn UDP port 5001 không?
```

✅ **Hoàn thành Ví dụ 4.4!**

---

## 💻 CÁC BÀI TẬP

### **BÀI TẬP 1 - Chữ Số Thành Chữ**

#### **Step A: Chạy Server**

1. Mở `src/exercise/Ex1_DigitToText.java`
2. **Run Configurations** → **Arguments**:
   ```
   server
   ```
3. Click **Run**

**Console Server**:
```
=== BÀI TẬP 1 - SERVER (Chuyển đổi chữ số) ===
Server lắng nghe trên cổng 5010
✓ TCP Client kết nối từ: /127.0.0.1:xxxxx
```

#### **Step B: Chạy Client**

1. **Run Configurations** → **Arguments**:
   ```
   client localhost 5010
   ```
2. Click **Run**

**Console Client**:
```
=== BÀI TẬP 1 - CLIENT ===
✓ Kết nối tới localhost:5010
Nhập một chữ số (0-9) hoặc QUIT để thoát:

>
```

#### **Step C: Test**

```
> 0
-> OK Không

> 5
-> OK Năm

> 9
-> OK Chín

> 10
-> ERR INVALID_DIGIT

> a
-> ERR INVALID_DIGIT

> (rỗng - chỉ gõ Enter)
-> ERR INVALID_DIGIT

>  5  (có khoảng trắng)
-> OK Năm

> QUIT
-> OK BYE
```

✅ **Bài tập 1 done!**

---

### **BÀI TẬP 2 - Dịch Vụ Ngày Giờ TCP/UDP**

#### **Part A: TCP**

**Step A1: Run TCP Server**
```
Arguments: tcp-server
```

**Console**:
```
=== BÀI TẬP 2 - TCP SERVER (Dịch vụ ngày giờ) ===
TCP Server lắng nghe trên cổng 5011
```

**Step A2: Run TCP Client**
```
Arguments: tcp-client localhost 5011
```

**Console**:
```
=== BÀI TẬP 2 - TCP CLIENT ===
✓ Kết nối tới localhost:5011

Các lệnh: DATE, TIME, DATETIME, QUIT

>
```

**Step A3: Test TCP**
```
> DATE
-> OK 27/09/2026

> TIME
-> OK 14:30:45

> DATETIME
-> OK 27/09/2026 14:30:45

> QUIT
-> OK BYE
```

#### **Part B: UDP**

**Step B1: Run UDP Server**
```
Arguments: udp-server
```

**Console**:
```
=== BÀI TẬP 2 - UDP SERVER (Dịch vụ ngày giờ) ===
UDP Server lắng nghe trên cổng 5012
```

**Step B2: Test UDP Client - DATE**
```
Arguments: udp-client localhost 5012 DATE
```

**Console**:
```
=== BÀI TẬP 2 - UDP CLIENT ===
Gửi: DATE
Nhận: OK 27/09/2026
```

**Step B3: Test UDP Client - TIME**
```
Arguments: udp-client localhost 5012 TIME
```

**Console**:
```
Gửi: TIME
Nhận: OK 14:30:45
```

**Step B4: Test UDP Client - DATETIME**
```
Arguments: udp-client localhost 5012 DATETIME
```

**Console**:
```
Gửi: DATETIME
Nhận: OK 27/09/2026 14:30:45
```

✅ **Bài tập 2 done!**

---

### **BÀI TẬP 3 - Máy Tính Từ Xa**

#### **Step A: Run Server**
```
Arguments: server
```

**Console**:
```
=== BÀI TẬP 3 - MÁY TÍNH TỪXƯƠNG (Calculator Server) ===
Server lắng nghe trên cổng 5013
Các phép toán hỗ trợ: +, -, *, /
```

#### **Step B: Run Client**
```
Arguments: client localhost 5013
```

**Console**:
```
=== BÀI TẬP 3 - CALCULATOR CLIENT ===
✓ Kết nối tới localhost:5013

Các lệnh:
  CALC + số1 số2      - Cộng
  CALC - số1 số2      - Trừ
  CALC * số1 số2      - Nhân
  CALC / số1 số2      - Chia
  QUIT                - Thoát

>
```

#### **Step C: Test**

```
> CALC + 100 200
-> OK 300

> CALC - 50 10
-> OK 40

> CALC * 5 6
-> OK 30

> CALC / 10 2
-> OK 5

> CALC / 10 0
-> ERR DIVIDE_BY_ZERO

> CALC % 10 3
-> ERR UNSUPPORTED_OPERATOR

> CALC + a 2
-> ERR INVALID_NUMBER

> CALC +
-> ERR INVALID_FORMAT

> QUIT
-> OK BYE
```

✅ **Bài tập 3 done!**

---

## 📸 CHEAT SHEET - Eclipse Console

### Các Console Tab

Khi chạy nhiều chương trình, Eclipse sẽ mở **nhiều Console tabs**:

```
[Console 1 - Server]  [Console 2 - Client 1]  [Console 3 - Client 2]
         |                    |                         |
   Server output         Client 1 output          Client 2 output
```

**Cách chuyển tab**: Click vào tab tên console hoặc dùng Window → Show View → Console

### Stop/Rerun

- **Stop**: Click nút đỏ (|■) trong Console
- **Rerun**: Click nút xanh (▶) hoặc Ctrl+F11

### Clear Console

- Nút chốt (🧤) để xóa console

---

## ✅ FULL CHECKLIST

- ☐ Import dự án vào Eclipse
- ☐ Build Project (Ctrl+B)
- ☐ Ví dụ 4.1: HostInspector chạy với `localhost` → ✓ IP, Loopback
- ☐ Ví dụ 4.2: TCP Server + Client → ✓ PING, UPPER, TIME, QUIT
- ☐ Ví dụ 4.3: Multi-client (3 clients) → ✓ Tất cả đồng thời
- ☐ Ví dụ 4.4: UDP Echo → ✓ Nhận được ACK, ✓ Timeout test
- ☐ Bài tập 1: 0-9, lỗi, QUIT → ✓ Tất cả pass
- ☐ Bài tập 2 TCP: DATE, TIME, DATETIME, QUIT → ✓ Pass
- ☐ Bài tập 2 UDP: DATE, TIME, DATETIME → ✓ Pass
- ☐ Bài tập 3: +, -, *, /, errors → ✓ Tất cả pass
- ☐ Chụp screenshots tất cả Console

---

## 📝 GHI CHÚ

### Nếu Console không hiển thị
```
Window → Show View → Console (hoặc Alt+Shift+Q → C)
```

### Nếu Program arguments không thấy
```
Run Configurations → (scroll xuống) → Arguments tab
```

### Nếu chạy mà không có arguments
```
Right-click → Run Configurations (tùy chọn mới)
→ Main tab → Project: lab04-socket, Main class: [chọn]
→ Arguments tab → Program arguments: [gõ tham số]
→ Apply → Run
```

---

**Bây giờ mọi thứ đã sẵn sàng! Chạy từng ví dụ/bài tập theo hướng dẫn trên. 🚀**

**Chúc bạn thành công! 🎉**
