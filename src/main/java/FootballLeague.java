import java.util.Scanner;
import java.lang.management.*;
import java.util.List;

public class FootballLeague {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 📌 Heap에 저장되는 객체

        // Stack에 저장되는 지역 변수
        System.out.print("경기장 전체 수용 인원을 입력하세요: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        System.out.print("입장한 총 관중 수를 입력하세요: ");
        int fans = Integer.parseInt(scanner.nextLine());

        int fansA = 0; // Stack
        int fansB = 0; // Stack
        int fansV = 0; // Stack
        int fansG = 0; // Stack

        for (int i = 1; i <= fans; i++) {
            System.out.printf("[%d번째 팬] 입장한 구역을 입력하세요 (A/B/V/G): ", i);
            String sector = scanner.nextLine(); // sector는 Stack, 문자열 리터럴은 String Pool

            switch (sector.toUpperCase()) {
                case "A": fansA++; break;
                case "B": fansB++; break;
                case "V": fansV++; break;
                case "G": fansG++; break;
                default: System.out.println("⚠️ 유효하지 않은 구역입니다. 무시됩니다.");
            }
        }

        double Apercentage = fansA * 100.0 / fans;
        double Bpercentage = fansB * 100.0 / fans;
        double Vpercentage = fansV * 100.0 / fans;
        double Gpercentage = fansG * 100.0 / fans;
        double AllPercentage = fans * 100.0 / capacity;

        System.out.printf("A 구역: %.2f%%%n", Apercentage);
        System.out.printf("B 구역: %.2f%%%n", Bpercentage);
        System.out.printf("V 구역: %.2f%%%n", Vpercentage);
        System.out.printf("G 구역: %.2f%%%n", Gpercentage);
        System.out.printf("전체 관중 비율: %.2f%%%n", AllPercentage);

        // 리소스 사용 정보 출력
        System.out.println("\n🔧 [JVM 실행 리소스 사용 정보]");
        MemoryMXBean memoryBean = 
        ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = 
        memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = 
        memoryBean.getNonHeapMemoryUsage();

        System.out.printf("🧠 Heap 메모리 사용량: %.2f MB / %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024, heap.getMax() / 1024.0 / 1024);
        System.out.printf("🧠 Non-Heap 메모리 사용량: %.2f MB / %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024, nonHeap.getMax() / 1024.0 / 1024);

        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pool : pools) {
            String name = pool.getName();
            if (name.contains("Metaspace") 
            || name.contains("Code Cache") 
            || name.contains("Perm Gen")) {
                MemoryUsage usage = pool.getUsage();
                System.out.printf("📦 %s 사용량: %.2f MB / %.2f MB%n",
                        name, usage.getUsed() / 1024.0 / 1024, usage.getMax() / 1024.0 / 1024);
            }
        }

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.printf("🧵 현재 실행 중인 스레드 수: %d개%n", threadBean.getThreadCount());

        if (threadBean.isThreadCpuTimeSupported()) {
            long cpuTime = threadBean.getCurrentThreadCpuTime();
            System.out.printf("⏱️ 현재 스레드 CPU 사용 시간: %.2f초%n", cpuTime / 1e9);
        }

        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        System.out.printf("📚 로딩된 클래스 수: %d개%n", 
        classBean.getLoadedClassCount());

        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        System.out.printf("⏳ 프로그램 Uptime: %.2f초%n", 
        runtimeBean.getUptime() / 1000.0);

        // Stack vs Heap에 대한 설명
        System.out.println("\n📘 [Stack / Heap 저장 구조 안내]");
        System.out.println("✅ Stack (메서드 호출 시 생성되는 지역 변수, 원시 타입):");
        System.out.println("   - int capacity, fans, fansA~G");
        System.out.println("   - double Apercentage ~ AllPercentage");
        System.out.println("   - for 루프 내 index i, sector");
        System.out.println("✅ Heap (객체 인스턴스, 참조 타입):");
        System.out.println("   - Scanner scanner");
        System.out.println("   - String, MemoryMXBean, List<MemoryPoolMXBean>");
        System.out.println("   - ThreadMXBean, RuntimeMXBean, ClassLoadingMXBean 등");

        scanner.close(); // 리소스 해제
    }
}
