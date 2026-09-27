package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * VÍ DỤ 4.3 - TCP SERVER PHỤC VỤ NHIỀU CLIENT ĐỒNG THỜI
 * 
 * Khác với TcpCommandServer phục vụ tuần tự (một client tại một thời điểm),
 * server này sử dụng Thread Pool (ExecutorService) để xử lý nhiều client cùng lúc.
 * 
 * Kỹ thuật:
 * - ExecutorService.newFixedThreadPool(20): Tạo một pool với tối đa 20 luồng
 * - pool.submit(Runnable): Giao task xử lý client cho một luồng trong pool
 * - Lambda expression: Viết code xử lý ngắn gọn hơn
 * 
 * Lợi ích:
 * - Nhiều client có thể kết nối và gửi lệnh cùng một lúc
 * - Không tạo luồng không giới hạn (tránh Out of Memory)
 * - Luồng được tái sử dụng, hiệu quả hơn
 * 
 * Cách chạy:
 * java tcp.MultiClientTcpServer
 * 
 * Sau đó trong các terminal khác chạy:
 * java tcp.TcpCommandClient localhost 5000
 */
public class MultiClientTcpServer {
    private static final int PORT = 5000;
    private static final int MAX_CLIENTS = 20;  // Số luồng tối đa trong pool

    public static void main(String[] args) {
        // Tạo thread pool có tối đa 20 luồng
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("=== MULTI-CLIENT TCP SERVER ===");
            System.out.println("Server đang lắng nghe trên cổng " + PORT);
            System.out.println("Có thể phục vụ tối đa " + MAX_CLIENTS + " client đồng thời");
            System.out.println();

            // Server chạy vô hạn để chấp nhận kết nối
            while (true) {
                // Chấp nhận kết nối từ client (chặn cho tới khi có client)
                Socket socket = server.accept();
                
                // Giao task xử lý client cho thread pool
                // Lambda expression thay thế Anonymous Class
                pool.submit(() -> {
                    // Lấy địa chỉ của client
                    String clientAddress = String.valueOf(socket.getRemoteSocketAddress());
                    System.out.println("✓ Client kết nối từ: " + clientAddress);
                    
                    // Xử lý client bằng phương thức serve từ TcpCommandServer
                    try (socket) {  // try-with-resources tự động đóng socket
                        TcpCommandServer.serve(socket);
                    } catch (IOException e) {
                        System.err.println("❌ Lỗi xử lý client " + clientAddress 
                                         + ": " + e.getMessage());
                    } finally {
                        System.out.println("✓ Client ngắt kết nối: " + clientAddress);
                    }
                });
            }

        } catch (IOException e) {
            System.err.println("❌ Lỗi khi khởi động server: " + e.getMessage());
        } finally {
            // Dừng thread pool khi server tắt
            pool.shutdown();
            System.out.println("Thread pool đã dừng");
        }
    }
}
