# 1. JDK 17 기반 이미지 사용
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle Wrapper 복사 및 실행 권한 부여
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x gradlew
RUN ./gradlew build -x test

# 4. 빌드된 JAR 파일 복사
COPY build/libs/*.jar app.jar

# 5. 실행 명령어
CMD ["java", "-jar", "app.jar"]