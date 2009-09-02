package sqm.migrate

final class MigrationRunner(log: { def info(message: => String): Unit }) {
  def migrateTo(version: Long) = {
    log.info("Migrating database to " + version)
  }
}
