FROM gradle:8.10-jdk17

WORKDIR /app

COPY . .

RUN gradle clean build -x test --no-daemon

EXPOSE 8080

CMD ["gradle", "run", "--no-daemon"]