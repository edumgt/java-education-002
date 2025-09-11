import java.util.List;
import java.util.Scanner;
import java.lang.management.*;
// JVM 메모리 및 실행 상태를 조회하기 위한 패키지

public class GodzillaVsKong {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        // ➤ Heap 메모리에 저장되는 객체

        // Stack에 저장되는 지역 변수
        System.out.print("예산을 입력하세요: ");
        double budget = Double.parseDouble(scanner.nextLine());

        System.out.print("엑스트라 수를 입력하세요: ");
        int extra = Integer.parseInt(scanner.nextLine());

        System.out.print("의상 가격을 입력하세요 (엑스트라 1명당): ");
        double price = Double.parseDouble(scanner.nextLine());

        double decor = budget * 0.10; // 10% 세트장 비용
        double cloth = extra * price;

        if (extra > 150) {
            cloth *= 0.90; // 10% 할인
        }

        double total = decor + cloth;

        if (total > budget) {
            System.out.println("Not enough money!");
            System.out.printf("Wingard needs %.2f 원 more.%n", total - budget);
        } else {
            System.out.println("Action!");
            System.out.printf("Wingard starts filming with %.2f 원 left.%n", budget - total);
        }

        // === 추가: JVM 리소스 및 메모리 구조 분석 ===
        System.out.println("\n📘 [JVM 메모리 및 리소스 사용 현황]");

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memoryBean.getNonHeapMemoryUsage();

        System.out.printf("🧠 Heap 메모리 사용량: %.2f MB / %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024, 
                heap.getMax() / 1024.0 / 1024);
        System.out.printf("🧠 Non-Heap 메모리 사용량: %.2f MB / %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024, 
                nonHeap.getMax() / 1024.0 / 1024);

        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pool : pools) {
            MemoryUsage usage = pool.getUsage();
            System.out.printf("📦 메모리 풀 [%s]: %.2f MB / %.2f MB%n",
                    pool.getName(), 
                    usage.getUsed() / 1024.0 / 1024, 
                    usage.getMax() / 1024.0 / 1024);
        }

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.printf("🧵 현재 실행 중인 스레드 수: %d%n", threadBean.getThreadCount());

        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        System.out.printf("📚 로딩된 클래스 수: %d개%n", classBean.getLoadedClassCount());

        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        System.out.printf("⏳ 프로그램 실행 시간(Uptime): %.2f초%n", runtimeBean.getUptime() / 1000.0);

        // === 추가: 메모리 구조 설명 ===
        System.out.println("\n📂 [Stack과 Heap 메모리의 역할 및 본 코드 내 사용 예]");
        System.out.println("✔ Stack: 지역 변수, 매개변수 등");
        System.out.println("   - double budget, price, decor, total, cloth");
        System.out.println("   - int extra");
        System.out.println("✔ Heap: 객체와 인스턴스 저장 공간");
        System.out.println("   - Scanner scanner 객체 (new 연산자 사용)");
        System.out.println("   - String 객체 (문자열은 String Pool or Heap)");

        scanner.close(); // 자원 정리
    }
}
