./generate_jwtKeys.sh
mvn clean compile package -am -pl service-main
mvn clean compile package -am -pl service-gateway
docker compose up
