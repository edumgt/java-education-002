import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class OrdersWithMemoryExplanation {
    public static void main(String[] args) {

        System.out.println("🧠 JVM 메모리 구조 설명 시작");
        System.out.println("🔸 Stack: 지역 변수와 참조(포인터)가 저장됩니다.");
        System.out.println("🔹 Heap: 실제 객체(Map, Scanner 등)가 저장됩니다.\n");

        // Stack에 참조 저장, Heap에 객체 저장
        Scanner scanner = new Scanner(System.in);
        System.out.println("📍 Scanner 객체 생성 → Heap에 저장됨, 참조는 Stack에 위치\n");

        // 두 Map도 Heap에 실제 객체 저장, Stack에는 참조만 저장
        Map<String, Double> orders = new LinkedHashMap<>();
        System.out.println("📍 orders (상품-가격 Map) 객체 생성 → Heap에 저장됨");
        Map<String, Integer> ordersQuantity = new LinkedHashMap<>();
        System.out.println("📍 ordersQuantity (상품-수량 Map) 객체 생성 → Heap에 저장됨\n");

        System.out.println("📦 상품 정보를 입력하세요 (형식: 상품명 가격 수량)");
        System.out.println("⏹ 입력 종료는 'buy' 입력");

        String input = scanner.nextLine(); // Stack에 input 저장됨

        while (!input.equals("buy")) {
            try {
                String[] parts = input.trim().split(" "); // parts 참조는 Stack, 배열은 Heap
                if (parts.length != 3) {
                    throw new IllegalArgumentException("입력 형식 오류: 상품명, 가격, 수량이 필요합니다.");
                }

                // name, price, quantity → Stack (기본형 및 참조형 지역 변수)
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                int quantity = Integer.parseInt(parts[2]);

                if (quantity < 0) {
                    System.out.printf("⚠️ 수량이 음수입니다 (%d). 이 입력은 무시됩니다.%n", quantity);
                    input = scanner.nextLine();
                    continue;
                }

                // ordersQuantity와 orders는 Heap에 있는 Map에 접근하는 것
                ordersQuantity.put(name, ordersQuantity
                .getOrDefault(name, 0) + quantity);
                orders.put(name, price);

            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자 변환 오류: 가격이나 수량에 숫자가 아닌 값이 포함되었습니다.");
            } catch (Exception e) {
                System.out.println("❌ 오류: " + e.getMessage());
            }

            input = scanner.nextLine(); // Stack에 input 참조가 바뀜
        }

        System.out.println("\n🧾 최종 주문 목록:");
        for (Map.Entry<String, Double> entry : orders.entrySet()) {
            String product = entry.getKey();
            double price = entry.getValue();
            int qty = ordersQuantity.get(product);
            double total = price * qty;

            System.out.printf("%s -> %.2f%n", product, total);
        }

        scanner.close();
    }
}
