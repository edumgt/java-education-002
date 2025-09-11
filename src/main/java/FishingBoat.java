import java.util.Scanner; // 외부 패키지를 현재 클래스에 불러오기 위해 사용합니다.
import java.lang.management.*; // JVM 리소스 정보를 얻기 위해 사용합니다.

public class FishingBoat {
    // 이 Java 파일의 주요 클래스 정의입니다. 클래스 이름은 파일 이름과 일치해야 합니다.

    public static void main(String[] args) {
        long startTime = System.nanoTime(); // 프로그램 실행 시간 측정 시작 (나노초 단위)

        Scanner scanner = new Scanner(System.in); // 사용자로부터 콘솔 입력을 받기 위해 Scanner 객체를 생성합니다.

        System.out.print("예산을 입력하세요 (원): ");
        int budget = Integer.parseInt(scanner.nextLine()); // 사용자로부터 예산 입력 받기

        System.out.print("계절을 입력하세요 (Spring, Summer, Autumn, Winter): ");
        String season = scanner.nextLine(); // 계절 입력 받기

        System.out.print("출항 인원 수를 입력하세요: ");
        int fishers = Integer.parseInt(scanner.nextLine()); // 인원 수 입력 받기

        double basePrice = 0;

        // 계절에 따라 기본 요금 설정
        switch (season) {
            case "Spring":
                basePrice = 3000;
                break;
            case "Summer":
            case "Autumn":
                basePrice = 4200;
                break;
            case "Winter":
                basePrice = 2600;
                break;
            default:
                System.out.println("❌ 올바르지 않은 계절입니다.");
                scanner.close();
                return;
        }

        // 인원 수에 따라 할인율 결정
        double discount;
        if (fishers <= 6) {
            discount = 0.10;
        } else if (fishers <= 11) {
            discount = 0.15;
        } else {
            discount = 0.25;
        }

        double price = basePrice * (1 - discount); // 할인 적용

        // 짝수 인원 추가 할인 (단, 가을 제외)
        if (fishers % 2 == 0 && !season.equals("Autumn")) {
            price *= 0.95;
        }

        // 예산과 비교하여 결과 출력
        if (budget >= price) {
            System.out.printf("Yes! You have %.2f 원 left.%n", budget - price);
        } else {
            System.out.printf("Not enough money! You need %.2f 원.%n", price - budget);
        }

        scanner.close();

        // --------------------------------------------
        // ✅ JVM 리소스 사용 정보 출력
        // --------------------------------------------
        System.out.println("\n🧠 [JVM 리소스 사용 정보]");

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryBean.getHeapMemoryUsage();       // Heap 메모리
        MemoryUsage nonHeap = memoryBean.getNonHeapMemoryUsage(); // Non-Heap 메모리

        System.out.printf("📦 Heap 메모리 사용: %.2f MB / 최대: %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024, heap.getMax() / 1024.0 / 1024);
        System.out.printf("🛠 Non-Heap 메모리 사용: %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024);

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.printf("🧵 현재 스레드 수: %d%n", threadBean.getThreadCount());

        ClassLoadingMXBean classBean = ManagementFactory
        .getClassLoadingMXBean();
        System.out.printf("📚 로딩된 클래스 수: %d%n", classBean.getLoadedClassCount());

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            double cpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean)
            .getProcessCpuLoad() * 100;
            System.out.printf("💻 CPU 사용률: %.2f%%%n", cpuLoad);
        }

        long endTime = System.nanoTime(); // 종료 시간
        double duration = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("⏱ 총 실행 시간: %.3f초%n", duration);

        // --------------------------------------------
        // ✅ Stack, Heap, Non-Heap 메모리 구조 설명
        // --------------------------------------------
        System.out.println("\n📘 자바 메모리 구조 해설");
        System.out.println("🔹 Stack: 메서드 호출 시 생성되는 지역 변수, 매개변수 등이 저장됩니다. (예: scanner, budget)");
        System.out.println("🔹 Heap: new 키워드로 생성된 객체가 저장되는 영역입니다. (예: Scanner 객체)");
        System.out.println("🔹 Non-Heap: 클래스 메타데이터, static 데이터 등이 저장됩니다. (예: 클래스 로딩 정보, 상수 풀 등)");
    }
}
