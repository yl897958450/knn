#!/bin/bash
PRG="${0}"
PRGDIR=`dirname ${PRG}`
home_dir=`cd "${PRGDIR}/.." > /dev/null;pwd `
pidfile=${home_dir}"/knn.pid"

#load jars
jars_list=`ls ${home_dir}/lib/*.jar`
jars_path=""
for jar_path in ${jars_list}
do
  jars_path="${jars_path}:${jar_path}"
done
knn_jar_path=`ls ${home_dir}/knn*.jar`

jars_path="${jars_path}:${knn_jar_path}"

TestPid () {
    if [ -f ${pidfile} ] ; then
        if ps ux | grep `cat $pidfile` |grep "KNNMain" | grep -v "grep" > /dev/null ; then
            echo "`date +"%F %T"`: `basename $0` is running!  main process id = $(cat ${pidfile})"
            exit 1
        else
            rm -f $pidfile
        fi
    fi
}
# Trap
trap "rm -f ${pidfile} ; exit 1 " 1 2 3 15
TestPid

nohup  java -cp ${home_dir}/conf/:${jars_path:1} com.yl.knn.KNNMain ${home_dir}/bin >> ${home_dir}/logs/out.log 2>&1 &
echo $! > ${pidfile}
