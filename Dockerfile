# 1. JDK 17 Alpine 기반 이미지 사용
FROM openjdk:17-alpine

# 2. 작업 디렉터리 생성
WORKDIR /app

# 3. 소스 코드 복사
COPY PhysicsMenuCalculator.java /app

# 4. UTF-8 인코딩으로 컴파일
RUN javac -encoding UTF-8 PhysicsMenuCalculator.java

# 5. 실행 시 메모리 제한 옵션 지정
CMD ["java", "-Xms3m", "-Xmx5m", "PhysicsMenuCalculator"]
