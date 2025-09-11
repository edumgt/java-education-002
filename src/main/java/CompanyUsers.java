import java.util.*;
import java.lang.management.*;

/**
 * 프로그램 설명:
 * 이 프로그램은 회사와 직원 ID 정보를 관리하는 시스템입니다.
 * 사용자는 "회사 이름 -> 직원 ID" 형식으로 데이터를 입력하며,
 * 동일한 회사에 동일한 직원 ID가 중복 등록되지 않도록 처리합니다.
 * "End"를 입력하면 프로그램은 모든 회사별 직원 ID 목록을 출력합니다.
 *
 * 예시 입력:
 * SoftUni -> AA12345
 * SoftUni -> BB12345
 * Microsoft -> CC12345
 * SoftUni -> AA12345
 * End
 *
 * 출력 예:
 * SoftUni
 * -- AA12345
 * -- BB12345
 * Microsoft
 * -- CC12345
 */
public class CompanyUsers {
    public static void main(String[] args) {
        long startTime = System.nanoTime(); // 프로그램 실행 시간 측정 시작

        Scanner scanner = new Scanner(System.in);

        // 사용자에게 입력 방법 안내
        System.out.println("👩‍💼 회사 -> 직원 ID 형식으로 입력하세요 (예: SoftUni -> AA12345).");
        System.out.println("🛑 'End'를 입력하면 종료됩니다.");

        // 회사 이름을 Key로, 직원 ID 리스트를 Value로 저장
        Map<String, List<String>> companies = new LinkedHashMap<>();

        String input = scanner.nextLine(); // 첫 입력 받기
        while (!input.equals("End")) {
            // 입력을 " -> " 기준으로 분리
            String company = input.split(" -> ")[0];
            String id = input.split(" -> ")[1];

            // 회사가 처음 입력된 경우, 새 리스트 생성 후 ID 추가
            companies.putIfAbsent(company, new ArrayList<>());
            List<String> currentIds = companies.get(company);

            // 중복 ID가 아니라면 리스트에 추가
            if (!currentIds.contains(id)) {
                currentIds.add(id);
            }

            input = scanner.nextLine(); // 다음 입력
        }

        // 결과 출력
        companies.entrySet().forEach(entry -> {
            System.out.println(entry.getKey()); // 회사 이름
            entry.getValue().forEach(id -> System.out.printf("-- %s%n", id)); // 직원 ID 목록
        });

        scanner.close(); // 자원 정리

        // -------------------------------------------------------
        // ✅ 자바 메모리 및 리소스 정보 출력
        // -------------------------------------------------------
        System.out.println("\n🧠 JVM 실행 리소스 정보");

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memoryBean.getNonHeapMemoryUsage();

        System.out.printf("📦 Heap 메모리 사용: %.2f MB / 최대: %.2f MB%n",
                heap.getUsed() / 1024.0 / 1024, heap.getMax() / 1024.0 / 1024);
        System.out.printf("🛠 Non-Heap 메모리 사용: %.2f MB%n",
                nonHeap.getUsed() / 1024.0 / 1024);

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.printf("🧵 현재 스레드 수: %d%n", threadBean.getThreadCount());

        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        System.out.printf("📚 로딩된 클래스 수: %d%n", classBean.getLoadedClassCount());

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            double cpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad() * 100;
            System.out.printf("💻 CPU 사용률: %.2f%%%n", cpuLoad);
        }

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("⏱ 총 실행 시간: %.3f초%n", duration);

        // -------------------------------------------------------
        // ✅ 메모리 구조 해설
        // -------------------------------------------------------
        System.out.println("\n🧬 [자바 메모리 구조]");
        System.out.println("📍 Stack: 메서드 내 지역 변수, 호출 스택 저장 (예: input, company)");
        System.out.println("📦 Heap: new로 생성한 객체 저장 공간 (예: Scanner, ArrayList, Map)");
        System.out.println("🧠 Non-Heap: JVM 내부 구조(class metadata 등)에 사용되는 메모리");
        System.out.println("🔧 JVM은 이 구조를 바탕으로 객체 생명 주기와 실행 흐름을 관리합니다.");

        // 왜 LinkedHashMap을 쓸까?
        System.out.println("\n📌 왜 LinkedHashMap?");
        System.out.println("→ 입력된 순서대로 회사 목록을 출력하기 위함입니다.");
        System.out.println("  TreeMap은 키 정렬, HashMap은 순서 무작위. 출력 목적에 따라 선택이 달라집니다.");
    }
}
