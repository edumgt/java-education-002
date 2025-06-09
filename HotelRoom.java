import java.util.Scanner;
import java.lang.management.*;
// 외부 패키지를 현재 클래스에 불러오기 위해 사용합니다.

public class HotelRoom {
    // 이 Java 파일의 주요 클래스 정의입니다. 클래스 이름은 파일 이름과 일치해야 합니다.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 사용자로부터 콘솔 입력을 받기 위해 Scanner 객체를 생성합니다.

        System.out.print("숙박 월을 입력하세요 (May, June, July, August, September, October): ");
        String month = scanner.nextLine();
        // 사용자로부터 한 줄의 입력을 받아옵니다.

        System.out.print("숙박 일수를 입력하세요: ");
        int nights = Integer.parseInt(scanner.nextLine());
        // 사용자로부터 한 줄의 입력을 받아옵니다.

        double studioPrice = 0;
        double apartmentPrice = 0;

        switch (month) {
            // switch 문: 변수의 값에 따라 여러 경우(case)를 나눠 실행합니다.
            case "May":
            case "October":
                studioPrice = 50 * nights;
                apartmentPrice = 65 * nights;
                if (nights > 14) {
                    studioPrice *= 0.70; // 30% 할인
                    apartmentPrice *= 0.90; // 10% 할인
                } else if (nights > 7) {
                    studioPrice *= 0.95; // 5% 할인
                }
                break;

            case "June":
            case "September":
                studioPrice = 75.20 * nights;
                apartmentPrice = 68.70 * nights;
                if (nights > 14) {
                    studioPrice *= 0.80; // 20% 할인
                    apartmentPrice *= 0.90;
                }
                break;

            case "July":
            case "August":
                studioPrice = 76 * nights;
                apartmentPrice = 77 * nights;
                if (nights > 14) {
                    apartmentPrice *= 0.90;
                }
                break;

            default:
                System.out.println("❌ 잘못된 월을 입력하셨습니다.");
                scanner.close();
                return;
        }

        System.out.printf("Apartment: %.2f lv.%n", apartmentPrice);
        System.out.printf("Studio: %.2f lv.%n", studioPrice);

        // === JVM 리소스 사용량 출력 ===
        System.out.println("\n📘 [JVM 실행 상태 및 메모리 사용 정보]");

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memoryBean.getNonHeapMemoryUsage();

        System.out.printf("🧠 Heap 메모리 사용량: %.2f MB / %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024, heap.getMax() / 1024.0 / 1024);
        System.out.printf("🧠 Non-Heap 메모리 사용량: %.2f MB / %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024, nonHeap.getMax() / 1024.0 / 1024);

        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            MemoryUsage usage = pool.getUsage();
            System.out.printf("📦 메모리 풀 [%s]: %.2f MB / %.2f MB%n",
                    pool.getName(), usage.getUsed() / 1024.0 / 1024, usage.getMax() / 1024.0 / 1024);
        }

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.printf("🧵 현재 실행 중인 스레드 수: %d%n", threadBean.getThreadCount());

        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        System.out.printf("⏳ 프로그램 실행 시간(Uptime): %.2f초%n", runtimeBean.getUptime() / 1000.0);

        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        System.out.printf("📚 로딩된 클래스 수: %d개%n", classBean.getLoadedClassCount());

        // === Stack vs Heap 설명 출력 ===
        System.out.println("\n📂 [Stack과 Heap 메모리 구조 해설]");
        System.out.println("✔ Stack: 지역 변수, 매개변수 등");
        System.out.println("   - month, nights, studioPrice, apartmentPrice 등");
        System.out.println("✔ Heap: 객체 및 참조형 데이터 저장");
        System.out.println("   - Scanner 객체, String 객체 등");

        scanner.close(); // 자원 정리
    }
}
