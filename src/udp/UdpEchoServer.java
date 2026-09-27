package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * VÍ DỤ 4.4 - UDP ECHO SERVER
 * 
 * Server UDP nhận datagram từ client, xử lý thông điệp, và gửi lại phản hồi.
 * 
 * Các khác biệt giữa UDP và TCP:
 * - UDP không có kết nối: server không cần ServerSocket, chỉ cần DatagramSocket
 * - Mỗi gói dữ liệu (datagram) độc lập, không đảm bảo thứ tự hoặc đến nơi
 * - Phải xác định rõ kích thước buffer và lấy độ dài thực tế của dữ liệu
 * - Mỗi gói có địa chỉ và cổng của người gửi để gửi phản hồi lại
 * 
 * Giao thức:
 * - Client gửi: một chuỗi UTF-8 (vd: "xin chào UDP")
 * - Server trả: "ACK" + chuỗi chuyển thành chữ hoa (vd: "ACK XIN CHÀO UDP")
 * 
 * Cách chạy:
 * java udp.UdpEchoServer
 * 
 * Sau đó trong terminal khác:
 * java udp.UdpEchoClient localhost 5001 "xin chào UDP"
 */
public class UdpEchoServer {
    private static final int PORT = 5001;
    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        // Buffer lưu trữ dữ liệu nhận từ client
        byte[] buffer = new byte[BUFFER_SIZE];

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("=== UDP ECHO SERVER ===");
            System.out.println("Server UDP đang lắng nghe trên cổng " + PORT);
            System.out.println("Kích thước buffer: " + BUFFER_SIZE + " byte");
            System.out.println();

            // Server chạy vô hạn
            while (true) {
                // Tạo datagram packet để lưu dữ liệu nhận
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                
                // Chờ nhận dữ liệu từ client (chặn cho tới khi có dữ liệu)
                socket.receive(request);
                
                // Chuyển dữ liệu nhận từ byte sang String
                // QUAN TRỌNG: sử dụng request.getOffset() và request.getLength()
                // để lấy đúng số byte thực tế, không phải cả buffer
                String message = new String(
                    request.getData(),
                    request.getOffset(),
                    request.getLength(),
                    StandardCharsets.UTF_8
                );
                
                System.out.println("Nhận từ " + request.getAddress().getHostAddress() 
                                 + ":" + request.getPort() 
                                 + " > " + message);
                
                // Tạo phản hồi: "ACK" + thông điệp chuyển thành chữ hoa
                String responseText = "ACK " + message.toUpperCase(Locale.ROOT);
                byte[] responseData = responseText.getBytes(StandardCharsets.UTF_8);
                
                // Tạo datagram packet gửi lại cho client
                // Cần lấy địa chỉ và cổng của client từ request
                DatagramPacket response = new DatagramPacket(
                    responseData,
                    responseData.length,
                    request.getAddress(),      // Địa chỉ IP của client
                    request.getPort()          // Cổng của client
                );
                
                // Gửi phản hồi
                socket.send(response);
                
                System.out.println("Gửi phản hồi: " + responseText);
                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("❌ Lỗi UDP server: " + e.getMessage());
        }
    }
}
