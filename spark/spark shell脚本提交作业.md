# spark shell 脚本提交作业 （调优）
```shell
# --为了好看换行了
spark-submit --master spark://11.172.54.167:7077 
--class $main --deploy-mode client --driver-memory 16g
--executor-memory 25g
--executor-cores 8
--total-executor-cores 320 
--conf spark.memory.fraction=0.8
--conf spark.memory.storageFraction=0.3 
--conf spark.memory.offHeap.enabled=true 
--conf spark.memory.offHeap.size=5g 
--conf "spark.executor.extraJavaOptions=-XX:+UseG1GC -XX:-TieredCompilation -XX:G1HeapRegionSize=16m -XX:InitiatingHeapOccupancyPercent=55 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:-UseCompressedClassPointers -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -XX:ReservedCodeCacheSize=512m -XX:+UseCodeCacheFlushing -XX:ParallelGCThreads=20 -XX:ConcGCThreads=20 -Xms20g -XX:+PrintGCDetails -XX:+PrintGCTimeStamps" 
--jars $jars xxxx.jar $date1 $max $date2  >> log/$log_file


#代码内参数
conf.set("spark.driver.maxResultSize", "8g");
conf.set("spark.serialize", "org.apache.spark.serializer.KryoSerializer");
conf.registerKryoClasses(new Class[]{ImmutableBytesWritable.class, HyperLogLog.class, HashSet.class, RegisterSet.class, IllegalArgumentException.class, FileCommitProtocol.TaskCommitMessage.class});
//conf.set("spark.kryo.registrationRequired","true"); #开启的话类没加到上面会报错
conf.set("spark.kryoserializer.buffer.mb", "10");
conf.set("spark.shuffle.file.buffer", "128");
conf.set("spark.reducer.maxSizeInFlight", "144");
conf.set("spark.shuffle.io.maxRetries", "50");
conf.set("spark.shuffle.io.retryWait", "5s");

```