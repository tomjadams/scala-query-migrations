package sqm.migrate

// TODO Pass in the SessionFactory
final class MigrationRunner(migrations: List[DatabaseMigration], log: { def info(message: => String): Unit }) {
  def migrateTo(version: Long) = {
    log.info("Migrating database to " + version)
  }
}
