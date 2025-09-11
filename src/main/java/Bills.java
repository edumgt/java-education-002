import java.util.Scanner;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;

public class Bills {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("몇 개월치 요금을 계산할까요? ");
        int months = Integer.parseInt(scanner.nextLine());

        double allElectricityCost = 0;
        double otherCost = 0;

        for (int i = 1; i <= months; i++) {
            System.out.printf("%d월 전기 요금을 입력하세요 (lv): ", i);
            double electricity = Double.parseDouble(scanner.nextLine());
            allElectricityCost += electricity;

            double monthlyBase = 20 + 15 + electricity; // 물, 인터넷, 전기
            otherCost += monthlyBase * 1.2; // 기타 요금: 전체의 20%
        }

        double allWaterCost = 20 * months;
        double allInternetCost = 15 * months;
        double total = allElectricityCost + allWaterCost + allInternetCost + otherCost;
        double average = total / months;

        System.out.printf("Electricity: %.2f lv%n", allElectricityCost);
        System.out.printf("Water: %.2f lv%n", allWaterCost);
        System.out.printf("Internet: %.2f lv%n", allInternetCost);
        System.out.printf("Other: %.2f lv%n", otherCost);
        System.out.printf("Average: %.2f lv%n", average);

        // 🧠 자바 메모리 및 CPU 정보 출력
        System.out.println("\n📊 [JVM 리소스 정보]");

        // 메모리 관리 빈
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();

        System.out.printf("🟦 Heap Memory 사용량: %.2f MB / 최대: %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024,
                heap.getMax() / 1024.0 / 1024);

        System.out.printf("🟨 Non-Heap Memory 사용량: %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024);

        // JVM 실행 시간 (CPU 사용과 비슷하게 참고됨)
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptimeMs = runtimeMXBean.getUptime();
        System.out.printf("⏱️ JVM 실행 시간: %.2f 초%n", uptimeMs / 1000.0);

        scanner.close();
    }
}
