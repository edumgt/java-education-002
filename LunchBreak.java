import java.util.Scanner;
import java.lang.management.*;
// 외부 패키지를 현재 클래스에 불러오기 위해 사용합니다.

public class LunchBreak {
    // 이 Java 파일의 주요 클래스 정의입니다. 클래스 이름은 파일 이름과 일치해야 합니다.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 사용자로부터 콘솔 입력을 받기 위해 Scanner 객체를 생성합니다.

        System.out.print("영화 제목을 입력하세요: ");
        String movie = scanner.nextLine();
        // 사용자로부터 한 줄의 입력을 받아옵니다.

        System.out.print("영화의 러닝타임(분)을 입력하세요: ");
        int duration = Integer.parseInt(scanner.nextLine());
        // 사용자로부터 한 줄의 입력을 받아옵니다.

        System.out.print("점심시간 전체 길이(분)을 입력하세요: ");
        int breakTime = Integer.parseInt(scanner.nextLine());
        // 사용자로부터 한 줄의 입력을 받아옵니다.

        // 점심 시간 중 1/8은 식사, 1/4는 휴식 (나머지만 자유시간)
        double freeTime = breakTime - (breakTime / 8.0 + breakTime / 4.0);
        double left = Math.ceil(Math.abs(freeTime - duration));

        if (freeTime >= duration) {
            // 조건문: 특정 조건이 참일 때 실행되는 코드를 정의합니다.
            System.out.printf("You have enough time to watch %s and left with %.0f minutes free time.%n", movie, left);
            // 출력문: 콘솔에 메시지나 결과를 출력합니다.
        } else {
            System.out.printf("You don't have enough time to watch %s, you need %.0f more minutes.%n", movie, left);
            // 출력문: 콘솔에 메시지나 결과를 출력합니다.
        }

        // === JVM 상태 정보 출력 ===
        System.out.println("\n📘 [JVM 실행 상태 및 메모리 정보]");

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

        // === 메모리 구조 해설 ===
        System.out.println("\n📂 [Stack과 Heap 메모리 구조 해설]");
        System.out.println("✔ Stack: 지역 변수, 메서드 호출 정보 등 (예: movie, duration, breakTime)");
        System.out.println("✔ Heap: 객체 및 배열 (예: Scanner, String 인스턴스)");

        scanner.close();
    }
}

/*
 * Metaspace는 JVM이 **클래스 메타데이터(class metadata)**를 저장하는 공간입니다.
 * 
 * 클래스 정의, 메서드 시그니처, 필드 정보 등 클래스 자체의 구조 정보가 저장됩니다.
 * 
 * 실제 객체(instance)는 Heap에 있지만, 그 객체가 어떤 클래스인지, 어떤 메서드가 있는지는 Metaspace에 저장됩니다.
 */