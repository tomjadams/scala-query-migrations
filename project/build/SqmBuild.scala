import sbt._
import sbt.TaskManager._

// TODO Release the migration stuff as a JAR, pull as dependency. git-hub project with built jar checked in.
trait ScalaQueryMigration {
  import _root_.com.mogeneration.flyte.db.migrate.DatabaseMigration

  /**
   * Define your list of migrations here.
   */
  def migrations: List[DatabaseMigration]

  lazy val migrate = task {
    args =>
        val newestVersion = Tmigrations.foldLeft(0)(max, migration => if (migration.version) > max else max)
        val version = if (args.length == 1) args(0).toInt else newestVersion
        migrateTo(version)
  } dependsOn(compile) describedAs("Migrates your database.")

  def migrateTo(version: Int) = task {
    log.info("Migrating database to " + version)
    // TODO Migrate here...
    None
  }
}

final class FlyteBuild(info: ProjectInfo) extends DefaultWebProject(info) with ScalaQueryMigration {
  //  val servlet = "javax.servlet" % "servlet-api" % "2.5" % "provided->default"

  // Runtime dependencies
  val jettyServlet = "org.eclipse.jetty" % "jetty-servlet" % "7.0.0.RC2"
  val jettyServer = "org.eclipse.jetty" % "jetty-server" % "7.0.0.RC2"
  val dispatch = "net.databinder" %% "dispatch-json" % "0.5.1"
  val h2database = "com.h2database" % "h2" % "1.1.117"

  // Test dependencies
  val specs = "org.scala-tools.testing" % "specs" % "1.5.0" % "test->default"
  val httpunit = "httpunit" % "httpunit" % "1.6.2" % "test->default"
  val rhino = "rhino" % "js" % "1.7R2" % "test->default"

  override def mainClass = Some("scapps.http.SlinkyRunner")

  import _root_.com.mogeneration.flyte.db.migrate.Migrations
  def migrations = Migrations.migrations
}
