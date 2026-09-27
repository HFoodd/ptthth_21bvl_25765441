# 🔗 Hướng dẫn Push code lên GitHub

## 1️⃣ Tạo Repository trên GitHub

### Bước 1: Đăng nhập GitHub
- Vào https://github.com
- Đăng nhập với tài khoản của bạn

### Bước 2: Tạo Repository mới
1. Click **"+"** (góc trên cùng bên phải)
2. Chọn **"New repository"**
3. Điền thông tin:
   - **Repository name**: `lab04-socket`
   - **Description**: `Lab 04 - Lập trình Socket TCP và UDP trong Java - IUH v2026`
   - **Visibility**: Chọn **Public** (để giảng viên xem được)
   - **Initialize this repository**: Bỏ chọn (vì chúng ta push code sẵn có)
4. Click **"Create repository"**

### Bước 3: Copy URL
Sau khi tạo, GitHub sẽ hiển thị URL:
```
https://github.com/YOUR_USERNAME/lab04-socket.git
```

Lưu URL này lại!

---

## 2️⃣ Setup Git trên máy

### Windows:
1. Tải Git: https://git-scm.com/download/win
2. Cài đặt (chọn default options)
3. Mở PowerShell hoặc cmd

### Linux/Mac:
```bash
# Ubuntu/Debian
sudo apt-get install git

# macOS
brew install git

# Kiểm tra
git --version
```

### Cấu hình Git (lần đầu)
```bash
git config --global user.name "Họ tên của bạn"
git config --global user.email "email@example.com"
```

---

## 3️⃣ Push code lên GitHub

### Mở Terminal/PowerShell

**Windows PowerShell**:
```bash
cd "d:\IUH\Tich Hop\lab04-socket"
```

**Linux/Mac Terminal**:
```bash
cd ~/path/to/lab04-socket
```

### Khởi tạo Git (nếu chưa có)
```bash
git init
```

### Thêm remote (liên kết với GitHub)
Thay `YOUR_USERNAME` bằng username GitHub của bạn:
```bash
git remote add origin https://github.com/YOUR_USERNAME/lab04-socket.git
```

### Thêm tất cả file
```bash
git add .
```

### Kiểm tra file sẽ được commit
```bash
git status
```

Nên thấy tất cả file được "added for commit"

### Commit lần đầu
```bash
git commit -m "Lab 04 - Socket TCP/UDP - Initial commit"
```

### Push lên GitHub
```bash
git branch -M main
git push -u origin main
```

### Nhập thông tin xác thực

**Cách 1: Dùng Personal Access Token (khuyến khích)**
1. Vào GitHub → Settings → Developer settings → Personal access tokens
2. Click "Generate new token"
3. Chọn scopes: `repo`, `write:packages`
4. Nhấp generate
5. Copy token
6. Paste vào PowerShell khi hỏi password

**Cách 2: Dùng SSH key (nâng cao)**
Xem: https://docs.github.com/en/authentication/connecting-to-github-with-ssh

---

## 4️⃣ Xác minh trên GitHub

### Kiểm tra
1. Vào https://github.com/YOUR_USERNAME/lab04-socket
2. Xem có file code, tài liệu không
3. Nên thấy:
   - src/ folder với 9 .java files
   - bin/ folder (có thể trống)
   - evidence/ folder
   - README.md, RUN_COMMANDS.txt, v.v.

### Nếu thành công
Bạn sẽ thấy "main" branch và tất cả commit

---

## 5️⃣ Cập nhật code sau này

### Thực hiện thay đổi code

Sau khi sửa file:

```bash
# Kiểm tra thay đổi
git status

# Thêm file đã thay đổi
git add src/tcp/TcpCommandServer.java  # hoặc
git add .                               # Thêm tất cả

# Commit
git commit -m "Fix: [Mô tả thay đổi]"

# Push
git push
```

---

## 6️⃣ Thêm .gitignore

File `.gitignore` đã có sẵn, nó sẽ loại trừ:
- `bin/` (compiled files)
- `*.class` (compiled Java)
- `.settings/` (IDE config)

Nên trước khi commit, xóa `bin/`:
```bash
rm -r bin/        # Linux/Mac
rmdir /S bin      # Windows
```

---

## ⚠️ Lỗi thường gặp

### "fatal: not a git repository"
```bash
git init  # Cần chạy lần đầu
```

### "fatal: 'origin' does not appear to be a 'git' repository"
```bash
git remote add origin https://github.com/YOUR_USERNAME/lab04-socket.git
```

### "Everything up-to-date"
Có nghĩa tất cả file đã push, không có thay đổi mới

### "permission denied"
```
Kiểm tra token hoặc SSH key
```

### Commit lỗi, muốn undo
```bash
git log                    # Xem commit history
git revert COMMIT_HASH     # Undo commit
git push
```

---

## 📝 Ví dụ hoàn chỉnh

### Setup lần đầu (5 phút)

```bash
# 1. Vào thư mục project
cd "d:\IUH\Tich Hop\lab04-socket"

# 2. Init git
git init

# 3. Thêm remote (thay YOUR_USERNAME)
git remote add origin https://github.com/YOUR_USERNAME/lab04-socket.git

# 4. Thêm tất cả file
git add .

# 5. Commit
git commit -m "Lab 04 - Socket TCP/UDP - Initial commit"

# 6. Push
git branch -M main
git push -u origin main

# ← Lúc này, nhập Personal Access Token khi hỏi password
```

### Lần sau (2 phút)

```bash
# 1. Sửa file
# (Edit code trong VSCode)

# 2. Stage file
git add src/tcp/TcpCommandServer.java

# 3. Commit
git commit -m "Update: Sửa lỗi trong xử lý QUIT"

# 4. Push
git push
```

---

## ✅ Checklist

- [ ] Tài khoản GitHub tạo
- [ ] Repository `lab04-socket` tạo (Public)
- [ ] Git cài đặt trên máy
- [ ] Git config user name/email
- [ ] `git init` chạy trong folder project
- [ ] `git remote add origin` chạy
- [ ] `git add .` chạy
- [ ] `git commit` chạy
- [ ] `git push` chạy thành công
- [ ] Trên GitHub thấy tất cả file
- [ ] Báo cáo Word có link GitHub

---

## 🔗 Link GitHub trong báo cáo

Khi viết báo cáo Word, thêm link này:

```
GitHub Repository: https://github.com/YOUR_USERNAME/lab04-socket
```

Giảng viên có thể click vào xem code của bạn

---

## 📚 Tài liệu GitHub

- [GitHub Docs](https://docs.github.com)
- [Git Tutorial](https://git-scm.com/doc)
- [GitHub First Steps](https://github.com/skills)

---

**Xong! Bây giờ bạn có thể push code lên GitHub! 🚀**
