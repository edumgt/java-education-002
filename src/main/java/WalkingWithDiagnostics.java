import java.lang.management.*;
import java.util.Scanner;

/**
 * 📚 프로그램 설명:
 * 사용자가 하루 목표 걸음 수인 10,000보를 달성했는지를 추적하고,
 * 실행 시 JVM의 메모리, 스레드, 클래스 로딩 상태를 출력합니다.
 */
public class WalkingWithDiagnostics {

    public static void main(String[] args) {
        printJVMDiagnostics(); // 시작 시 JVM 리소스 상태 출력

        Scanner scanner = new Scanner(System.in); // 👉 Stack에 참조, Heap에 Scanner 객체
        int goal = 10000; // 목표 걸음 수
        int allSteps = 0;

        System.out.println("\n🎯 목표: 하루 10,000걸음 걷기");
        System.out.println("🚶 'Going home' 입력 후 마지막 걸음 수 입력:");

        while (allSteps < goal) {
            String steps = scanner.nextLine();

            if (steps.equalsIgnoreCase("Going home")) {
                System.out.print("🏠 집까지 마지막으로 걸은 걸음 수: ");
                int lastSteps = Integer.parseInt(scanner.nextLine());
                allSteps += lastSteps;
                break;
            }

            try {
                int walkedSteps = Integer.parseInt(steps);
                allSteps += walkedSteps;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ 숫자만 입력하세요!");
            }
        }

        int diff = Math.abs(allSteps - goal);
        System.out.println();

        if (allSteps >= goal) {
            System.out.println("🎉 Goal reached! Good job!");
            System.out.printf("✅ %d steps over the goal!%n", diff);
        } else {
            System.out.printf("🚩 %d more steps to reach goal.%n", diff);
        }

        scanner.close();
        printJVMDiagnostics(); // 종료 시 JVM 리소스 상태 출력
    }

    // JVM 상태 출력 함수
    public static void printJVMDiagnostics() {
        System.out.println("\n🧠 JVM 상태 정보 출력 --------------------");

        // Memory 사용
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memBean.getNonHeapMemoryUsage();
        System.out.printf("📦 Heap 사용: %.2f MB / %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024,
                heap.getMax() / 1024.0 / 1024);
        System.out.printf("📦 Non-Heap 사용: %.2f MB / %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024,
                nonHeap.getMax() / 1024.0 / 1024);

        // 메타스페이스 등 Pool 메모리 사용
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            MemoryUsage usage = pool.getUsage();
            System.out.printf("🧩 [%s] 사용량: %.2f MB%n",
                    pool.getName(), usage.getUsed() / 1024.0 / 1024);
        }

        // 클래스 로딩 정보
        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        System.out.printf("📘 로딩된 클래스 수: %d (총 %d, 언로딩 %d)%n",
                classBean.getLoadedClassCount(),
                classBean.getTotalLoadedClassCount(),
                classBean.getUnloadedClassCount());

        // 스레드 정보
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.printf("🧵 현재 실행 중인 스레드 수: %d%n", threadBean.getThreadCount());
        System.out.printf("⏱ 총 CPU 시간: %.2f초%n",
                threadBean.getCurrentThreadCpuTime() / 1_000_000_000.0);

        // JVM 업타임
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        System.out.printf("⏳ 프로그램 시작 이후 시간: %.2f초%n",
                runtime.getUptime() / 1000.0);

        System.out.println("🧠 JVM 상태 정보 출력 종료 --------------------\n");
    }
}
