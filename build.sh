cd nuls-switch-web
yarn run build

docker build -t registry.cn-hangzhou.aliyuncs.com/edwardchan/nuls-switch-web:latest .

cd ../docker/mysql
docker build -t registry.cn-hangzhou.aliyuncs.com/edwardchan/nuls-switch-mysql:latest .

cd ../../

cd nuls-switch-backend
mvn clean package -DskipTests=true
docker build -t registry.cn-hangzhou.aliyuncs.com/edwardchan/nuls-switch-backend:latest .

if [ $? -eq 0 ];then
    echo "docker build success"
else
    echo "docker build fail"
    exit -1
fi

#docker push registry.cn-hangzhou.aliyuncs.com/edwardchan/nuls-switch-web:latest
#docker push registry.cn-hangzhou.aliyuncs.com/edwardchan/nuls-switch-backend:latest

if [ $1 == push ];then
    echo "push to docker repository"
    docker push registry.cn-hangzhou.aliyuncs.com/edwardchan/nuls-switch-web:latest
    docker push registry.cn-hangzhou.aliyuncs.com/edwardchan/nuls-switch-backend:latest
    docker push registry.cn-hangzhou.aliyuncs.com/edwardchan/nuls-switch-mysql:latest
fi