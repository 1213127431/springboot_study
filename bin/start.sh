#!/usr/bin/env bash

#启动：sh start.sh start {profileName,如:pro,dev,fz},停止：sh start.sh stop

#部署目录
#SERVER=/app/appadmin/springboot_study
#通过相对路径获取部署目录
cd ..
SERVER=`pwd`
#日志目录
LOG_SERVER=/app/appadmin/logs/springboot_study/logs
#jar包的名称
SERVER_NAME=springboot_study
CONF_DIR=$SERVER/config
ALLCONF_DIR=$SERVER/config-envs

cd $SERVER
JAR=*.jar

#Spring active profile env
ENV_NAME=$2

#java虚拟机启动参数
JAVA_OPTS="-Xmx2G -Xms1G -Xmn512m -Xss512k"

if [ "$3" = "debug" ]; then
DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=9000,server=y,suspend=n"
fi

if [ "$4" = "jacoco" ]; then
JACOCO_OPTS="-javaagent:$SERVER/lib/jacocoagent.jar=includes=*,output=tcpserver,address=0.0.0.0,port=10086"
fi

CONFIG_FILES=" -Dspring.config.location=$CONF_DIR/application.yml "

start(){

    #判断应用是否已经启动
    PIDS=`ps -f | grep java | grep "$SERVER_NAME" |awk '{print $2}'`
    if [ -n "$PIDS" ]; then
        echo "ERROR: The service $SERVER_NAME already started!"
        echo "PID: $PIDS"
        exit 1
    fi

    #如果没有LOG_SERVER目录则创建
    if [ ! -d $LOG_SERVER ]; then
      mkdir -p -m 755 $LOG_SERVER
      echo "mkdir -p -m 755 ${LOG_SERVER} done"
    fi

    #如果没有CONF_DIR目录则创建
    if [ ! -d $CONF_DIR ]; then
      mkdir -p -m 755 $CONF_DIR
      echo "mkdir -p -m 755 ${CONF_DIR} done"
    fi

    #复制对应的配置文件
    cp -rf $ALLCONF_DIR/$ENV_NAME/* $CONF_DIR

    LOG=$LOG_SERVER/info.log
    echo "start project begin..." >> $LOG
    cd $SERVER
    classPath="."
    nohup java $JAVA_OPTS $DEBUG_OPTS $JACOCO_OPTS $CONFIG_FILES -jar $SERVER/$JAR >/dev/null 2>&1 &
    echo $! > $SERVER/server.pid
    echo "start project success..." >> $LOG
    echo "start project success,PID:$1,LOG-PATH:$LOG"
}

stop(){
    echo "stop project..."
    PID=`cat $SERVER/server.pid`
    kill $PID
    rm -rf $SERVER/server.pid
    echo "stop project success,PID:$PID"
}


restart(){
    stop
    sleep 1
    start
}

if [ "$1" = "start" ]; then
start
elif [ "$1" = "stop" ]; then
stop
elif [ "$1" = "restart" ]; then
restart
else
restart
fi


#restart

