package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * VÍ DỤ 4.4 - UDP ECHO CLIENT
 * 
 * Client UDP gửi một thông điệp tới server và chờ phản hồi.
 * 
 * Đặc điểm UDP:
 * - Không kết nối: client không cần thiết lập kết nối trước
 * - Cần thiết lập timeout để không chờ vô hạn nếu server không phản hồi
 * - Mỗi gửi/nhận là một datagram độc lập
 * 
 * Cách chạy:
 * java udp.UdpEchoClient localhost 5001 "xin chào UDP"
 * java udp.UdpEchoClient 192.168.1.10 5001 "hello UDP"
 * 
 * Nếu không truyền thông điệp:
 * java udp.UdpEchoClient
 * (sẽ dùng giá trị mặc định: localhost, 5001, "xin chào UDP")
 */
public class UdpEchoClient {
    private static final int BUFFER_SIZE = 4096;
    private static final int TIMEOUT_MS = 3000;  // Timeout 3 giây

    public static void main(String[] args) throws Exception {
        // Lấy hostname từ tham số, mặc định là localhost
        String host = args.length > 0 ? args[0] : "localhost";
        
        // Lấy port từ tham số, mặc định là 5001
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5001;
        
        // Lấy thông điệp từ tham số, mặc định là "xin chào UDP"
        String message = args.length > 2 ? args[2] : "xin chào UDP";

        System.out.println("=== UDP ECHO CLIENT ===");
        System.out.println("Máy chủ: " + host + ":" + port);
        System.out.println("Thông điệp: " + message);
        System.out.println("Timeout: " + TIMEOUT_MS + "ms");
        System.out.println();

        // Chuyển thông điệp sang mảng byte
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        
        // Lấy địa chỉ IP của server
        InetAddress server = InetAddress.getByName(host);

        // try-with-resources tự động đóng DatagramSocket
        try (DatagramSocket socket = new DatagramSocket()) {
            // Thiết lập timeout: nếu không nhận được dữ liệu trong 3 giây, ném SocketTimeoutException
            socket.setSoTimeout(TIMEOUT_MS);
            
            // Tạo datagram packet gửi
            DatagramPacket sendPacket = new DatagramPacket(
                data,
                data.length,
                server,
                port
            );
            
            // Gửi dữ liệu
            System.out.println("Đang gửi...");
            socket.send(sendPacket);
            
            // Chuẩn bị buffer nhận phản hồi
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            
            try {
                // Chờ nhận phản hồi từ server
                System.out.println("Chờ phản hồi (timeout sau " + TIMEOUT_MS + "ms)...");
                socket.receive(receivePacket);
                
                // Chuyển dữ liệu nhận từ byte sang String
                String response = new String(
                    receivePacket.getData(),
                    receivePacket.getOffset(),
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
                );
                
                System.out.println();
                System.out.println("✓ Nhận được phản hồi từ " 
                                 + receivePacket.getAddress().getHostAddress() 
                                 + ":" + receivePacket.getPort());
                System.out.println("Nội dung: " + response);
                
            } catch (SocketTimeoutException e) {
                System.out.println();
                System.err.println("❌ Lỗi: Hết thời gian chờ (" + TIMEOUT_MS + "ms)");
                System.err.println("   Kiểm tra:");
                System.err.println("   - Server có đang chạy không?");
                System.err.println("   - Host và port có đúng không?");
                System.err.println("   - Firewall có chặn UDP port 5001 không?");
            }
            
        } catch (NumberFormatException e) {
            System.err.println("❌ Lỗi: Port phải là một số nguyên");
        } catch (Exception e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
        }
    }
}
