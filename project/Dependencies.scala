import sbt._

object Dependencies {
  object Versions {
    val vAkka = "2.5.4"
    val vAkkaHttp = "10.0.10"
    val vAkkaKafka = "0.18"
    val vLogback = "1.2.3"
    val vKafka = "0.10.2.1"
    val vJson4s = "3.5.3"
    val vGson = "2.8.1"
    val vCommonsIo = "1.3.2"
    val vCommonsCompress = "1.14"
    val vScalaLogging = "3.7.2"
    val vScalaLikeJdbc = "3.0.1"
    val vMsSqlDriver = "4.2"
    val vLz4 = "1.3.0"
    val vRecorderEvents = "3.2.2"
  }

  object Artifacts {
    import Versions._
    val gson = "com.google.code.gson" % "gson" % vGson withSources() withJavadoc()
    val commonsIo = "commons-io" % "commons-io" % vCommonsIo withSources() withJavadoc()
    //Compression
    val commonsCompress = "org.apache.commons" % "commons-compress" % vCommonsCompress withSources() withJavadoc()
    val lz4 = "net.jpountz.lz4" % "lz4" % vLz4 withSources() withJavadoc()


    //Akka
    val akkaActor = "com.typesafe.akka" %% "akka-actor" % vAkka withSources() withJavadoc()
    val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % vAkka withSources() withJavadoc()
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % vAkka withSources() withJavadoc()
    val akkaStreamKafka = "com.typesafe.akka" %% "akka-stream-kafka" % vAkkaKafka withSources() withJavadoc()
    val allAkka = Seq(akkaActor,akkaSlf4j,akkaStream,akkaStreamKafka)

    //Json
    val json4sNative = "org.json4s" %% "json4s-native" % vJson4s withSources() withJavadoc()
    val json4sJackson = "org.json4s" %% "json4s-jackson" % vJson4s withSources() withJavadoc()
    val allJson = Seq(json4sNative,json4sJackson)


    //Kafka
    val kafka = "org.apache.kafka" %% "kafka" % vKafka exclude("org.slf4j", "*") withSources() withJavadoc()


    //Clicktale
    val recorderEvents = "com.clicktale" %% "recorder-events" % vRecorderEvents
    val allClicktale = Seq(recorderEvents)

    //Logging
    val logback = "ch.qos.logback" % "logback-classic" % vLogback withSources() withJavadoc()
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % vScalaLogging withSources() withJavadoc()
    val allLogging = Seq(logback,scalaLogging)

    //RDS
    val scalaLikeJdbc = "org.scalikejdbc" %% "scalikejdbc" % vScalaLikeJdbc withSources() withJavadoc()
    val msSqlDriver = "com.microsoft.sqlserver" % "jdbc" % vMsSqlDriver
    val allSql = Seq(scalaLikeJdbc,msSqlDriver)

  }
}
