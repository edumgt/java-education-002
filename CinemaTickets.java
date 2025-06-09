import java.lang.management.*;
import java.util.Scanner;

public class CinemaTickets {
    public static void main(String[] args) {
        long startTime = System.nanoTime(); // ⏱ 프로그램 시작 시간 측정

        Scanner scanner = new Scanner(System.in); // 🎯 Heap에 객체 생성, 참조는 Stack에 저장

        int student = 0;
        int standard = 0;
        int kid = 0;
        int totalTickets = 0;

        System.out.print("🎬 영화 제목을 입력하세요 (끝내려면 'Finish'): ");
        String movie = scanner.nextLine(); // 🎯 Stack 변수 movie

        while (!movie.equals("Finish")) {
            System.out.printf("💺 \"%s\" 상영관의 총 좌석 수를 입력하세요: ", movie);
            int allSeats = Integer.parseInt(scanner.nextLine());

            int currentSeats = 0;

            while (currentSeats < allSeats) {
                System.out.print("🎟 티켓 종류를 입력하세요 (student / standard / kid / End): ");
                String type = scanner.nextLine();

                if (type.equals("End")) {
                    break;
                }

                switch (type) {
                    case "student": student++; break;
                    case "standard": standard++; break;
                    case "kid": kid++; break;
                    default:
                        System.out.println("⚠️ 잘못된 티켓 유형입니다.");
                        continue;
                }
                currentSeats++;
            }

            double percentFull = currentSeats * 100.0 / allSeats;
            System.out.printf("%s - %.2f%% full.%n", movie, percentFull);
            totalTickets += currentSeats;

            System.out.print("\n🎬 다음 영화 제목 입력 (또는 'Finish'): ");
            movie = scanner.nextLine();
        }

        // ✅ 전체 통계 출력
        double percentStudent = student * 100.0 / totalTickets;
        double percentStandard = standard * 100.0 / totalTickets;
        double percentKid = kid * 100.0 / totalTickets;

        System.out.printf("%n🎟 총 티켓 수: %d%n", totalTickets);
        System.out.printf("%.2f%% student tickets.%n", percentStudent);
        System.out.printf("%.2f%% standard tickets.%n", percentStandard);
        System.out.printf("%.2f%% kids tickets.%n", percentKid);

        scanner.close();

        // -----------------------------------------------------
        // ✅ 실행 종료 후 JVM 리소스 및 메모리 구조 정보 출력
        // -----------------------------------------------------
        System.out.println("\n📊 [JVM 실행 리소스 정보]");
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
            System.out.println("💻 CPU 사용률: 지원되지 않음");
        }

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("⏱ 프로그램 실행 시간: %.3f초%n", duration);

        // -----------------------------------------------------
        // ✅ 메모리 구조 Stack vs Heap 설명
        // -----------------------------------------------------
        System.out.println("\n🧬 [자바 메모리 구조]");
        System.out.println("📦 Heap: new 연산자로 생성한 객체 저장 공간 (예: Scanner, String 객체 등)");
        System.out.println("🧾 Stack: 메서드 내의 지역 변수, 호출 스택 정보 (예: int student, double percent)");
        System.out.println("🧠 JVM은 이 두 영역을 통해 메모리 효율성과 실행 흐름을 관리합니다.");
    }
}
