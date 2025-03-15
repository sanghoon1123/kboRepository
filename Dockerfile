# 1. 사용할 기본 이미지 (JDK 17)
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일 복사
COPY build/libs/*.jar app.jar

# 4. 실행 명령어
CMD ["java", "-jar", "app.jar"]
