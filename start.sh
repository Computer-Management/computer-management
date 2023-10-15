./generate_jwtKeys.sh
cd service-main
mvn clean compile package
cd ../service-gateway
mnv clean compile package
cd ../
docker compose up
