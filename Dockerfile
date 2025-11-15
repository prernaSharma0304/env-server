FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

# Compile your Java file
RUN javac EnvServer.java

# Expose the port your Java server uses
EXPOSE 8080

# Start the server
CMD ["java", "EnvServer"]
