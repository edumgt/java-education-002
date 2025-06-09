/**
 * 📘 프로그램 설명:
 * 이 Java 프로그램은 한 사람이 1800년부터 특정 연도까지 살면서,
 * 초기 예산으로 생존이 가능한지를 계산하는 시뮬레이션입니다.
 *
 * 규칙:
 * - 짝수 해: 12000 레바 지출
 * - 홀수 해: 12000 + (50 × 나이) 레바 지출
 * - 1800년에 18세였다면, 이후 매년 한 살씩 나이 증가
 * - 결과적으로 예산이 충분하면 남은 돈 출력, 부족하면 필요한 돈 출력
 */

import java.lang.management.*;
import java.util.Scanner;

public class BackToThePastWithExplanation {
    public static void main(String[] args) {

        // 📦 JVM 구조 설명
        System.out.println("🧠 JVM 메모리 구조 요약:");
        System.out.println("🔸 Stack: 지역 변수 (money, year, age 등)");
        System.out.println("🔹 Heap : 객체 (Scanner), new 연산으로 생성된 모든 데이터\n");

        // ▶ Stack: scanner 참조 저장 / Heap: Scanner 객체 생성
        Scanner scanner = new Scanner(System.in);
        System.out.println("📍 new Scanner(System.in) → Heap에 Scanner 객체 생성, Stack에는 참조 저장됨\n");

        // ▶ 사용자 입력 받기 (Stack에 변수 저장됨)
        System.out.print("초기 소지 금액을 입력하세요 (예: 50000): ");
        double money = Double.parseDouble(scanner.nextLine()); // Stack: money

        System.out.print("살아갈 마지막 연도를 입력하세요 (예: 1900): ");
        int targetYear = Integer.parseInt(scanner.nextLine()); // Stack: targetYear

        double livingMoney = 0; // Stack: 누적 지출 저장
        int age = 17;           // 시작 나이 (1800년엔 18세부터 시작)

        // ▶ CPU 및 메모리 사용량 측정 시작
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean cpuBean = ManagementFactory.getThreadMXBean();
        long startCpu = cpuBean.getCurrentThreadCpuTime();

        // ▶ 연도별 지출 계산
        for (int year = 1800; year <= targetYear; year++) {
            age++; // 한 해가 지날 때마다 나이 증가

            if (year % 2 == 0) {
                livingMoney += 12000;
                System.out.printf("🔹 %d년 (짝수) → 12000 사용 → 누적 지출: %.2f%n", 
                year, livingMoney);
            } else {
                double cost = 12000 + 50 * age;
                livingMoney += cost;
                System.out.printf("🔸 %d년 (홀수) → 12000 + 50×%d = %.2f 사용 → 누적 지출: %.2f%n", 
                year, age, cost, livingMoney);
            }
        }

        // ▶ 메모리/CPU 사용 정보 출력
        MemoryUsage heap = memoryBean.getHeapMemoryUsage();
        long heapUsed = heap.getUsed() / 1024 / 1024;
        long heapMax = heap.getMax() / 1024 / 1024;

        long endCpu = cpuBean.getCurrentThreadCpuTime();
        double cpuTimeMs = (endCpu - startCpu) / 1_000_000.0;

        System.out.printf("\n🧠 최종 Heap 사용량: %d MB / %d MB%n", heapUsed, heapMax);
        System.out.printf("⚙️  CPU 사용 시간 (현재 쓰레드): %.2f ms%n", cpuTimeMs);

        // ▶ 예산 비교 결과 출력
        double diff = Math.abs(money - livingMoney); // Stack

        if (money >= livingMoney) {
            System.out.printf("\n✅ 예산 충분! 남은 금액: %.2f dollars%n", diff);
        } else {
            System.out.printf("\n❌ 예산 부족! 부족한 금액: %.2f dollars%n", diff);
        }

        scanner.close(); // 입력 종료
        System.out.println("🧹 Scanner 닫기 → Heap 자원 해제 요청됨");
        System.out.println("✅ 프로그램 종료 → Stack 프레임 제거, Heap 객체는 GC 대상 가능\n");
    }
}
