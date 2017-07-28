package com.vatbox.sbt

import sbt.{TaskKey, _}
import sbt.Attributed._
import Keys.{Classpath, _}


object AmmoniteReplPlugin extends AutoPlugin {

  override def trigger = allRequirements

  //  override def requires = JvmPlugin

  object autoImport {
    lazy val ammoniteVersion = settingKey[String]("Ammonite version")
//    lazy val ammoniteMain = settingKey[String]("Ammonite main class")
    lazy val ammoniteArgs = settingKey[Seq[String]]("Ammonite init args")

    lazy val ammoniteRepl = inputKey[Unit]("Run ammonite repl")
    lazy val ammoniteReplQuick = inputKey[Unit]("Run ammonite repl with compile sources")
  }

  import autoImport._


  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()

  def runTask(dependencyClasspath: TaskKey[Classpath], config: Configuration, mainClass: String, arguments: String*) = //: Initialize[Task[Unit]] =
    (dependencyClasspath in config, runner in (config, run), streams) map { (cp, r, s) =>
      toError(r.run(mainClass, data(cp), arguments, s.log))
    }

  def loadSource(dependencyClasspath: TaskKey[Classpath]) = {
    (dependencyClasspath in Test) ++= {
      (updateClassifiers in Test).value
        .configurations
        .find(_.configuration == Test.name)
        .get
        .modules
        .flatMap(_.artifacts)
        .collect{case (a, f) if a.classifier == Some("sources") => f}
    }
  }
  lazy val loadSources: Seq[Setting[_]] = inConfig(Test)(loadSource(fullClasspath)) ++ loadSource(externalDependencyClasspath)

  val ammoniteMain = "ammonite.Main"
  override lazy val projectSettings =  Seq(
    ammoniteVersion := "1.0.1",
    ammoniteArgs := Nil,

    libraryDependencies += "com.lihaoyi" % "ammonite" % ammoniteVersion.value % Test cross CrossVersion.full,

    ammoniteRepl := Def.taskDyn[Unit] {
      runTask(fullClasspath, Test, ammoniteMain, ammoniteArgs.value: _*)
    }.value,
    ammoniteReplQuick := Def.taskDyn[Unit] {
      runTask(externalDependencyClasspath, Test, ammoniteMain, ammoniteArgs.value: _*)
    }.value
  )
}