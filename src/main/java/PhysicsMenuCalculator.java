import java.util.Scanner;

public class PhysicsMenuCalculator {

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("\n🚀 Physics Mini Calculator (m, s 단위)\n");
            System.out.println("메뉴를 선택하세요:");
            System.out.println("  1. 가속도 (Acceleration)");
            System.out.println("  2. 평균 속도 (Average Velocity)");
            System.out.println("  3. 이동 거리 (Distance)");
            System.out.print("\n👉 선택: ");
            int choice = input.nextInt();

            System.out.println(); // 줄바꿈

            switch (choice) {
                case 1: // 가속도
                    System.out.print("초기 속도 (m/s): ");
                    double u1 = input.nextDouble();
                    System.out.print("최종 속도 (m/s): ");
                    double v1 = input.nextDouble();
                    System.out.print("시간 (s): ");
                    double t1 = input.nextDouble();

                    double acceleration = (v1 - u1) / t1;
                    System.out.println("\n▶ 가속도 = " + String.format("%.4f", acceleration) + " m/s^2");
                    break;

                case 2: // 평균 속도
                    System.out.print("초기 속도 (m/s): ");
                    double u2 = input.nextDouble();
                    System.out.print("최종 속도 (m/s): ");
                    double v2 = input.nextDouble();

                    double avg_velocity = (u2 + v2) / 2.0;
                    System.out.println("\n▶ 평균 속도 = " + String.format("%.4f", avg_velocity) + " m/s");
                    break;

                case 3: // 이동 거리
                    System.out.print("초기 속도 (m/s): ");
                    double u3 = input.nextDouble();
                    System.out.print("최종 속도 (m/s): ");
                    double v3 = input.nextDouble();
                    System.out.print("시간 (s): ");
                    double t3 = input.nextDouble();

                    double avg_v3 = (u3 + v3) / 2.0;
                    double distance = avg_v3 * t3;
                    System.out.println("\n▶ 이동 거리 = " + String.format("%.4f", distance) + " m");
                    break;

                default:
                    System.out.println("\n❌ 잘못된 선택입니다. (1~3 입력)");
            }
        } catch (Exception e) {
            System.err.println("\n❌ 입력 오류: " + e.getMessage());
        }
    }
}
