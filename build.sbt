name := """sbt-ammonite"""
organization := "com.vatbox"
//git.baseVersion := "0.1.1"

//scalaVersion := "2.10.6"
scalaVersion := "2.12.2"
//sbtVersion in pluginCrossBuild := "1.0.0"
sbtVersion in Global := "1.0.0"

crossSbtVersions := Vector("0.13.16", "1.0.0")

scalaCompilerBridgeSource := {
  val sv = appConfiguration.value.provider.id.version
  ("org.scala-sbt" % "compiler-interface" % sv % "component").sources
}

sbtPlugin := true

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.1" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

enablePlugins(GitVersioning, GitBranchPrompt)
git.useGitDescribe := true

publishMavenStyle := false
bintrayRepository := "sbt-plugins"
bintrayOrganization := Option("vatbox-oss")
bintrayVcsUrl := Option("""git@github.com:VATBox/sbt-ammonite.git""")
bintrayPackageLabels := Seq("sbt","plugin")

//initialCommands in console := """import com.vatbox.sbt._"""

// set up 'scripted; sbt plugin for testing sbt plugins
ScriptedPlugin.scriptedSettings
scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}
scriptedBufferLog := false
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))


//libraryDependencies += "com.lihaoyi" % "ammonite" % "1.0.1" % Test cross CrossVersion.full

//initialCommands in (Test, console) := """ammonite.Main().run()"""