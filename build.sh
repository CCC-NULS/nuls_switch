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

#docker run -p 80:80 --name nginx --network overlay -d 192.168.1.72/chenxue/paascloud-nginx
#docker service create --name paascloud-nginx --publish 80:80 --network overlay 192.168.1.72/chenxue/paascloud-nginx