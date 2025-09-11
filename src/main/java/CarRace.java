import java.lang.management.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CarRace {
    public static void main(String[] args) {
        long startTime = System.nanoTime(); // 프로그램 실행 시작 시간 기록

        Scanner scanner = new Scanner(System.in);

        // 사용자 입력 유도
        System.out.println("🏁 자동차 경주 시뮬레이션을 시작합니다.");
        System.out.print("🚗 경로 타임 리스트를 공백으로 입력하세요 (예: 10 20 0 30 40): ");

        // 공백으로 구분된 숫자 문자열을 정수 리스트로 변환
        List<Integer> numbers = Arrays
                .stream(scanner.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int middleElementIndex = numbers.size() / 2;

        double leftCar = 0;
        double rightCar = 0;

        for (int i = 0; i < middleElementIndex; i++) {
            int current = numbers.get(i);
            if (current == 0) {
                leftCar -= leftCar * 0.20;
            } else {
                leftCar += current;
            }
        }

        for (int i = numbers.size() - 1; i > middleElementIndex; i--) {
            int current = numbers.get(i);
            if (current == 0) {
                rightCar -= rightCar * 0.20;
            } else {
                rightCar += current;
            }
        }

        if (leftCar > rightCar) {
            System.out.printf("🏆 The winner is right with total time: %.1f%n", rightCar);
        } else {
            System.out.printf("🏆 The winner is left with total time: %.1f%n", leftCar);
        }

        scanner.close();

        // ===== JVM 리소스 사용 정보 출력 =====
        System.out.println("\n🧠 JVM 리소스 사용 정보");

        // 1. 메모리 사용량
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memoryBean.getNonHeapMemoryUsage();
        System.out.printf("📦 Heap 메모리 사용량: %.2f MB / 최대 %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024, heap.getMax() / 1024.0 / 1024);
        System.out.printf("🧩 Non-Heap 메모리 사용량: %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024);

        // 2. 스레드 수
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.println("🧵 현재 스레드 수: " + threadBean.getThreadCount());

        // 3. 클래스 로딩 수
        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        System.out.println("📚 로딩된 클래스 수: " + classBean.getLoadedClassCount());

        // 4. CPU 사용률 (가능한 경우)
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            double cpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad() * 100;
            System.out.printf("🖥️ CPU 사용률: %.2f%%%n", cpuLoad);
        } else {
            System.out.println("🖥️ CPU 사용률: 측정 불가 (플랫폼 미지원)");
        }

        // 5. 실행 시간
        long endTime = System.nanoTime();
        double durationSec = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("⏱ 프로그램 실행 시간: %.3f 초%n", durationSec);
    }
}
