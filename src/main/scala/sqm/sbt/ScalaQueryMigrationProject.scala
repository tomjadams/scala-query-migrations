package sqm.sbt

import _root_.sbt._
import migrate._

trait ScalaQueryMigrationProject extends Project {
  /**
   * Define your list of migrations.
   */
  def migrations: List[DatabaseMigration]

  lazy val migrate = migrateAction

  //  def migrateAction = migrateTask dependsOn(compile) describedAs("Migrates your database: migrate [version]")
  def migrateAction = migrateTask describedAs("Migrates your database: migrate [version]")

  def migrateTask = task { args: Array[String] =>
    val latest = migrations.foldLeft(0L)((max, migration) => if (migration.version > max) migration.version else max)
    val version = if (args.length == 1) args(0).toLong else latest
    migrateTo(version)
  }

  def migrateTo(version: Long) = task {
    new MigrationRunner(migrations, log).migrateTo(version)
    None
  }
}
