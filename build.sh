app=consumer
tag=4.0

eval $(minikube docker-env)

mvn clean package -DskipTests -B
docker build -t ${app}:${tag} .