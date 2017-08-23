name := """sbt-ammonite"""
organization := "com.vatbox"
git.baseVersion := "0.1.0"

scalaVersion := "2.10.6"

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

initialCommands in console := """import com.vatbox.sbt._"""

// set up 'scripted; sbt plugin for testing sbt plugins
ScriptedPlugin.scriptedSettings
scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}
scriptedBufferLog := false
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.0")

//libraryDependencies += "com.lihaoyi" % "ammonite" % "1.0.1" % Test cross CrossVersion.full

//initialCommands in (Test, console) := """ammonite.Main().run()"""