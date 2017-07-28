package com.vatbox.sbt

import sbt.Keys._
import sbt._


object AmmoniteSshdPlugin extends AutoPlugin {

  override def trigger = noTrigger

    override def requires = AmmoniteReplPlugin

  object autoImport {
    lazy val ammoniteSshdVersion = settingKey[String]("Ammonite sshd version")
  }

  import autoImport._


  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()


  override lazy val projectSettings =  Seq(
    ammoniteSshdVersion := AmmoniteReplPlugin.autoImport.ammoniteVersion.value,
    libraryDependencies += "com.lihaoyi" % "ammonite-sshd" % ammoniteSshdVersion.value cross CrossVersion.full
  )
}