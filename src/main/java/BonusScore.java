import java.lang.management.*;
import java.util.Scanner;

public class BonusScore {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // 사용자로부터 점수를 입력받음
            System.out.print("점수를 입력하세요: ");
            int points = Integer.parseInt(scanner.nextLine());

            double bonus = 0;

            // 기본 보너스 점수 계산
            if (points <= 100) {
                bonus += 5;
            } else if (points <= 1000) {
                bonus += points * 0.20;
            } else {
                bonus += points * 0.10;
            }

            // 추가 보너스 조건
            if (points % 2 == 0) {
                bonus += 1;
            }
            if (points % 10 == 5) {
                bonus += 2;
            }

            // 결과 출력
            System.out.println("보너스 점수: " + bonus);
            System.out.println("최종 점수: " + (points + bonus));

            // JVM 리소스 정보 출력 시작
            System.out.println("\n📊 [JVM 실행 중 리소스 정보]");

            // 1️⃣ 메모리 정보 (Heap / Non-Heap)
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();

            System.out.printf("Heap Memory 사용량: %.2f MB / 최대: %.2f MB%n",
                    heap.getUsed() / 1024.0 / 1024,
                    heap.getMax() / 1024.0 / 1024);
            System.out.printf("Non-Heap Memory 사용량: %.2f MB%n",
                    nonHeap.getUsed() / 1024.0 / 1024);

            // 2️⃣ 스레드 수
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            System.out.println("스레드 수: " + threadMXBean.getThreadCount());

            // 3️⃣ 클래스 로딩 수
            ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
            System.out.println("로딩된 클래스 수: " + classLoadingMXBean.getLoadedClassCount());

            // 4️⃣ CPU Load (OS 기준)
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
                com.sun.management.OperatingSystemMXBean sunOs =
                        (com.sun.management.OperatingSystemMXBean) osBean;
                double processCpuLoad = sunOs.getProcessCpuLoad() * 100;
                System.out.printf("CPU 사용률: %.2f%%%n", processCpuLoad);
            } else {
                System.out.println("CPU 사용률: 측정 불가 (플랫폼 미지원)");
            }

            // 5️⃣ Uptime
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            System.out.printf("프로그램 실행 시간: %.2f 초%n", runtimeMXBean.getUptime() / 1000.0);

        } catch (NumberFormatException e) {
            System.out.println("잘못된 숫자 형식입니다. 정수를 입력하세요.");
        }
    }
}
