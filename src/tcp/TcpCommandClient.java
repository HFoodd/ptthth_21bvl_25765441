package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * VÍ DỤ 4.2 - TCP COMMAND CLIENT
 * 
 * Client kết nối tới server TCP trên cổng 5000 và cho phép người dùng nhập lệnh.
 * Mỗi lệnh được gửi tới server theo giao thức dòng (line protocol).
 * 
 * Các lệnh có thể gửi:
 * - PING: Server sẽ trả "OK PONG"
 * - TIME: Server sẽ trả thời gian hiện tại
 * - UPPER <text>: Server sẽ trả text chuyển thành chữ hoa
 * - QUIT: Kết thúc kết nối
 * 
 * Cách chạy:
 * java tcp.TcpCommandClient localhost 5000
 * java tcp.TcpCommandClient 192.168.1.10 5000 (kết nối tới server trên máy khác)
 */
public class TcpCommandClient {
    
    public static void main(String[] args) {
        // Lấy host từ tham số, mặc định là localhost nếu không có
        String host = args.length > 0 ? args[0] : "localhost";
        
        // Lấy port từ tham số, mặc định là 5000 nếu không có
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;

        try (Socket socket = new Socket(host, port);
             // Đọc từ bàn phím người dùng
             BufferedReader console = new BufferedReader(
                 new InputStreamReader(System.in, StandardCharsets.UTF_8));
             // Đọc phản hồi từ server
             BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
             // Gửi yêu cầu tới server
             PrintWriter out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            
            System.out.println("=== TCP COMMAND CLIENT ===");
            System.out.println("✓ Kết nối tới " + host + ":" + port);
            System.out.println();
            System.out.println("Các lệnh có thể sử dụng:");
            System.out.println("  PING              - Kiểm tra server còn hoạt động");
            System.out.println("  TIME              - Lấy thời gian từ server");
            System.out.println("  UPPER <text>     - Chuyển <text> thành chữ hoa");
            System.out.println("  QUIT              - Thoát khỏi chương trình");
            System.out.println();
            System.out.println("Nhập lệnh (gõ QUIT để thoát):");
            System.out.println();
            
            String request;
            // Đọc lệnh từ người dùng
            while ((request = console.readLine()) != null) {
                // Gửi lệnh tới server
                out.println(request);
                
                // Đọc phản hồi từ server
                String response = in.readLine();
                
                // Kiểm tra xem server có đóng kết nối không
                if (response == null) {
                    System.out.println("❌ Server đã đóng kết nối");
                    break;
                }
                
                // Hiển thị phản hồi từ server
                System.out.println("Server: " + response);
                
                // Nếu lệnh là QUIT, kết thúc client
                if (request.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
            
            System.out.println("\n✓ Kết nối đã đóng");
            
        } catch (NumberFormatException e) {
            System.err.println("❌ Lỗi: Port phải là một số nguyên");
            System.err.println("   Cách sử dụng: java tcp.TcpCommandClient [host] [port]");
        } catch (IOException e) {
            System.err.println("❌ Lỗi kết nối: " + e.getMessage());
            System.err.println("   Kiểm tra server có đang chạy và port có đúng không");
        }
    }
}
