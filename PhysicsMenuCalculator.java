import java.lang.management.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PhysicsMenuCalculator {

    public static void main(String[] args) {
        // 1. 실행 인자가 있으면 자동 실행
        if (args.length > 0) {
            String arg = args[0];
            if ("9".equals(arg)) {
                System.out.println("\n🚀 Physics Mini Calculator (자동 실행 모드)\n");
                System.out.println("👉 메뉴 선택: 9 (JVM/시스템 상태 출력)\n");
                printJvmAndSystemStatus();
                System.out.println("\n👋 프로그램을 종료합니다.");
                return;
            } else if ("0".equals(arg)) {
                System.out.println("\n👋 프로그램을 종료합니다. (자동 실행)");
                return;
            } else {
                System.out.println("\n❌ 알 수 없는 실행 인자: " + arg);
                return;
            }
        }

        // 2. 실행 인자가 없으면 기존 메뉴 방식
        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("\n🚀 Physics Mini Calculator (m, s 단위)\n");
                System.out.println("메뉴를 선택하세요:");
                System.out.println("  1. 가속도 (Acceleration)");
                System.out.println("  2. 평균 속도 (Average Velocity)");
                System.out.println("  3. 이동 거리 (Distance)");
                System.out.println("  9. JVM/시스템 상태 출력");
                System.out.println("  0. 종료");
                System.out.print("\n👉 선택: ");

                int choice = safeReadInt(input);

                switch (choice) {
                    case 1:
                        double u1 = safeReadDouble(input, "초기 속도 (m/s): ");
                        double v1 = safeReadDouble(input, "최종 속도 (m/s): ");
                        double t1 = safeReadDouble(input, "시간 (s): ");
                        double acceleration = (v1 - u1) / t1;
                        System.out.println("\n▶ 가속도 = " + String.format("%.4f", acceleration) + " m/s^2");
                        break;

                    case 2:
                        double u2 = safeReadDouble(input, "초기 속도 (m/s): ");
                        double v2 = safeReadDouble(input, "최종 속도 (m/s): ");
                        double avg_velocity = (u2 + v2) / 2.0;
                        System.out.println("\n▶ 평균 속도 = " + String.format("%.4f", avg_velocity) + " m/s");
                        break;

                    case 3:
                        double u3 = safeReadDouble(input, "초기 속도 (m/s): ");
                        double v3 = safeReadDouble(input, "최종 속도 (m/s): ");
                        double t3 = safeReadDouble(input, "시간 (s): ");
                        double avg_v3 = (u3 + v3) / 2.0;
                        double distance = avg_v3 * t3;
                        System.out.println("\n▶ 이동 거리 = " + String.format("%.4f", distance) + " m");
                        break;

                    case 9:
                        printJvmAndSystemStatus();
                        break;

                    case 0:
                        System.out.println("\n👋 프로그램을 종료합니다.");
                        running = false;
                        break;

                    default:
                        System.out.println("\n❌ 잘못된 선택입니다. (0~3, 9 입력)");
                }
            }
        }
    }

    // ✅ 안전하게 int 입력받기
    private static int safeReadInt(Scanner input) {
        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("❌ 잘못된 입력입니다. 정수를 입력하세요: ");
                input.nextLine();
            }
        }
    }

    // ✅ 안전하게 double 입력받기
    private static double safeReadDouble(Scanner input, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("❌ 잘못된 입력입니다. 숫자를 입력하세요.");
                input.nextLine();
            }
        }
    }

    // ✅ JVM & 시스템 상태 출력 함수
    private static void printJvmAndSystemStatus() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        System.out.println("\n🖥 JVM & System 상태 -------------------");
        System.out.println("▶ Heap 메모리: 사용 " + (heap.getUsed() / (1024 * 1024)) + "MB / "
                + (heap.getMax() / (1024 * 1024)) + "MB");
        System.out.println("▶ Non-Heap 메모리: 사용 " + (nonHeap.getUsed() / (1024 * 1024)) + "MB");
        System.out.println("▶ 실행 중 스레드 수: " + threadMXBean.getThreadCount());
        System.out.println("▶ 로드된 클래스 수: " + classLoadingMXBean.getLoadedClassCount());
        System.out.println("▶ OS: " + osBean.getName() + " " + osBean.getVersion());
        System.out.println("▶ CPU 아키텍처: " + osBean.getArch());
        System.out.println("▶ CPU 코어 수: " + osBean.getAvailableProcessors());

        double load = osBean.getSystemLoadAverage();
        if (load >= 0) {
            System.out.println("▶ 시스템 Load Average: " + load);
        } else {
            System.out.println("▶ 시스템 Load Average: 지원되지 않음");
        }
        System.out.println("-----------------------------------\n");
    }
}
