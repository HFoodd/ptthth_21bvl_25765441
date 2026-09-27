package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * BÀI TẬP 2 - DỊCH VỤ NGÀY GIỜ TRÊN TCP VÀ UDP
 * 
 * YÊU CẦU:
 * - Dùng DateTimeFormatter với khuôn dạng dd/MM/yyyy và HH:mm:ss
 * - Server TCP hỗ trợ: DATE, TIME, DATETIME, QUIT
 * - Server UDP hỗ trợ: DATE, TIME, DATETIME (không cần QUIT vì không có phiên)
 * - Viết so sánh hành vi khi server dừng giữa lúc client hoạt động
 * 
 * TCP PORT: 5011
 * UDP PORT: 5012
 */
public class Ex2_DateTimeService {
    
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    // ============ TCP SERVER ============
    
    static class TcpServer {
        private static final int PORT = 5011;
        
        public static void main(String[] args) {
            try (ServerSocket server = new ServerSocket(PORT)) {
                System.out.println("=== BÀI TẬP 2 - TCP SERVER (Dịch vụ ngày giờ) ===");
                System.out.println("TCP Server lắng nghe trên cổng " + PORT);
                
                while (true) {
                    try (Socket socket = server.accept()) {
                        System.out.println("✓ TCP Client kết nối từ: " + socket.getRemoteSocketAddress());
                        serveTcp(socket);
                        System.out.println("✓ TCP Client ngắt kết nối\n");
                    } catch (IOException e) {
                        System.err.println("❌ Lỗi: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("❌ Lỗi TCP server: " + e.getMessage());
            }
        }
        
        static void serveTcp(Socket socket) throws IOException {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                
                String request;
                while ((request = in.readLine()) != null) {
                    String response = processTcp(request);
                    out.println(response);
                    System.out.println("  Yêu cầu: " + request + " -> " + response);
                    if (request.trim().equalsIgnoreCase("QUIT")) break;
                }
            }
        }
        
        static String processTcp(String request) {
            String trimmed = request.trim().toUpperCase();
            
            if (trimmed.equals("DATE")) {
                return "OK " + LocalDate.now().format(DATE_FORMATTER);
            } else if (trimmed.equals("TIME")) {
                return "OK " + LocalTime.now().format(TIME_FORMATTER);
            } else if (trimmed.equals("DATETIME")) {
                LocalDateTime now = LocalDateTime.now();
                String date = now.format(DATE_FORMATTER);
                String time = now.format(TIME_FORMATTER);
                return "OK " + date + " " + time;
            } else if (trimmed.equals("QUIT")) {
                return "OK BYE";
            } else {
                return "ERR UNKNOWN_COMMAND";
            }
        }
    }
    
    // ============ UDP SERVER ============
    
    static class UdpServer {
        private static final int PORT = 5012;
        
        public static void main(String[] args) {
            byte[] buffer = new byte[1024];
            
            try (DatagramSocket socket = new DatagramSocket(PORT)) {
                System.out.println("=== BÀI TẬP 2 - UDP SERVER (Dịch vụ ngày giờ) ===");
                System.out.println("UDP Server lắng nghe trên cổng " + PORT);
                
                while (true) {
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    socket.receive(request);
                    
                    String message = new String(request.getData(), 
                                              request.getOffset(), 
                                              request.getLength(),
                                              StandardCharsets.UTF_8);
                    
                    System.out.println("✓ UDP Client: " + request.getAddress().getHostAddress() 
                                     + ":" + request.getPort() + " -> " + message);
                    
                    String responseText = processUdp(message);
                    byte[] responseData = responseText.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket response = new DatagramPacket(
                        responseData, responseData.length,
                        request.getAddress(), request.getPort()
                    );
                    
                    socket.send(response);
                    System.out.println("  Phản hồi: " + responseText + "\n");
                }
            } catch (IOException e) {
                System.err.println("❌ Lỗi UDP server: " + e.getMessage());
            }
        }
        
        static String processUdp(String request) {
            String trimmed = request.trim().toUpperCase();
            
            if (trimmed.equals("DATE")) {
                return "OK " + LocalDate.now().format(DATE_FORMATTER);
            } else if (trimmed.equals("TIME")) {
                return "OK " + LocalTime.now().format(TIME_FORMATTER);
            } else if (trimmed.equals("DATETIME")) {
                LocalDateTime now = LocalDateTime.now();
                String date = now.format(DATE_FORMATTER);
                String time = now.format(TIME_FORMATTER);
                return "OK " + date + " " + time;
            } else {
                return "ERR UNKNOWN_COMMAND";
            }
        }
    }
    
    // ============ TCP CLIENT ============
    
    static class TcpClient {
        public static void main(String[] args) {
            String host = args.length > 0 ? args[0] : "localhost";
            int port = args.length > 1 ? Integer.parseInt(args[1]) : 5011;
            
            try (Socket socket = new Socket(host, port);
                 BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8));
                 BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                
                System.out.println("=== BÀI TẬP 2 - TCP CLIENT ===");
                System.out.println("✓ Kết nối tới " + host + ":" + port);
                System.out.println("Các lệnh: DATE, TIME, DATETIME, QUIT\n");
                
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
    
    // ============ UDP CLIENT ============
    
    static class UdpClient {
        public static void main(String[] args) throws Exception {
            String host = args.length > 0 ? args[0] : "localhost";
            int port = args.length > 1 ? Integer.parseInt(args[1]) : 5012;
            String command = args.length > 2 ? args[2] : "DATE";
            
            byte[] data = command.getBytes(StandardCharsets.UTF_8);
            
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(3000);
                
                java.net.InetAddress server = java.net.InetAddress.getByName(host);
                socket.send(new DatagramPacket(data, data.length, server, port));
                
                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                
                System.out.println("=== BÀI TẬP 2 - UDP CLIENT ===");
                System.out.println("Gửi: " + command);
                
                try {
                    socket.receive(response);
                    String text = new String(response.getData(),
                        response.getOffset(), response.getLength(),
                        StandardCharsets.UTF_8);
                    System.out.println("Nhận: " + text);
                } catch (java.net.SocketTimeoutException e) {
                    System.err.println("❌ Timeout - Server không phản hồi");
                }
            }
        }
    }
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Cách sử dụng:");
            System.out.println("  java exercise.Ex2_DateTimeService tcp-server");
            System.out.println("  java exercise.Ex2_DateTimeService tcp-client [host] [port]");
            System.out.println("  java exercise.Ex2_DateTimeService udp-server");
            System.out.println("  java exercise.Ex2_DateTimeService udp-client [host] [port] [command]");
            return;
        }
        
        switch (args[0].toLowerCase()) {
            case "tcp-server":
                TcpServer.main(new String[]{});
                break;
            case "tcp-client":
                String[] tcpArgs = new String[args.length - 1];
                System.arraycopy(args, 1, tcpArgs, 0, tcpArgs.length);
                TcpClient.main(tcpArgs);
                break;
            case "udp-server":
                UdpServer.main(new String[]{});
                break;
            case "udp-client":
                String[] udpArgs = new String[args.length - 1];
                System.arraycopy(args, 1, udpArgs, 0, udpArgs.length);
                try {
                    UdpClient.main(udpArgs);
                } catch (Exception e) {
                    System.err.println("❌ Lỗi: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Lệnh không hợp lệ: " + args[0]);
        }
    }
}
