package sqm.migrate

import com.novocode.squery.session.SessionFactory

object MigrationRunner {
  def migrate(database: SessionFactory, log: {def info(message: => String): Unit}, migrations: List[DatabaseMigration], version: Long) =
    new MigrationRunner(database, log, migrations).migrateTo(version)
}

final class MigrationRunner(sessionFactory: SessionFactory, log: {def info(message: => String): Unit}, migrations: List[DatabaseMigration]) {
  // TODO Return Some(List[Exception]) to handle errors
  def migrateTo(version: Long) = {
    // TODO Get current version of database
    // TODO Figure out where the database is & apply up/down as appropriate
    log.info("Migrating database to " + version)
    migrations.foreach((m: DatabaseMigration) => {
      m.upWithTransaction(sessionFactory)
      log.info("Applied UP in migration: " + m.description.getOrElse(m.version.toString))
    })
  }
}
