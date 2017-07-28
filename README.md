# sbt-ammonite
sbt plugin to enable Ammonite REPL.


[Ammonite REPL](http://ammonite.io/#Ammonite-REPLL) is a great REPL that make the life easier. Using it require some boilerplate that should be add to each project.
This plugin eliminate it.
 
 
Installation
============
### Stable version
Add the following lines to one of these files:
- The project-specific file at `project/plugins.sbt`
- Your global file at `~/.sbt/0.13/plugins/sbt-ammonite.sbt`

```sbt
resolvers += Resolver.bintrayIvyRepo("vatbox-oss", "sbt-plugins")
addSbtPlugin("com.vatbox" % "sbt-ammonite" % "0.1.0")
```

Usage
=====
### Commands
`ammoniteRepl` Starts the Scala interpreter with the project classes on the classpath.

`ammoniteReplQuick` Starts the Scala interpreter with the project dependencies on the classpath.


### Load sources
For the `source` command to work, add: 
```sbt
AmmoniteReplPlugin.loadSources
```

### Remote REPL
Add to the build:
```sbt
enablePlugins(AmmoniteSshdPlugin)
```
and add the REPL server to your application:
```scala
import ammonite.sshd._
object passwordChecker extends PasswordAuthenticator {
  def authenticate(username: String, password: String, session: ServerSession): Boolean = {
    username == "username" && password == "password"
  }
}
val replServer = new SshdRepl(
  SshServerConfig(
    address = "localhost", // or "0.0.0.0" for public-facing shells
    port = 22222, // Any available port
    passwordAuthenticator = Some(passwordChecker) // or publicKeyAuthenticator
  )
)
replServer.start()
```

### REPL Arguments
To pass arguments to the REPL use the `ammoniteArgs` setting, eg:

```sbt
ammoniteArgs := Seq("--predef-code", """import ammonite.ops._; import ammonite.ops.ImplicitWd._""")
```

### Ammonite version
To use different version of ammonite REPL use:
```sbt
ammoniteVersion := "1.0.1"
```

---

* If you've enjoyed using Ammonite, and use it day to day, please [chip](https://www.patreon.com/lihaoyi "Patreon page") in to support the project!