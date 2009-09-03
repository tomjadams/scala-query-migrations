package sqm.sbt

import _root_.sbt._
import migrate._
import com.novocode.squery.session.SessionFactory

//trait ScalaQueryMigrationProject extends Project {
trait ScalaQueryMigrationProject extends ManagedProject {
//trait ScalaQueryMigrationProject extends BasicDependencyProject {
  // Automatically include this plugin in projects that mix this trait in.
  val sqm = "sqm" % "sqm" % "0.0.1"

  /**
   * Define your list of migrations.
   */
  def migrations: List[DatabaseMigration]

  /**
   * Define your session factory to use when running migrations.
   */
  def sessionFactory: SessionFactory

  lazy val migrate = migrateAction

  //  def migrateAction = migrateTask dependsOn(compile) describedAs("Migrates your database: migrate [version]")
  def migrateAction = migrateTask describedAs("Migrates your database: migrate [version]")

  def migrateTask = task { args: Array[String] =>
    val latest = migrations.foldLeft(0L)((max, migration) => if (migration.version > max) migration.version else max)
    val version = if (args.length == 1) args(0).toLong else latest
    migrateTo(version) //dependsOn(compile)
  }

  def migrateTo(version: Long) = task {
    new MigrationRunner(sessionFactory, log, migrations).migrateTo(version)
    None
  }
}
