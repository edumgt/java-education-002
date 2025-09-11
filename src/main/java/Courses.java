import java.util.*;
import java.lang.management.*;

/**
 * 프로그램 설명:
 * 이 프로그램은 과목별로 등록된 학생들의 명단을 관리하는 시스템입니다.
 * 사용자로부터 "과목 : 학생" 형식의 데이터를 입력받고,
 * 입력 종료 후 과목별로 학생 수와 명단을 출력합니다.
 *
 * 예시 입력:
 * 수학 : 홍길동
 * 영어 : 이영희
 * 수학 : 김철수
 * end
 *
 * 출력 예:
 * 수학: 2
 * -- 홍길동
 * -- 김철수
 * 영어: 1
 * -- 이영희
 */
public class Courses {
    public static void main(String[] args) {
        long startTime = System.nanoTime(); // ⏱ 프로그램 실행 시간 측정 시작

        Scanner scanner = new Scanner(System.in);

        // 과목별로 등록된 학생들을 저장할 Map 구조 생성
        Map<String, List<String>> courses = new LinkedHashMap<>();

        // 사용자에게 입력 형식을 안내하는 메시지 출력
        System.out.println("📌 과목과 학생 이름을 아래 형식대로 입력하세요:");
        System.out.println("예: 수학 : 홍길동");
        System.out.println("입력을 마치려면 'end'를 입력하세요.");
        System.out.println("---------------------------------------");

        String command = scanner.nextLine(); // 첫 입력 받기

        while (!command.equalsIgnoreCase("end")) {
            // 입력 문자열을 " : " 기준으로 나눠 과목과 학생으로 구분
            String[] parts = command.split(" : ");
            if (parts.length != 2) {
                System.out.println("입력 형식이 잘못되었습니다. 과목 : 학생' 형태로 입력하세요.");
            } else {
                String course = parts[0].trim();
                String student = parts[1].trim();

                // 과목이 없으면 새로 등록하고 학생 추가, 있으면 기존 목록에 학생 추가
                courses.putIfAbsent(course, new ArrayList<>());
                courses.get(course).add(student);
            }

            command = scanner.nextLine(); // 다음 입력 받기
        }

        // 결과 출력
        System.out.println("\n✅ 등록 결과:");
        courses.entrySet().forEach(course -> {
            System.out.printf("%s: %d명%n", 
            course.getKey(), course.getValue().size());
            course.getValue().forEach(student -> 
            System.out.printf("-- %s%n", student));
        });

        scanner.close(); // 입력 종료

        // -----------------------------------------------
        // ✅ JVM 리소스 정보 출력
        // -----------------------------------------------
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

        // -----------------------------------------------
        // ✅ 메모리 구조 해설 출력
        // -----------------------------------------------
        System.out.println("\n🔍 자바 메모리 구조 해설");
        System.out.println("📍 Stack: 메서드 실행 중의 지역 변수, 파라미터 저장 (예: command, scanner 등)");
        System.out.println("📦 Heap: new로 생성한 객체 저장 공간 (예: Map, ArrayList 등)");
        System.out.println("🧠 Non-Heap: 클래스 메타데이터, 정적 변수 등의 JVM 내부 구조 저장");
    }
}
