package network;

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;

/**
 * VÍ DỤ 4.1 - KHẢO SÁT ĐỊA CHỈ MẠNG
 * 
 * Chương trình này sử dụng InetAddress để phân giải hostname và nhận diện các đặc điểm
 * của địa chỉ mạng trả về như: loại IP (IPv4 hay IPv6), có phải loopback không, 
 * có phải site local không.
 * 
 * Cách chạy:
 * java network.HostInspector localhost
 * java network.HostInspector example.com
 * java network.HostInspector host-khong-ton-tai.invalid
 */
public class HostInspector {
    public static void main(String[] args) {
        // Kiểm tra tham số đầu vào
        if (args.length != 1) {
            System.out.println("Cách sử dụng: java network.HostInspector <hostname>");
            return;
        }

        try {
            // Lấy tất cả địa chỉ IP của hostname
            InetAddress[] addresses = InetAddress.getAllByName(args[0]);
            
            System.out.println("=== KHẢO SÁT ĐỊA CHỈ MẠNG ===");
            System.out.println("Host: " + args[0]);
            System.out.println("Số lượng địa chỉ: " + addresses.length);
            System.out.println();

            for (int i = 0; i < addresses.length; i++) {
                InetAddress address = addresses[i];
                System.out.println("Địa chỉ #" + (i + 1) + ":");
                
                // Hiển thị địa chỉ IP
                System.out.println("  - IP: " + address.getHostAddress());
                
                // Xác định loại IP (IPv4 hay IPv6)
                String ipType = (address instanceof Inet4Address) ? "IPv4" : "IPv6";
                System.out.println("  - Loại: " + ipType);
                
                // Hiển thị tên canonical (tên đầy đủ)
                System.out.println("  - Tên Canonical: " + address.getCanonicalHostName());
                
                // Kiểm tra có phải là loopback không (127.0.0.1 hoặc ::1)
                System.out.println("  - Loopback: " + address.isLoopbackAddress());
                
                // Kiểm tra có phải là site local không (192.168.x.x, 10.x.x.x, v.v.)
                System.out.println("  - Site Local: " + address.isSiteLocalAddress());
                
                // Kiểm tra có phải là multicast không
                System.out.println("  - Multicast: " + address.isMulticastAddress());
                
                System.out.println();
            }

        } catch (UnknownHostException e) {
            System.err.println("❌ Lỗi: Không thể phân giải hostname '" + args[0] + "'");
            System.err.println("   Kiểm tra tên hostname hoặc kết nối mạng.");
        } catch (Exception e) {
            System.err.println("❌ Lỗi không mong muốn: " + e.getMessage());
        }
    }
}
