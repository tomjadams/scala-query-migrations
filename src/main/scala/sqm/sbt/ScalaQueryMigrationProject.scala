package sqm.sbt

import _root_.sbt._
import sqm.migrate.DatabaseMigration

abstract class ScalaQueryMigrationProject(info: ProjectInfo) extends DefaultProject(info) {
  /**
   * Define your list of migrations.
   */
  def migrations: List[DatabaseMigration]

  lazy val migrate = migrateAction
  def migrateAction = migrateTask dependsOn(compile) describedAs("Migrates your database: migrate [version]")
  def migrateTask: Task = task { log.info("This is a test."); None }


//  lazy val migrate = task { args =>
//        val latest = migrations.foldLeft(0L)((max, migration) => if (migration.version > max) migration.version else max)
//        val version = if (args.length == 1) args(0).toLong else latest
//        migrateTo(version)
//  } dependsOn(compile) describedAs("Migrates your database: migrate [version]")
//
//  def migrateTo(version: Long) = task {
//    log.info("Migrating database to " + version)
//    // TODO Migrate here...
//    None
//  }
}
