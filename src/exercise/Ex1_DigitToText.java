package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * BÀI TẬP 1 - CHUYỂN ĐỔI CHỮ SỐ THÀNH CHỮ
 * 
 * YÊU CẦU:
 * - Client gửi một ký tự từ 0 đến 9
 * - Server trả về cách đọc tiếng Việt tương ứng
 * - Nếu dữ liệu không phải đúng một chữ số, server trả "ERR INVALID_DIGIT"
 * - Cho phép client gửi nhiều yêu cầu cho tới khi nhập QUIT
 * - Bắt buộc dùng UTF-8 và giao thức mỗi thông điệp một dòng
 * 
 * CA KIỂM THỬ:
 * - Nhập: "0" -> Phản hồi: "OK Không"
 * - Nhập: "9" -> Phản hồi: "OK Chín"
 * - Nhập: "" (chuỗi rỗng) -> Phản hồi: "ERR INVALID_DIGIT"
 * - Nhập: "10" -> Phản hồi: "ERR INVALID_DIGIT"
 * - Nhập: "a" -> Phản hồi: "ERR INVALID_DIGIT"
 * - Nhập: " 5 " (có khoảng trắng) -> Phản hồi: "OK Năm"
 * - Nhập: "QUIT" -> Phản hồi: "OK BYE"
 */
public class Ex1_DigitToText {

    // ============ SERVER ============
    
    static class Server {
        private static final int PORT = 5010;
        
        public static void main(String[] args) {
            try (ServerSocket server = new ServerSocket(PORT)) {
                System.out.println("=== BÀI TẬP 1 - SERVER (Chuyển đổi chữ số) ===");
                System.out.println("Server lắng nghe trên cổng " + PORT);
                
                while (true) {
                    try (Socket socket = server.accept()) {
                        System.out.println("✓ Client kết nối từ: " + socket.getRemoteSocketAddress());
                        serve(socket);
                        System.out.println("✓ Client ngắt kết nối\n");
                    } catch (IOException e) {
                        System.err.println("❌ Lỗi: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("❌ Lỗi server: " + e.getMessage());
            }
        }
        
        static void serve(Socket socket) throws IOException {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                
                String request;
                while ((request = in.readLine()) != null) {
                    String response = process(request);
                    out.println(response);
                    if (request.trim().equalsIgnoreCase("QUIT")) break;
                }
            }
        }
        
        static String process(String request) {
            String trimmed = request.trim();
            
            // Xử lý QUIT
            if (trimmed.equalsIgnoreCase("QUIT")) {
                return "OK BYE";
            }
            
            // Kiểm tra có phải chính xác một chữ số
            if (trimmed.length() != 1 || !Character.isDigit(trimmed.charAt(0))) {
                return "ERR INVALID_DIGIT";
            }
            
            // Chuyển đổi chữ số thành chữ
            String[] digitNames = {
                "Không", "Một", "Hai", "Ba", "Bốn",
                "Năm", "Sáu", "Bảy", "Tám", "Chín"
            };
            
            int digit = Integer.parseInt(trimmed);
            return "OK " + digitNames[digit];
        }
    }
    
    // ============ CLIENT ============
    
    static class Client {
        public static void main(String[] args) {
            String host = args.length > 0 ? args[0] : "localhost";
            int port = args.length > 1 ? Integer.parseInt(args[1]) : 5010;
            
            try (Socket socket = new Socket(host, port);
                 BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8));
                 BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                
                System.out.println("=== BÀI TẬP 1 - CLIENT ===");
                System.out.println("✓ Kết nối tới " + host + ":" + port);
                System.out.println("Nhập một chữ số (0-9) hoặc QUIT để thoát:\n");
                
                String request;
                while ((request = console.readLine()) != null) {
                    out.println(request);
                    String response = in.readLine();
                    if (response == null) break;
                    System.out.println("-> " + response);
                    if (request.trim().equalsIgnoreCase("QUIT")) break;
                }
                
            } catch (IOException e) {
                System.err.println("❌ Lỗi: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Cách sử dụng:");
            System.out.println("  java exercise.Ex1_DigitToText server");
            System.out.println("  java exercise.Ex1_DigitToText client [host] [port]");
            return;
        }
        
        if ("server".equalsIgnoreCase(args[0])) {
            Server.main(new String[]{});
        } else if ("client".equalsIgnoreCase(args[0])) {
            String[] clientArgs = new String[args.length - 1];
            System.arraycopy(args, 1, clientArgs, 0, clientArgs.length);
            Client.main(clientArgs);
        }
    }
}
