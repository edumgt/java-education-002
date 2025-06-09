import java.lang.management.*;
import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        long startTime = System.nanoTime(); // ⏱ 실행 시간 측정을 위한 시작 시점 저장

        Scanner scanner = new Scanner(System.in);
        System.out.println("🎬 영화관 총 수익 계산 프로그램입니다.");
        System.out.print("상영 종류를 입력하세요 (Premiere, Normal, Discount): ");
        String project = scanner.nextLine(); // 📍 Stack 영역에 저장되는 지역 변수

        System.out.print("행(row) 개수를 입력하세요: ");
        int rows = Integer.parseInt(scanner.nextLine());

        System.out.print("열(column) 개수를 입력하세요: ");
        int cols = Integer.parseInt(scanner.nextLine());

        double pricePerTicket = 0; // 💡 Stack에 저장되는 기본형 지역 변수

        switch (project) {
            case "Premiere":
                pricePerTicket = 12.00;
                break;
            case "Normal":
                pricePerTicket = 7.50;
                break;
            case "Discount":
                pricePerTicket = 5.00;
                break;
            default:
                System.out.println("❌ 잘못된 상영 종류입니다.");
                scanner.close();
                return;
        }

        double total = pricePerTicket * rows * cols;
        System.out.printf("🎟️ 총 수익: %.2f 원%n", total);

        scanner.close();

        // -------------------------------
        // ✅ JVM 리소스 사용 정보 출력
        // -------------------------------
        System.out.println("\n📊 [실행 중 JVM 리소스 정보]");

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memoryBean.getNonHeapMemoryUsage();

        System.out.printf("🧠 Heap 메모리 사용: %.2f MB / 최대 %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024, heap.getMax() / 1024.0 / 1024);

        System.out.printf("🔧 Non-Heap 메모리 사용: %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024);

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.println("🧵 현재 스레드 수: " + threadBean.getThreadCount());

        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        System.out.println("📚 로딩된 클래스 수: " + classBean.getLoadedClassCount());

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            double cpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad() * 100;
            System.out.printf("💻 CPU 사용률: %.2f%%%n", cpuLoad);
        } else {
            System.out.println("💻 CPU 사용률: 지원되지 않는 플랫폼");
        }

        long endTime = System.nanoTime();
        double durationSec = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("⏱ 프로그램 실행 시간: %.3f초%n", durationSec);

        // -------------------------------
        // ✅ Stack과 Heap 영역 설명 출력
        // -------------------------------
        System.out.println("\n🧬 [메모리 구조 이해: Stack vs Heap]");
        System.out.println("🧾 Stack: 메서드 호출 시 생성되는 임시 변수 저장 공간 (예: rows, cols)");
        System.out.println("📦 Heap: new 연산자로 생성된 객체 저장 영역 (예: Scanner, String)");
        System.out.println("       ⇒ scanner 객체는 heap에 생성되며, scanner 변수는 stack에 위치합니다.");
    }
}
