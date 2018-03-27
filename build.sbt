import Dependencies.Artifacts._

name := "xmltoavrostreamer"

version := "1.0.3"

organization := "com.clicktale.pipeline"

scalaVersion := "2.12.4"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8", "-language:implicitConversions")

isSnapshot := true

resolvers ++= Seq(
  "Nexus Maven Virtual" at "http://rg-nexus:8080/nexus/content/repositories/nexus-maven-virtual/",
  "Pipeline Nexus Repository" at "http://rg-nexus:8080/nexus/content/repositories/nexus-pipeline/",
  "Global Nexus Repository" at "http://rg-nexus/nexus/content/repositories/nexus-rpm-global/",
  "MVN Repository" at "http://mvnrepository.com/",
  "Typesafe Repository" at "http://dl.bintray.com/typesafe/maven-releases/"
)

libraryDependencies ++= Seq(
  gson,
  commonsIo,
  commonsCompress,
  lz4,
  kafka
)  ++ allAkka ++ allJson ++ allLogging ++ allSql ++ allClicktale

mainClass in assembly := Some("com.clicktale.xmlavrostreamer.Boot")
mainClass in packageBin := Some("com.clicktale.xmlavrostreamer.Boot")

enablePlugins(JavaServerAppPackaging, RpmPlugin, RpmDeployPlugin)
rpmVendor := "Clicktale"
rpmLicense := Some("Clicktale")
packageName in Rpm := s"${name.value}"
assemblyJarName := s"${name.value}-${version.value}.jar"
// we specify the name for our fat jar
// the bash scripts classpath only needs the fat jar
scriptClasspath := Seq(assemblyJarName.value)
packageArchitecture in Rpm := "noarch"
packageSummary in Rpm := "ClickTale XmlToAvroStreamer"
packageDescription in Linux := "Streaming events from source kafka to destination kafka as avro"
daemonUser in Linux := "ec2-user"// to validate
maintainer in Linux := "Pipeline Team"

defaultLinuxInstallLocation := sys.props.getOrElse("clicktalefolder", default = "/opt/clicktale")
rpmPrefix := Some(defaultLinuxInstallLocation.value)
linuxPackageSymlinks := Seq.empty
defaultLinuxLogsLocation := "/var/log"

// removes all jar mappings in universal and appends the fat jar
mappings in Universal := {
  val universalMappings = (mappings in Universal).value
  val fatJar = (assembly in Compile).value
  // removing means filtering
  val filtered = universalMappings filter {
    case (_, fileName) => !fileName.endsWith(".jar")
  }
  filtered :+ fatJar -> ("lib/" + fatJar.getName)
}


// Override the RPM Post install scriptlet so that it would just add it in init.d without starting it
maintainerScripts in Rpm := (maintainerScripts in Rpm).value ++ Map(RpmConstants.Post -> IO.readLines(sourceDirectory.value / "rpm" / "post-rpm"))

// Publish rpm to nexus repo
credentials += Credentials("Sonatype Nexus Repository Manager", "192.168.180.212", "deployment", "dep123")
// disable publishing the java doc jar
publishArtifact in(Compile, packageDoc) := false
// publishMavenStyle is used to ensure POMs are generated and pushed
publishMavenStyle := true
publishTo := {
  val nexus = "http://192.168.180.212:8080/nexus/"
  Some("releases" at nexus + "content/repositories/nexus-rpm/")
}
