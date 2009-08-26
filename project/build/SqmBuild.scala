import sbt._

final class SqmBuild(info: ProjectInfo) extends DefaultProject(info) {
  // Test dependencies
  val specs = "org.scala-tools.testing" % "specs" % "1.5.0" % "test->default"
  val h2database = "com.h2database" % "h2" % "1.1.117" % "test->default"
}
