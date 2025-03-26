mvn clean package
if [ $? -ne 0 ]; then
    exit 1
fi

docker-compose up -d
if [ $? -ne 0 ]; then
    exit 1
fi
