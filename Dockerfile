FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

# Compile the Java server
RUN javac EnvServer.java

# Render uses PORT from environment
# So we use it dynamically
CMD ["sh", "-c", "java EnvServer"]

