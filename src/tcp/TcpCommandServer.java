package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * VÍ DỤ 4.2 - TCP COMMAND SERVER (SERVER TUẦN TỰ)
 * 
 * Đây là server TCP đơn giản phục vụ một client tại một thời điểm. Server lắng nghe trên cổng 5000
 * và xử lý các lệnh giao thức dòng:
 * - PING: Server trả lại "OK PONG"
 * - TIME: Server trả lại "OK" kèm theo thời gian hiện tại
 * - UPPER <text>: Server trả lại "OK" kèm theo text chuyển thành chữ hoa
 * - QUIT: Server trả lại "OK BYE" và đóng kết nối
 * 
 * Mỗi yêu cầu và phản hồi là một dòng UTF-8, kết thúc bằng ký tự xuống dòng (\n).
 * 
 * Cách chạy:
 * java tcp.TcpCommandServer
 * 
 * Sau đó chạy client trong terminal khác:
 * java tcp.TcpCommandClient localhost 5000
 */
public class TcpCommandServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("=== TCP COMMAND SERVER ===");
            System.out.println("Server đang lắng nghe trên cổng " + PORT);
            System.out.println("Đợi kết nối từ client...");
            System.out.println();

            // Server chạy vô hạn để chấp nhận kết nối
            while (true) {
                try (Socket socket = server.accept()) {
                    // Hiển thị thông tin client
                    System.out.println("✓ Client kết nối từ: " + socket.getRemoteSocketAddress());
                    
                    // Phục vụ client
                    serve(socket);
                    
                    System.out.println("✓ Client ngắt kết nối");
                    System.out.println();
                    
                } catch (IOException e) {
                    System.err.println("❌ Lỗi phiên client: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Lỗi khi khởi động server: " + e.getMessage());
        }
    }

    /**
     * Phục vụ một client đơn lẻ
     * Sử dụng try-with-resources để tự động đóng các stream
     * 
     * @param socket Kết nối với client
     * @throws IOException Nếu có lỗi I/O
     */
    static void serve(Socket socket) throws IOException {
        // try-with-resources tự động đóng BufferedReader và PrintWriter
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            
            String request;
            // Đọc từng dòng yêu cầu từ client cho tới khi kết nối đóng
            while ((request = in.readLine()) != null) {
                // Xử lý yêu cầu và lấy phản hồi
                String response = process(request);
                
                // Gửi phản hồi cho client
                out.println(response);
                
                // Nếu client gửi QUIT, kết thúc phiên
                if (request.equalsIgnoreCase("QUIT")) break;
            }
        }
    }

    /**
     * Xử lý yêu cầu và tạo phản hồi theo giao thức
     * 
     * @param request Chuỗi yêu cầu từ client
     * @return Chuỗi phản hồi để gửi lại client
     */
    static String process(String request) {
        String trimmed = request.trim();
        
        // Lệnh PING: kiểm tra server đang hoạt động
        if (trimmed.equalsIgnoreCase("PING")) {
            return "OK PONG";
        }
        
        // Lệnh TIME: trả về thời gian hiện tại
        if (trimmed.equalsIgnoreCase("TIME")) {
            return "OK " + LocalDateTime.now();
        }
        
        // Lệnh QUIT: kết thúc phiên
        if (trimmed.equalsIgnoreCase("QUIT")) {
            return "OK BYE";
        }
        
        // Lệnh UPPER: chuyển đổi văn bản thành chữ hoa
        // Kiểm tra xem yêu cầu có bắt đầu bằng "UPPER " không (không phân biệt hoa thường)
        if (trimmed.regionMatches(true, 0, "UPPER ", 0, 6)) {
            String text = trimmed.substring(6);
            return "OK " + text.toUpperCase(Locale.ROOT);
        }
        
        // Lệnh không hợp lệ
        return "ERR UNKNOWN_COMMAND";
    }
}
