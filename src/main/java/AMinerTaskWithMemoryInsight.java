/**
 * 📘 프로그램 설명:
 * 자바에서 Map과 Scanner를 활용하여 사용자로부터 자원 이름과 수량을 입력받고,
 * 입력된 자원들을 Map에 저장해 최종적으로 자원별 총 수량을 출력하는 프로그램입니다.
 * 
 * ▶ 입력은 "자원명", "수량" 순으로 반복되며 "stop" 입력 시 종료됩니다.
 * ▶ 리소스별 수량은 누적되며, 마지막에 전체 자원 목록과 JVM의 리소스 사용량(Heap, CPU 시간)을 출력합니다.
 *
 * 💡 교육 포인트:
 * - Stack / Heap / Method Area에 어떤 데이터가 저장되는지 추적
 * - JVM의 메모리 사용량, CPU 사용 시간 출력
 * - Map 사용법, 입력 검증 처리
 */

import java.lang.management.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class AMinerTaskWithMemoryInsight {

    public static void main(String[] args) {
        // 📘 Method Area
        // 클래스 정보, static 정보가 Method Area에 저장됨

        // 📘 Stack
        // 지역 변수: scanner, resourceMap (참조만), resource, quantity

        // 📘 Heap
        // new Scanner(...) → Heap
        // new LinkedHashMap<>() → Heap

        System.out.println("📚 JVM 메모리 구조 설명");
        System.out.println("🔹 Method Area : 클래스 메타정보, static 필드");
        System.out.println("🔸 Stack       : 지역 변수, 참조 변수, 메서드 호출 정보");
        System.out.println("🔸 Heap        : 객체(new로 생성된 Scanner, Map 등)");
        System.out.println();

        // ▶ Stack: 참조 저장 / Heap: 객체 저장
        Scanner scanner = new Scanner(System.in);
        System.out.println("📍 new Scanner(System.in) → Scanner 객체는 Heap에 저장, 참조는 Stack");

        Map<String, Integer> resourceMap = new LinkedHashMap<>();
        System.out.println("📍 new LinkedHashMap<>() → Map 객체는 Heap에 저장, 참조는 Stack\n");

        // JVM 관리 객체 가져오기
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long startCpuTime = threadMXBean.getCurrentThreadCpuTime();

        System.out.println("💡 자원을 입력하세요. 'stop'을 입력하면 종료됩니다.\n");

        while (true) {
            System.out.print("자원 이름 입력 (예: gold, silver): ");
            String resource = scanner.nextLine().trim(); // Stack에 저장, 문자열은 Heap 또는 String Constant Pool

            System.out.println("🧾 [resource는 Stack에 참조 저장, 문자열 리터럴은 Constant Pool(Method Area)]");

            if (resource.equalsIgnoreCase("stop")) {
                System.out.println("🛑 'stop' 입력 → 입력 종료\n");
                break;
            }

            System.out.print("해당 자원의 수량 입력: ");
            int quantity;

            try {
                quantity = Integer.parseInt(scanner.nextLine().trim()); // Stack 저장
                System.out.println("✅ quantity는 Stack에 저장되는 int 기본형입니다.");
            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자가 아닙니다. 다시 입력하세요.");
                continue;
            }

            // 📦 Heap의 Map 데이터 수정
            int currentQty = resourceMap.getOrDefault(resource, 0);
            resourceMap.put(resource, currentQty + quantity);

            System.out.printf("📦 Map.put('%s', %d) → Heap의 Map 객체에 저장됨%n", resource, currentQty + quantity);

            // ▶ 리소스 사용량 출력
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();
            long usedHeap = heapUsage.getUsed() / 1024 / 1024;
            long maxHeap = heapUsage.getMax() / 1024 / 1024;
            System.out.printf("🧠 Heap 사용량: %d MB / %d MB%n", usedHeap, maxHeap);

            long cpuTime = threadMXBean.getCurrentThreadCpuTime();
            double cpuMillis = (cpuTime - startCpuTime) / 1_000_000.0;
            System.out.printf("⚙️  CPU 사용 시간: %.2f ms (현재 쓰레드)\n\n", cpuMillis);
        }

        // 🔍 결과 출력
        System.out.println("📦 수집된 자원 목록 출력 (Heap의 Map에서 가져옴):");
        for (Map.Entry<String, Integer> entry : resourceMap.entrySet()) {
            String key = entry.getKey();       // Stack 참조
            Integer value = entry.getValue();  // Stack 참조
            System.out.printf("🔸 %s → %d%n", key, value);
        }

        // 자원 정리
        scanner.close();
        System.out.println("\n🧹 Scanner 닫기 → 입력 스트림 해제 요청 (Heap 자원 대상)");
        System.out.println("✅ 프로그램 종료 → Stack 프레임 제거, Heap 객체는 GC 대상이 될 수 있음");
    }
}
