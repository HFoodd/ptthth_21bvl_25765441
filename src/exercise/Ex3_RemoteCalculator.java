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
 * BÀI TẬP 3 - MÁY TÍNH TỪ XA
 * 
 * YÊU CẦU:
 * - Thiết kế yêu cầu theo dạng: CALC toán_tử toán_hạng_1 toán_hạng_2
 * - Server hỗ trợ cộng (+), trừ (-), nhân (*), chia (/)
 * - Trả về "OK kết_quả" hoặc "ERR mã_lỗi"
 * 
 * VÍ DỤ:
 * - CALC + 100 200     -> OK 300
 * - CALC - 50 10       -> OK 40
 * - CALC * 5 6         -> OK 30
 * - CALC / 10 2        -> OK 5
 * - CALC / 10 0        -> ERR DIVIDE_BY_ZERO
 * - CALC % 10 3        -> ERR UNSUPPORTED_OPERATOR
 * - CALC + a 2         -> ERR INVALID_NUMBER
 * - CALC +             -> ERR INVALID_FORMAT
 * 
 * TCP PORT: 5013
 */
public class Ex3_RemoteCalculator {
    
    // ============ SERVER ============
    
    static class Server {
        private static final int PORT = 5013;
        
        public static void main(String[] args) {
            try (ServerSocket server = new ServerSocket(PORT)) {
                System.out.println("=== BÀI TẬP 3 - MÁY TÍNH TỪXƯƠNG (Calculator Server) ===");
                System.out.println("Server lắng nghe trên cổng " + PORT);
                System.out.println("Các phép toán hỗ trợ: +, -, *, /\n");
                
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
                    System.out.println("  Yêu cầu: " + request);
                    System.out.println("  Phản hồi: " + response);
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
            
            // Tách các phần của yêu cầu
            String[] parts = trimmed.split("\\s+");
            
            // Kiểm tra định dạng: phải có ít nhất 4 thành phần
            // CALC toán_tử toán_hạng_1 toán_hạng_2
            if (parts.length < 4 || !parts[0].equalsIgnoreCase("CALC")) {
                return "ERR INVALID_FORMAT";
            }
            
            String operator = parts[1];
            String operand1Str = parts[2];
            String operand2Str = parts[3];
            
            // Kiểm tra toán tử có hợp lệ không
            if (!operator.matches("[+\\-*/]")) {
                return "ERR UNSUPPORTED_OPERATOR";
            }
            
            try {
                // Chuyển đổi toán hạng thành số
                double operand1 = Double.parseDouble(operand1Str);
                double operand2 = Double.parseDouble(operand2Str);
                
                // Thực hiện phép tính
                double result;
                switch (operator) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        if (operand2 == 0) {
                            return "ERR DIVIDE_BY_ZERO";
                        }
                        result = operand1 / operand2;
                        break;
                    default:
                        return "ERR UNSUPPORTED_OPERATOR";
                }
                
                // Trả về kết quả
                // Nếu kết quả là số nguyên, hiển thị mà không có phần thập phân
                if (result == (long) result) {
                    return "OK " + (long) result;
                } else {
                    return "OK " + result;
                }
                
            } catch (NumberFormatException e) {
                return "ERR INVALID_NUMBER";
            }
        }
    }
    
    // ============ CLIENT ============
    
    static class Client {
        public static void main(String[] args) {
            String host = args.length > 0 ? args[0] : "localhost";
            int port = args.length > 1 ? Integer.parseInt(args[1]) : 5013;
            
            try (Socket socket = new Socket(host, port);
                 BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8));
                 BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                
                System.out.println("=== BÀI TẬP 3 - CALCULATOR CLIENT ===");
                System.out.println("✓ Kết nối tới " + host + ":" + port);
                System.out.println("\nCác lệnh:");
                System.out.println("  CALC + số1 số2      - Cộng");
                System.out.println("  CALC - số1 số2      - Trừ");
                System.out.println("  CALC * số1 số2      - Nhân");
                System.out.println("  CALC / số1 số2      - Chia");
                System.out.println("  QUIT                - Thoát\n");
                
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
            System.out.println("  java exercise.Ex3_RemoteCalculator server");
            System.out.println("  java exercise.Ex3_RemoteCalculator client [host] [port]");
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
